#!/usr/bin/env bash

set -euo pipefail

readonly PAPER_VERSION="1.19.3"
readonly PAPER_BUILD="448"
readonly PAPER_SHA256="3007f2c638d5f04ed32b6adaa33053fe3634ccfa74345c83d3ea4982d38db5dc"
readonly PAPER_URL="https://fill-data.papermc.io/v1/objects/${PAPER_SHA256}/paper-${PAPER_VERSION}-${PAPER_BUILD}.jar"
readonly USER_AGENT="Devoria-Smoke-Test/1.0 (https://github.com/ptobey/Devoria)"
readonly STARTUP_TIMEOUT_SECONDS="${DEVORIA_SMOKE_TIMEOUT_SECONDS:-180}"

plugin_jar="${1:-target/Devoria-1.0.jar}"
if [[ ! -f "$plugin_jar" ]]; then
    echo "Plugin JAR not found: $plugin_jar" >&2
    exit 1
fi

server_directory="$(mktemp -d)"
server_pid=""

cleanup() {
    if [[ -n "$server_pid" ]] && kill -0 "$server_pid" 2>/dev/null; then
        printf 'stop\n' >&3 2>/dev/null || true
        kill "$server_pid" 2>/dev/null || true
        wait "$server_pid" 2>/dev/null || true
    fi
    exec 3>&- 2>/dev/null || true
    rm -rf -- "$server_directory"
}
trap cleanup EXIT INT TERM

mkdir -p "$server_directory/plugins"
cp "$plugin_jar" "$server_directory/plugins/Devoria.jar"

curl --fail --location --silent --show-error \
    --header "User-Agent: $USER_AGENT" \
    "$PAPER_URL" \
    --output "$server_directory/paper.jar"
printf '%s  %s\n' "$PAPER_SHA256" "$server_directory/paper.jar" | sha256sum --check --status

printf 'eula=true\n' >"$server_directory/eula.txt"
cat >"$server_directory/server.properties" <<'PROPERTIES'
allow-nether=false
difficulty=peaceful
enable-command-block=false
enable-query=false
enable-rcon=false
generate-structures=false
level-type=minecraft:flat
max-players=1
motd=Devoria smoke test
online-mode=false
simulation-distance=2
spawn-protection=0
sync-chunk-writes=true
view-distance=2
PROPERTIES

mkfifo "$server_directory/console"
exec 3<>"$server_directory/console"

(
    cd "$server_directory"
    java -Xms512M -Xmx1G -jar paper.jar --nogui <&3 >server.log 2>&1
) &
server_pid=$!

deadline=$((SECONDS + STARTUP_TIMEOUT_SECONDS))
while kill -0 "$server_pid" 2>/dev/null; do
    if grep -Fq "Error occurred while enabling Devoria" "$server_directory/server.log" \
        || grep -Fq "UnknownDependencyException" "$server_directory/server.log" \
        || grep -Fq "NoClassDefFoundError: com/ticxo/modelengine" "$server_directory/server.log"; then
        echo "Devoria failed during Paper startup:" >&2
        tail -n 200 "$server_directory/server.log" >&2
        exit 1
    fi

    if grep -Fq "[Devoria] Enabling Devoria v" "$server_directory/server.log" \
        && grep -Fq "ModelEngine is not installed; model-backed mobs are disabled." "$server_directory/server.log" \
        && grep -Fq "Done (" "$server_directory/server.log"; then
        printf 'devoria status\n' >&3
        printf 'summonmob example_mob\n' >&3
        command_deadline=$((SECONDS + 10))
        while ! grep -Fq "ModelEngine: unavailable (required for full feature set; degraded mode active)" "$server_directory/server.log" \
            || ! grep -Fq "ModelEngine is unavailable; model-backed mobs cannot be summoned." "$server_directory/server.log"; do
            if ! kill -0 "$server_pid" 2>/dev/null || (( SECONDS >= command_deadline )); then
                echo "The operator status or unavailable ModelEngine command did not return its expected message:" >&2
                tail -n 200 "$server_directory/server.log" >&2
                exit 1
            fi
            sleep 1
        done

        printf 'stop\n' >&3
        wait "$server_pid"
        server_pid=""

        if ! grep -Fq "[Devoria] Disabling Devoria v" "$server_directory/server.log"; then
            echo "Paper stopped without disabling Devoria cleanly:" >&2
            tail -n 200 "$server_directory/server.log" >&2
            exit 1
        fi

        echo "Devoria Paper ${PAPER_VERSION} smoke test passed without ModelEngine."
        exit 0
    fi

    if (( SECONDS >= deadline )); then
        echo "Paper did not finish starting within ${STARTUP_TIMEOUT_SECONDS} seconds:" >&2
        tail -n 200 "$server_directory/server.log" >&2
        exit 1
    fi

    sleep 1
done

wait "$server_pid" || true
server_pid=""
echo "Paper exited before Devoria completed its startup checks:" >&2
tail -n 200 "$server_directory/server.log" >&2
exit 1
