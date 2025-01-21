---
title: UnitSocializer - Home
sidebar: toc
---

# UnitSocializer

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_sociable-testing&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_sociable-testing)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_sociable-testing&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_sociable-testing)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_sociable-testing&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_sociable-testing)

## Context
Writing tests can be a hard job. Especially when you want to write good tests.
I often see unit tests that don't test what they should be testing. Tests should focus on testing **behavior**, 
instead of testing that the current implementation is technically correct.
Why? We want to know that a feature works as it should be working. We want to know that for the current code, 
but preferably we want this test to validate that our feature still behaves as it was initially developed (and validated).

To be able to do so, we want to avoid using mocks too much. 
And this is the root cause of a problem many projects face nowadays. 
Many developers (especially starters) don't know how to write a proper unit test anymore! 
They don't care about the fact that their test will break if someone else refactors their code!

Or maybe they do care, but they don't want to do the effort of writing a decent test. 
As long as the code is covered by a test and my coverage goals are met, it's all good!

I'm not a fan of this mentality. I want my test to work, and I want it to keep working (unless someone breaks the feature obviously)!
To improve my tests I started reading about **Sociable Unit Tests**. If you don't know them, make sure to read [this post written by Martin Fowler](https://martinfowler.com/bliki/UnitTest.html).
Disregarding the fact that I like the concept of sociable tests a lot, 
I find them hard to write, especially in large scale projects.

Why is that? I don't want my sociable unit tests to have the same setup as my integration tests. 
If I'm using an application framework (Quarkus, Spring-Boot, ...) for my application, 
I don't want to have to spin up the application context to run my **unit** tests!
That's why I started this project!

## Focus on behavior
We want our tests to be as **robust** as possible. 
This is something we want to do without too much effort.
I see many developers struggle with this, because they bind their tests to the **technical implementation** of the feature.

This coupling is done by relying on mocking libraries to simplify their test setup.
They misunderstand the definition of a unit test. They think a unit has to be a single class.
This leads to testing the code of a single class and mocking all the classes on which their class under test relies on.

This type of test is called a **solitary unit test**. 
A solitary unit test is prone to break when the structure of your code changes.
That's why I try to avoid solitary tests as much as possible.

When writing tests, I want to know that the result a piece of code returns is what I expect.
I want that my current code returns what I want, but I don't want my test to start failing when I refactor my code 
or when I add new features to it.

This is when **sociable unit tests** enter the stage! Sociable tests embrace the existence of dependencies in your code.
They don't mock the classes on which our test subject depends. 
They accept the dependency and use a real implementation instead of a mock.
However, some parts of the code will be mocked.
We will be mocking things that our out of our control. We will not interact with external systems or with a real database.
Why not? We want our tests to be as fast as possible, because we are still writing unit tests!

## Keep it simple!
Setting up a sociable unit test can be a pain in the ***! 
Especially in larger projects, where we have many internal dependencies.
Setting up your test unit can be labour intensive and prone to change.
That is why I started writing this framework!

With this framework, 
we can simply create our test unit with a few annotations and everything will be wired together automagically!

This reduces the effort of writing tests and allows us as a developer to focus on what really matters... 
the **expected behavior**!

## Usage
You can simply use your preferred build tool to include the required dependencies in your project.
Check the specific [module page](#modules) for usage details. 

## How does it work
This framework simplifies the setup of Sociable Unit Tests by providing extensions for testing frameworks.
It allows you to easily setup a sociable test by simply adding some annotations to configure your test setup.
When you start your tests, the framework detects which class you are testing via te `@TestSubject` annotation.

We use **reflection** to investigate how we can instantiate this class and which dependencies it needs.
We instantiate the class and all of its dependencies.
We **avoid using mocks**, however some components will need to be mocked.
You can easily [configure](./modules/core/mock-configuration) what needs to be mocked by adding a `unit-socializer.yaml` file in the root of your resources folder.

You can easily add [predefined instances](modules/core/predefined.md) to your test context, which will be used while creating your test unit.

We also provide the possibility to [inject a reference to a dependency within your testing unit.](modules/core/inject.md)

In Object-Oriented programming, we have the concept of inheritance. 
T his is something we address on two ways.
- Classpath scanning for classes that implement a given interface or extend a given (abstract) class.
- Because classpath scanning is an expensive operation, we also provide the possibility to [preconfigure](modules/core/resolve.md) which specific type needs to be used for a given supertype.

## Modules
The project is a multi-module project. 
Not all module is suitable for direct use, but they are reused within other modules, to avoid code duplication.
- unit-socializer-jacoco
    - Not intended to be used. Aggregates coverage data
- unit-socializer-core [JavaDoc](https://www.javadoc.io/doc/io.github.wouter-bauweraerts/unit-socializer-core/latest/index.html){:target="_blank"}
  - Not intended for separate use.
  - Backbone of the testing framework
  - Provides core features, which are reused in other modules
- [unit-socializer-junit-mockito](./modules/junit-mockito/us-junit-mockito)

## Releases

| Version | Release Date |
|--------|--------------|
| 0.0.1  | Jan 20 2025  |
| 0.0.2  | Jan 21 2025  |


## Changelog
### 0.0.1
- Initial setup and MVP functionality
- Includes module unit-socializer-junit-mockito

### 0.0.2
- Include default config in jar

## Problems / Feature Requests
If you encounter an issue of if you're missing an (important) feature,
please [submit a GitHub Issue](https://github.com/wouter-bauweraerts/UnitSocializer/issues)

## License
[MIT](https://github.com/wouter-bauweraerts/UnitSocializer/blob/b103c55c3e7b1bd1617035b37da96cca051b4da7/LICENSE){:target="_blank"} ©2025 Wouter Bauweraerts
