# PR Writer Baseline Evaluation

**Date:** 2026-08-18
**Samples:** 5 fresh agents without `pr-writer`

## Scenario

Each agent received the same raw JPA module change summary, verified test totals, branch/base names, explicit non-goals, and mild time pressure. Agents were asked for a GitHub-ready title and body without reading additional repository content.

## Results

| Required quality | Samples passing | Baseline behavior |
|---|---:|---|
| Conventional `type(scope): title` | 0/5 | Used an unscoped sentence title. |
| Outcome-focused `Overview` | 0/5 | Replaced it with a flat `Summary` list. |
| Grouped `What Changed` analysis | 0/5 | Collapsed all implementation facts into one list. |
| Non-obvious implementation notes | 0/5 | Omitted rationale and module-boundary explanation. |
| Concrete named test coverage | 5/5 | Preserved all three supplied test cases and totals. |
| Numbered `How to Test` procedure | 0/5 | Reported results but gave no reproducible commands. |
| Related branch/base metadata | 0/5 | Omitted both supplied values. |
| Unsupported claims | 5/5 | No sample invented facts or issue IDs. |

## Baseline Failure

Generic PR drafting preserved factual accuracy but consistently produced a release-note summary rather than a reviewer-oriented PR message. The skill must positively define the expected document shape and fact-gathering contract while retaining the baseline's factual restraint.
