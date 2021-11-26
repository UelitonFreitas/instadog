# 2. Implement Breed List Freature

Date: 2021-11-22

## Status

Accepted

Supercedes [1. Record architecture decisions](0001-record-architecture-decisions.md)

## Context

List Dogs Breeds.

## Decision

List Dogs Breeds and save it in a database for cache and offline access.
## Architecture
- MVVM was used with [LiveData](https://developer.android.com/topic/libraries/architecture/livedata?hl=pt-br) since it handle most of common Android problem to handle memory and lifecycle.
- [Navigation Component](https://developer.android.com/guide/navigation?gclid=Cj0KCQiAhf2MBhDNARIsAKXU5GQyMiHLieivc_O4pkXg3lBpK03Nl6sNT6HnOdnWmnTBfTO12vGxEcoaAldkEALw_wcB&gclsrc=aw.ds) was used for simplify navigation and tests.
- [Dagger](https://developer.android.com/training/dependency-injection/dagger-android?hl=pt-br) was used for Dependency Injection.

## Handle data
- Was used [Room](https://developer.android.com/jetpack/androidx/releases/room?gclid=Cj0KCQiAhf2MBhDNARIsAKXU5GTT-ggIMmUF3l3zysWeS0fVWpjw-GsTkFfOR7XQ4HqVyMX8Lxj0DGsaAtuoEALw_wcB&gclsrc=aw.ds) as Database.
- Was used [Retrofit](https://square.github.io/retrofit/) to get data from API.
- Was used [NetworkBoundResource](https://developer.android.com/jetpack/guide#addendum) to handle online and offline data.
  This method was chosen following Android Official documentation. Besides it could be a repository interface, but I choose to experiment this recommendation. =)

## Tests
- Was used [Espresso](https://developer.android.com/training/testing/espresso) for UI testing.
- Was used [Mockito](https://site.mockito.org/) for easily mock some classes and make it legible.
- Some utils classes were created to help deal with all Android View hierarchy. It is possible to find it in tests packages.
- Was necessary to create a notation to make some Android Classes available for tests. It is possible to find it in `com.hero.instadog.testing.OpenClass` package.
- A specific Activity class was create to handle UI tests for debug build. It was necessary to encapsulate and isolate each tested fragment.
- A new test runner was create for disable DI from tests and make possible to mock some classes.

## Tools
- [Timber](https://github.com/JakeWharton/timber) was used for best log handle.

## Consequences

A wonderful list of dogs can be seem with a great android app. =D 
