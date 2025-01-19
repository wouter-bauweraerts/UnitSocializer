# Sociable Testing
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)
## WIP
This project is still under development and is not ready to be used just yet! 
The minimum required features to provide value for your project haven't been completely implemented!
Watch this project and or follow me on social media for updates:
- [LinkedIn](https://www.linkedin.com/in/wouter-bauweraerts-938689108)
- [X (Twitter)](https://x.com/wbauweraerts)
- [BlueSky](https://bsky.app/profile/wbauweraerts.bsky.social)

# Table of contents
- [Background](#background)
- [Usage](#usage)
- [Related efforts](#related-efforts)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [Build status](#build-status)
- [Sonarcloud status](#sonarcloud-status)
- [License](#license)

## Background
This library is all about writing simple, readable and qualitative tests that focus on behavior 
instead of focussing on the technical implementation.
Many tests are coupled to the implementation because they use mocking and stubbing 
to have control over interactions between classes. This is something you often see in Solitary Unit Tests.

This library focusses on writing Sociable Unit Tests instead. A solitary unit test may look like an integration test,
but there is a big difference! A sociable unit test is still a unit test, only with a bigger unit!
The main advantage of writing sociable tests is that they are more robust! They can still be green after refactoring,
just because they aren't bound to the implementation.

There is still some mocking or stubbing available, but only for controlling interactions with external systems,
systems that are out of our control.

[//]: # (Writing tests can be a hard job. Especially when you want to write good tests.)

[//]: # (I often see unit tests that don't test what they should be testing. Tests should focus on testing **behavior**, )

[//]: # (instead of testing that the current implementation is technically correct.)

[//]: # (Why? We want to know that a feature works as it should be working. We want to know that for the current code, )

[//]: # (but preferably we want this test to validate that our feature still behaves as it was initially developed &#40;and validated&#41;.)

[//]: # ()
[//]: # (To be able to do so, we want to avoid using mocks too much. )

[//]: # (And this is the root cause of a problem many projects face nowadays. )

[//]: # (Many developers &#40;especially starters&#41; don't know how to write a proper unit test anymore! )

[//]: # (They don't care about the fact that their test will break if someone else refactors their code!)

[//]: # ()
[//]: # (Or maybe they do care, but they don't want to do the effort of writing a decent test. )

[//]: # (As long as the code is covered by a test and my coverage goals are met, it's all good!)

[//]: # ()
[//]: # (I'm not a fan of this mentality. I want my test to work, and I want it to keep working &#40;unless someone breaks the feature obviously&#41;!)

[//]: # (To improve my tests I started reading about **Sociable Unit Tests**. If you don't know them, make sure to read [this post written by Martin Fowler]&#40;https://martinfowler.com/bliki/UnitTest.html&#41;.)

[//]: # (Disregarding the fact that I like the concept of sociable tests a lot, )

[//]: # (I find them hard to write, especially in large scale projects.)

[//]: # ()
[//]: # (Why is that? I don't want my sociable unit tests to have the same setup as my integration tests. )

[//]: # (If I'm using an application framework &#40;Quarkus, Spring-Boot, ...&#41; for my application, )

[//]: # (I don't want to have to spin up the application context to run my **unit** tests!)

[//]: # (That's why I started this project!)

## Usage
Please check the documentation site for a detailed usage description. (WIP: page does not exist yet)
To start using the project, please add the following to your pom.xml (Maven users)

```xml
<dependency>
    <groupId>io.github.wouter-bauweraerts</groupId>
    <artifactId>sociable-testing-junit-mockito</artifactId>
    <version>1.0</version> <!-- TODO nothing released yet! -->
</dependency>
```

[//]: # (TODO add the correct link!)
Check [the Maven Central Repository](https://central.sonatype.com/artifact/io.github.wouter-bauweraerts/instancio-fixture-builder) for the most recent version.
There you can also find how to include it with different build systems.
## Related efforts
- [Instancio Fixture Builder](https://wouter-bauweraerts.github.io/instancio-fixture-builder/)
## Maintainers
- [Wouter Bauweraerts](https://github.com/wouter-bauweraerts)
## Contributing
Feel free to dive in! Missing a feature of encountering a problem? Feel free to open an issue!
Want to contribute? Contact us!
## Build status
![GH Build status](https://github.com/wouter-bauweraerts/sociable-testing/actions/workflows/maven.yml/badge.svg)
## Sonarcloud status
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_sociable-testing&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_sociable-testing)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_sociable-testing&metric=coverage)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_sociable-testing)

## License
[MIT](LICENSE) © Wouter Bauweraerts