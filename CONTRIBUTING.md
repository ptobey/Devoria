# Contributing to Devoria

Devoria is recovering from an early prototype into a testable Paper MMO plugin.
Keep changes small, reviewable, and compatible with the current runtime gate.

## Development environment

- Java 17
- Paper 1.19.3 API
- Maven Wrapper from this repository
- ModelEngine API for compilation
- ModelEngine runtime for complete production mob features

Build and run all unit tests with:

```bash
./mvnw -B -ntp clean verify
```

Run the disposable degraded-mode Paper lifecycle with:

```bash
scripts/smoke-test.sh target/Devoria-1.0.jar
```

The smoke test requires Java 17, `curl`, and `sha256sum`. It accepts the EULA
only inside a temporary directory that is removed after the test.

## Change workflow

1. Branch from the latest `master`.
2. Keep one cohesive concern per pull request.
3. Add or update tests for pure logic and failure paths.
4. Run Maven verification and the Paper smoke test when runtime behavior changes.
5. Document configuration, data-format, permission, and migration effects.
6. Use an imperative commit subject that describes the outcome.

## Engineering rules

- Never commit credentials, tokens, private keys, or production connection
  strings. Use environment variables or ignored local configuration.
- Do not add blocking database, filesystem, or network work to the Paper server
  thread.
- Do not store new authoritative data in item display names or localized names.
  New item schemas belong in namespaced persistent data and must be versioned.
- Keep ModelEngine calls behind the integration boundary. Degraded startup is a
  recovery capability; ModelEngine remains required for full production features.
- World mutations and optional integrations must be explicit configuration.
- Validate external YAML, command arguments, serialized data, and content paths
  before use.
- Do not swallow exceptions. Report actionable context without exposing secrets.
- Prefer immutable domain values and pure functions over positional string lists,
  static mutable state, or Bukkit metadata as a long-term data model.
- Pin CI actions to immutable commits and retain least-privilege workflow
  permissions.

## Definition of done

A change is ready to merge when:

- `./mvnw -B -ntp clean verify` passes;
- relevant unit and runtime tests cover the behavior;
- the packaged plugin boots through the supported smoke-test path;
- permissions and failure messages are safe for their intended audience;
- configuration and stored-data compatibility are documented;
- no new secret, warning, main-thread I/O, or silent failure is introduced; and
- the pull request contains a concise verification note.

## Pull requests

Complete the repository pull-request template. Stacked pull requests must state
their dependency and be retargeted to `master` after the base change merges.
Avoid merging a stacked pull request into an already-merged feature branch,
because that does not place its commits on the default branch.
