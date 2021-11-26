# Instadog


# Tools

## Documentation

### Code Style
- Was used [Gitflow](http://datasift.github.io/gitflow/IntroducingGitFlow.html) for feature development process.
- Was used [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) for meaning commit messages.

### Architecture Decision Records (ADRs).
- Was used ADR tool to handle architectural decisions. It is possible to check the documentation and tools [here](https://github.com/npryce/adr-tools).

### How to test
#### UI/Integration tests

To run UI/Integration run the follow command with a running emulator:

```bash
./gradlew connectedAndroidTest
```