## Summary

- What user, operator, or developer outcome does this change provide?

## Verification

- [ ] `./mvnw -B -ntp clean verify`
- [ ] Relevant unit tests added or updated
- [ ] Paper smoke test run for runtime changes
- [ ] Failure and degraded-mode paths exercised

## Compatibility and operations

- [ ] No configuration or stored-data change
- [ ] Configuration defaults and migration notes documented
- [ ] Permissions and operator messages reviewed
- [ ] ModelEngine/full-feature impact documented
- [ ] No new main-thread I/O, committed secret, or silent exception handling

## Stacking

- Base PR or dependency: none
- Retargeting required after the base merges: no
