# PR Message Style

This guide captures Edem's preferred PR message style from prior PR writeups.

## Shape

Use this order by default:

```markdown
# type(scope): Short PR Title [ISSUE-ID]

## Overview

Short explanation of what the PR ships, why it matters, and the boundary of the change.
Mention important non-goals or unchanged areas when they prevent reviewer confusion.

---

## What Changed

Grouped, concrete breakdown of the implementation.

---

## Implementation Notes

Explain non-obvious technical choices, query shapes, boundaries, tradeoffs, or edge cases.

---

## Test Coverage

List unit, integration, acceptance, smoke, and lower-layer tests by name and what they assert.

---

## How to Test

Manual verification steps with commands, requests, expected statuses, and important payload checks.

---

## Notes

Optional. Use for follow-ups, known test plumbing issues, or rationale that should not block the PR.

---

## Related

- Closes ISSUE-ID
- Branch: `branch-name`
- Base: `dev`
```

## Title

Use a conventional commit style heading:

```markdown
# feat(admin): Platform Analytics API [KADO-66]
# refactor(merchant): Analytics Endpoint Split [KADO-68]
# feat(vendors): Add Vendor Product Schema
```

Pattern:

```text
type(scope): concise title [optional-ticket]
```

Common types:

- `feat`
- `refactor`
- `fix`
- `docs`
- `test`

## Overview

The overview should be 1-3 tight paragraphs.

It should answer:

- What is shipping?
- Who or what uses it?
- What boundary changed?
- What is intentionally unchanged or deferred?

Good patterns:

- "Adds..."
- "Ships..."
- "Replaces..."
- "This is schema/entity work only..."
- "`GET ...` is unchanged."
- "All endpoints are secured by..."

## What Changed

Prefer grouped subsections over a flat bullet dump.

Useful subsection names:

- `### New Files`
- `### Modified Files`
- `### New Endpoints`
- `### Endpoint Changes`
- `### Service Changes`
- `### Database`
- `### Constants`
- `### DTOs`
- `### DI Registration`
- `### Smoke Test`
- `### No Changes To`
- `### Deleted`

Use tables for files and API surfaces:

```markdown
| File | Module |
|------|--------|
| `SomeController.java` | admin |
| `SomeService.java` | admin/internal |
```

```markdown
| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/admin/analytics/summary` | All-time KPIs |
```

For modified files, use bold file names and explain the behavior:

```markdown
**`GiftCardService` + `GiftCardServiceImpl`**
Added two new public methods:
- `getPlatformCardStats()`
- `getPlatformCardTrend(granularity, from, to)`
```

## API Documentation

When a PR adds or changes endpoints, include:

- method and path
- auth/role requirements
- query params and defaults
- response examples for important endpoints
- status code expectations where useful

For larger API PRs, include JSON response examples.

## Implementation Notes

Use this section for the reasoning reviewers need.

Examples of what belongs here:

- avoiding N+1 queries
- why a query uses anonymous projections
- pagination mechanics
- default windows or date anchors
- "sold" or domain-specific semantics
- auth boundary rationale
- performance or index reasoning
- why something was not changed

Keep this factual and specific. Name the actual classes, methods, tables, indexes, or query behavior.

## Test Coverage

Group tests by layer.

Common group labels:

- `### Unit Tests`
- `### Service Unit Tests`
- `### Controller Integration Tests`
- `### Integration Tests`
- `### Acceptance Tests`
- `### Lower-Layer Tests Added`
- `### Live Smoke Test`

List test method names and what they assert:

```markdown
- `getSummary_returns403ForMerchantToken` — merchant JWT blocked
- `findMonthlyPlatformCardTrendCountsOnlySoldStatuses` — verifies sold-only status filtering
```

For many related tests, a table is acceptable:

```markdown
| Case | Asserts |
|------|---------|
| `AddNoteAsync` — happy path | Persists audit log and returns 201 |
```

## How to Test

Use numbered manual steps.

Include:

- setup commands
- auth/login instructions
- concrete requests
- expected status codes
- payload fields to verify
- DB verification SQL when relevant

Example style:

```markdown
1. Start infra and API:
   ```bash
   docker compose up -d postgres redis
   ./mvnw spring-boot:run
   ```
2. `GET /path` with `Authorization: Bearer <token>` — expect `200`.
3. Without auth — expect `401`. As a non-admin — expect `403`.
```

## Notes

Use notes sparingly.

Good uses:

- follow-up work
- known test fixture issue
- production behavior vs test plumbing
- accepted technical compromise
- why a non-blocking ideal was deferred

## Related

End with traceability metadata:

```markdown
## Related

- Closes KADO-66
- Branch: `kado-66-admin-analytics`
- Base: `dev`
- Blueprint step: `8.4`
```

Use only fields that apply.

## Voice and Tone

- Direct and concrete.
- Reviewer-oriented.
- No vague "various improvements" phrasing.
- Prefer exact names, routes, status codes, test names, and table/index names.
- Call out unchanged areas explicitly to reduce review scope.
- Mention follow-ups honestly without making the PR sound incomplete.

## Compact Template

```markdown
# type(scope): Title [ISSUE-ID]

## Overview

What this ships, why it matters, and what is unchanged or deferred.

---

## What Changed

### New Files

| File | Module |
|------|--------|
| `File.java` | module |

### Modified Files

**`ExistingFile`**
Describe exact behavior added or changed.

### No Changes To

- Existing contract or module intentionally left alone

---

## Implementation Notes

- Important technical choice and why.
- Important boundary, query, auth, or performance detail.

---

## Test Coverage

### Unit Tests

- `testName` — assertion summary

### Integration Tests

- `testName` — assertion summary

---

## How to Test

1. Run setup command.
2. Call endpoint or execute command — expect result.
3. Verify auth/error/edge behavior.

---

## Related

- Closes ISSUE-ID
- Branch: `branch-name`
- Base: `dev`
```
