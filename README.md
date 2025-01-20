# UnitSocializer
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
![GH Build status](https://github.com/wouter-bauweraerts/UnitSocializer/actions/workflows/maven.yml/badge.svg)
## Sonarcloud status
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_sociable-testing&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_sociable-testing)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_sociable-testing&metric=coverage)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_sociable-testing)

## License
[MIT](LICENSE) © Wouter Bauweraerts