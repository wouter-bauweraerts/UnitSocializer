## Configuring mock settings
In most projects we will have integrations with external systems.
You will have to connect to a database or a message broker in most projects.
Sometimes you will even have to execute calls to a remote application.

These are examples of things we do not want to include in our (sociable) unit tests.
To handle this, we provided a mocking feature that integrates with your preferred mocking library.

We can configure mocks on two ways, where we can also combine both. 
When combining two configurations, we can define the strategy to be used for this specific test.
Configuration can be done using a `unit-socializer-config.yaml` file or by annotating the test class with `@ConfigureMocking`.

You can configure mocks on 3 levels.
1. classes to mock
2. package to mock
3. annotations to mock

### Configuration using yaml file
The configuration of which types need to be mocked is relatively straightforward.
Add a file named `unit-socializer-config.yaml` to the root of your resources directory (`/src/test/resources`)
to configure what needs to be mocked.

**Classes to mock** defines which classes should be mocked by default. 
**Packages** describes the packages to check when instantiating a class. 
If a class is inside a defined package, it will be mocked.
With the configuration for **annotations** you can define the annotations that will be used. 
If a class is annotated with one of the annotations listed here, a mock will be provided instead of a real instance.

If you don't want to mock anything, you can just leave the configuration empty as shown below:
```yaml
unit-socializer:
    packages:
    classes:
    annotations:
```

#### Example configuration
Given the configuration below, we will mock the following:
- all classes within the package `com.example.amazing`
- the class `RemoteServiceClient` in `com.example.external`
- all classes annotated with the Spring `@Repository` annotation

```yaml
unit-socializer:
    packages:
      - "com.example.amazing"
    classes:
      - "com.example.external.RemoteServiceClient"
    annotations:
      - "org.springframework.stereotype.Repository"
```

### Configuration using `@ConfigureMocking` annotation
Just annotate the test class with `@ConfigureMocking` to configure what needs to be mocked.
This annotation takes 4 parameters:

**Classes to mock** defines which classes should be mocked by default.
**Packages** describes the packages to check when instantiating a class.
If a class is inside a defined package, it will be mocked.
With the configuration for **annotations** you can define the annotations that will be used.
If a class is annotated with one of the annotations listed here, a mock will be provided instead of a real instance.

The last parameter is the strategy to be used for this specific test.
Currently, we support two strategies: `REPLACE` and `MERGE`.
`REPLACE` will replace the default configuration with the one defined in the annotation.
`MERGE` will merge the default configuration with the one defined in the annotation.
This means that the default configuration will be used for classes that are not mocked in the annotation.`.

If you don't want to mock anything, simply define REPLACE as the strategy to be used.

#### Example configuration
Given the configuration below, we will mock the following:
- all classes within the package `com.example.amazing`
- the class `RemoteServiceClient` in `com.example.external`
- all classes annotated with the Spring `@Repository` annotation

```yaml
@ConfigureMocking(
  strategy = MockingStrategy.REPLACE,
  packages = "com.example.amazing",
  classes com.example.external.RemoteServiceClient.class,
  annotations = org.springframework.stereotype.Repository.class
)
@SociableTest
class MyAmazingSociableTest {
   // ...
}
```