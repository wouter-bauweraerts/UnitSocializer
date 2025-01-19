# UnitSocializer

## WIP
This project is still under development and is not ready to be used just yet! 
The minimum required features to provide value for your project haven't been completely implemented!
Watch this project and or follow me on social media for updates:
- [LinkedIn](https://www.linkedin.com/in/wouter-bauweraerts-938689108)
- [X (Twitter)](https://x.com/wbauweraerts)
- [BlueSky](https://bsky.app/profile/wbauweraerts.bsky.social)

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

## CI Status
![example workflow](https://github.com/wouter-bauweraerts/sociable-testing/actions/workflows/maven.yml/badge.svg)
## Sonarcloud status
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_sociable-testing&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_sociable-testing)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_sociable-testing&metric=coverage)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_sociable-testing)