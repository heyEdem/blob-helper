# Architectural Decisions

> ADR entries explain WHY. Detailed decision records live in `docs/adrs/`.

## Content identity includes size

**Date:** 2026-07-04  
**Why:** Byte-identical deduplication needs stable content identity across storage providers. The chosen identity is `hash_algorithm + content_hash + size_bytes`.  
**Tradeoffs:** Hashing must complete before duplicate detection can finish. Future hash algorithms require identity versioning.  
**Alternatives considered:** Hash-only identity was rejected because it drops the explicit size guard.

## Core stays provider-neutral

**Date:** 2026-07-04  
**Why:** The library must support S3, Azure Blob Storage, local filesystem storage, and future object stores without coupling business logic to any SDK.  
**Tradeoffs:** Provider modules require separate adapters and tests.  
**Alternatives considered:** A single module with all providers was rejected because it would force unused SDK dependencies on consumers.

## Applications own logical assets

**Date:** 2026-07-04  
**Why:** Every consuming app has different ownership, authorization, lifecycle, and business fields for logical assets. Blob Helper owns only reusable physical content metadata.  
**Tradeoffs:** Reconciliation requires an app-provided reference-count source.  
**Alternatives considered:** Library-owned logical asset tables were rejected as too opinionated.

## Duplicate uploads skip physical writes

**Date:** 2026-07-04  
**Why:** The main cost-saving behavior is avoiding repeated object-store writes and storage for identical bytes.  
**Tradeoffs:** Upload orchestration must handle duplicate-key races and storage/database ordering carefully.  
**Alternatives considered:** Letting storage adapters detect duplicates was rejected because reference counting is metadata behavior, not storage IO behavior.

## Reconciliation repair is opt-in

**Date:** 2026-07-04  
**Why:** Reference-count drift repair can mutate production metadata and must be explicit.  
**Tradeoffs:** Operators must choose when to repair instead of relying on automatic mutation.  
**Alternatives considered:** Scheduled repair enabled by default was rejected.

## Local dashboard uses self-registration and pull collection

**Date:** 2026-08-28
**Why:** Developers and operators need one lightweight read-only view across multiple local Blob Helper instances, including historical traffic contribution and deduplication savings.
**Tradeoffs:** The dashboard only observes instances while their local management endpoints are reachable, and the MVP is not suitable for remote deployment.
**Alternatives considered:** An embedded dashboard, push-only telemetry, and direct database/object-store access were rejected for the initial version because they increase deployment coupling, delivery complexity, or provider/schema coupling.

## Adopt Spring Boot 4.1 and Jackson 3

**Date:** 2026-09-06

**Why:** The project is still early enough to absorb Spring Boot 4's module and package changes before more consumers depend on the Boot 3 surface. The standalone dashboard therefore uses the Boot 4 MVC and Jackson starters and Jackson 3 APIs.

**Tradeoffs:** Boot 4 is a major upgrade; consumers and future integrations must use the Boot 4-compatible auto-configuration and JSON module layout.
**Alternatives considered:** Remaining on Spring Boot 3.5 was rejected because it would defer migration cost and leave the project on the older framework line while the public API is still forming.
