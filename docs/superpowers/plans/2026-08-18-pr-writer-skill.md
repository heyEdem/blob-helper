# PR Writer Skill Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:writing-skills and skill-creator to implement this plan. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Install and enforce a globally reusable `pr-writer` skill for Claude and Codex.

**Architecture:** Build one concise Codex skill with a generalized reference template, validate it, then copy the complete directory into Claude's native skills directory and verify byte parity. Use no-skill and with-skill evaluations to prove the output contract improves PR messages.

**Tech Stack:** Markdown Agent Skills, Codex skill-creator scripts, Claude Code skills, GitHub CLI

**Spec:** `docs/plans/2026-08-18-pr-writer-skill-design.md`

## Global Constraints

- Skill name is exactly `pr-writer`.
- Install native copies under `~/.codex/skills/pr-writer` and `~/.claude/skills/pr-writer`.
- Derive structure from `/Users/Edem/Documents/pr-kado-65.md` without retaining KADO-specific implementation details.
- Gather PR facts from the actual repository state and never invent verification results or issue metadata.
- Require `$pr-writer` in this repository's `AGENTS.md` and `CLAUDE.md`.

---

### Task 1: RED baseline

**Files:**
- Create: `docs/skill-evaluations/pr-writer-baseline.md`

- [x] Run five fresh-context PR-writing prompts without the skill.
- [x] Use the same raw change summary for each sample.
- [x] Record structural omissions and unsupported claims in a compact baseline report.
- [x] Confirm the baseline demonstrates inconsistent reviewer detail before authoring the skill.

### Task 2: Codex skill

**Files:**
- Create: `~/.codex/skills/pr-writer/SKILL.md`
- Create: `~/.codex/skills/pr-writer/references/pr-template.md`
- Create: `~/.codex/skills/pr-writer/agents/openai.yaml`

- [x] Read the skill-creator UI metadata reference.
- [x] Initialize `pr-writer` with the official `init_skill.py` script and a `references` directory.
- [x] Write trigger-only frontmatter that covers drafting, updating, reviewing, and creating pull requests.
- [x] Write a concise fact-gathering and output-shaping workflow in imperative form.
- [x] Add one generalized reviewer-oriented PR template and checklist.
- [x] Run `quick_validate.py` and fix every reported issue.

### Task 3: Claude installation

**Files:**
- Create: `~/.claude/skills/pr-writer/SKILL.md`
- Create: `~/.claude/skills/pr-writer/references/pr-template.md`
- Create: `~/.claude/skills/pr-writer/agents/openai.yaml`

- [x] Copy the validated Codex skill directory into Claude's native skill directory.
- [x] Compare sorted file lists and SHA-256 hashes to prove both installations are identical.
- [x] Validate the Claude copy with the same validator.

### Task 4: Repository enforcement

**Files:**
- Modify: `AGENTS.md`
- Modify: `CLAUDE.md`

- [x] Require `$pr-writer` before drafting or creating any PR.
- [x] Require the agent to gather actual commit, diff, test, branch, base, and issue facts before writing.
- [x] Keep the instruction concise and identical in both files.

### Task 5: GREEN forward tests and live use

**Files:**
- Create: `docs/skill-evaluations/pr-writer-forward-test.md`

- [x] Run five fresh-context prompts with the skill.
- [x] Compare every result against the structural contract and baseline failure modes.
- [x] Record pass/fail evidence without copying full generated PR bodies into the repository.
- [x] Use the skill contract to update GitHub PR #6.

### Task 6: Verification and delivery

- [x] Run both skill validators.
- [x] Re-run installation parity checks.
- [x] Run `git diff --check` and the repository's full Maven verification.
- [x] Commit repository instruction and evaluation artifacts.
- [x] Push the milestone branch so PR #6 contains the new policy and skill design artifacts.
- [x] Verify PR #6 title/body and report installed skill paths.
