# 2. Implement Breed List Freature

Date: 2021-11-22

## Status

Accepted

Supercedes [1. Record architecture decisions](0001-record-architecture-decisions.md)

## Context

The issue motivating this decision, and any context that influences or constrains the decision.

## Decision

## Handle data
- Was used [NetworkBoundResource](https://developer.android.com/jetpack/guide#addendum) to handle online and offline data.
This method was chosen following Android Official documentation. Besides, it could be a repository interface, but i choose to experiment this recommendation.
  
[reference](https://developer.android.com/jetpack/guide)

The change that we're proposing or have agreed to implement.

## Tests
- Was used [Espresso](https://developer.android.com/training/testing/espresso) for UI testing.
- Was used [Mockito](https://site.mockito.org/) for easily mock some classes and make it legible.
- Some utils classes were created to help deal with all Android View hierarchy. It is possible to find it in tests packages.
- Was necessary to create a notation to make some Android Classes available for tests. It is possible to find it in `com.hero.instadog.testing.OpenClass` package.
- A specific Activity class was create to handle UI tests for debug build. It was necessary to encapsulate and isolate each tested fragment.
- A new test runner was create for disable DI from tests and make possible to mock some classes.


## Consequences

What becomes easier or more difficult to do and any risks introduced by the change that will need to be mitigated.
