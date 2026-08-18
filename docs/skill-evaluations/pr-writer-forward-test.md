# PR Writer Forward Evaluation

**Date:** 2026-08-18
**Initial samples:** 5 fresh agents with `pr-writer`

## Scenario

Each agent received the same JPA module evidence used for the baseline plus one of the two identical global skill installations. The prompt prohibited repository edits, tool use, and invented facts.

## Initial Results

| Required quality | Samples passing | Result |
|---|---:|---|
| Conventional `type(scope): title` | 5/5 | Every title used the required form. |
| Outcome-focused `Overview` | 5/5 | Every draft stated the module/entity outcome and non-goals. |
| Behavior-focused `What Changed` | 5/5 | Every draft explained module and mapping behavior instead of listing filenames. |
| Explicit named subsystem groups | 3/5 | Two drafts still collapsed multiple areas into one list. |
| `Implementation Notes` | 5/5 | Every draft explained the persistence boundary and deferred behavior. |
| Concrete named test coverage | 5/5 | Every draft retained the supplied test classes and cases. |
| Numbered `How to Test` | 5/5 | Every draft included the verified Maven command and expected result. |
| Branch/base metadata | 5/5 | Every draft included the supplied head and base. |
| Unsupported claims | 5/5 | No issue ID or unverified result was invented. |

## Refinement

The skill was tightened to require named `###` groups whenever more than one area changed. Two contract-adherent fresh reruns then used separate module and entity/mapping groups, satisfying the missing requirement.

Two additional attachment-based invocations did not follow the supplied skill at all and reproduced baseline-style `Summary`/`Testing` output. This is an invocation-compliance failure rather than an ambiguity in the skill contract; the repository-level `AGENTS.md` and `CLAUDE.md` rules therefore make `$pr-writer` mandatory before any pull request operation.

## Conclusion

When invoked, `pr-writer` consistently produces the reviewer-oriented title, evidence, structure, test procedure, and related metadata that the no-skill baseline omitted. Both global installations use the same validated files.
