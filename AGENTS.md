## Codebase Index

This project has a living `docs/` folder with architecture, implementation, patterns, decisions, and changelog files.

### Session Start

- Read `docs/architecture.md` and `docs/implementation.md` before doing any work.
- These files contain the project map. Do not re-scan the codebase from scratch.

### Subagents

Subagents spawned via the Agent tool (Explore, Plan, general-purpose) do NOT inherit AGENTS.md. When writing any subagent prompt, include this line at the top:

> "This project has indexed docs. Read `docs/architecture.md` and `docs/implementation.md` first. They are the project map. Do not explore source files for information already covered there."

### After Every Feature or Bugfix

1. Run `git diff HEAD~1 --name-only` to identify changed files.
2. Re-scan only the changed files and their direct neighbors (same package/directory).
3. Update the relevant doc files using targeted edits. Do not rewrite unaffected sections:
   - New module/package: update `docs/architecture.md`, `docs/implementation.md`
   - New class/function/endpoint: update `docs/implementation.md`
   - Renamed files/folders: update `docs/architecture.md`, `docs/patterns.md`
   - New dependency: update `docs/architecture.md`
   - New naming/code pattern: update `docs/patterns.md`
4. Ask: "Did this change involve making or reversing an architectural decision?"
   - If yes, append an ADR entry to `docs/decisions.md`
   - If no, skip
5. Append a dated changelog entry to `docs/changelog.md`:

```markdown
## YYYY-MM-DD — [brief description]

- What changed
- Which modules were affected
```
