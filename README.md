# Devoria
Devoria is (currently) an open-source project for the server Eternia (might change it to Devoria soon). It is an ambitious project to create an
MMO in Minecraft with a full-fledged spell and item system. It is coded fully in Java 

**Join the Team!**:
Our public discord server is at https://discord.gg/X5wAaZ2C7F. Feel free to submit an application for our team at any time!

**Credits**:  
*ptobey*: Created Mobs, Loot, and Items systems  
*SleepingRaven*: Created Spells and Player Saves  

## Build and runtime smoke test

Devoria targets Java 17 and Paper 1.19.3. Build a reproducible plugin JAR with:

```bash
./mvnw -B -ntp clean verify
```

With Java 17, `curl`, and `sha256sum` available, boot the resulting JAR on a
temporary Paper server with:

```bash
scripts/smoke-test.sh target/Devoria-1.0.jar
```

The smoke test downloads checksum-pinned Paper build 448 into a temporary
directory, starts Devoria without ModelEngine, verifies the model-backed mob
fallback, and shuts the server down cleanly. ModelEngine is optional at runtime
and can also be disabled through `integrations.model-engine.enabled` in
`config.yml`.

ModelEngine is expected on production servers and is required for the complete
model-backed mob feature set. The soft dependency exists so development,
diagnostics, and recovery can run in a clearly reported degraded mode. Operators
can inspect the current runtime state from the server console with:

```text
devoria status
```
