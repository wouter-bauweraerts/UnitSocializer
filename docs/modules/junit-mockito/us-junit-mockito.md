## UnitSocializer jUnit-Mockito
Module to be used in combination with the jUnit testing framework,
combined with Mockito as your mocking provider.

### Installation
To use UnitSocializer jUnit-Mockito, you will need to add only one dependency!
```xml
<dependency>
    <groupId>io.github.wouter-bauweraerts</groupId>
    <artifactId>unit-socializer-junit-mockito</artifactId>
    <version>${unit-socializer.version}</version> <!-- Set correct version here on in the properties --> 
    <scope>test</scope>
</dependency>
```
Please check [Maven Central Repository](https://central.sonatype.com/artifact/io.github.wouter-bauweraerts/unit-socializer-junit-mockito){:target="_blank"} for the latest release version!

### Compatibility info
The unit-socializer-junit-mockito module requires dependencies on jUnit and Mockito.
I guarantee that it works with this version, using other versions may or may not cause problems.

| UnitSocializer     | junit-jupiter | mockito | JDK                         |
|--------------------|---------------|---------|-----------------------------|
| 1.0.0              | 5.11.4        | 5.15.2  | 17 - 21 (tested on Temurin) |
| 1.1.0              | 5.11.4        | 5.20.0  | 17 - 25 (tested on Temurin) |
| 1.2.0 (unreleased) | 6.0.1         | 5.20.0  | 17 - 25 (tested on Temurin) |

### Usage
The UnitSocializer jUnit-Mockito module provides a Sociable Test extension built upon jUnit 5.
All you have to do to make your test use this extension, is annotate your test class with the `@SociableTest` annotation.

The `@SociableTest` annotation also requires an `@TestSubject` annotated field to be present in your test.
This field defines your test unit, which describes the part of your code you want to test in this test class.

### Examples

#### Minimal setup example
Below you can find a simple example, where the class has no actual dependencies. 
As mentioned in the [Usage](#usage) section, you only need to add 2 annotations to make your test run.

```java
public enum CountryShippingCost {
    BELGIUM(BigDecimal.ONE, BigDecimal.ZERO),
    // ...
    US(BigDecimal.valueOf(120), BigDecimal.valueOf(100));

    final BigDecimal shippingCost;
    final BigDecimal noShippingCostMinimum;

    CountryShippingCost(BigDecimal shippingCost, BigDecimal noShippingCostMinimum) {
        this.shippingCost = shippingCost;
        this.noShippingCostMinimum = noShippingCostMinimum;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public boolean isAboveMinimum(BigDecimal totalPrice) {
        return totalPrice.compareTo(noShippingCostMinimum) >= 0;
    }
}
```
```java
// package declaration and imports omitted
public class ShippingCostCalculator {
    public BigDecimal calculateShippingCost(BigDecimal totalPrice, String shipToCountryCode) {
        CountryShippingCost countryShippingCost = CountryShippingCost.valueOf(shipToCountryCode);

        return countryShippingCost.isAboveMinimum(totalPrice)
                ? BigDecimal.ZERO
                : countryShippingCost.getShippingCost();
    }
}
```

The code below shows how to write a test for the code examples above
```java
// package declaration and imports omitted

@SociableTest // <-- annotate your test class with @SociableTest
class ShippingCostCalculatorTest {
    @TestSubject // <-- annotate your system under test with @TestSubject
    ShippingCostCalculator shippingCostCalculator;

    @ParameterizedTest
    @MethodSource("shippingCostSource")
    void calculateShippingCost(CountryShippingCost country, BigDecimal value, BigDecimal expected) {
        assertThat(shippingCostCalculator.calculateShippingCost(value, country.name())).isEqualTo(expected);
    }
    
    // shippingCostSource method omitted
}
```
This minimal setup will also work if your class has dependencies on concrete implementations.
If you need to configure components that you want to mock, check the [mock configuration example](../core/mock-configuration)

#### More examples
- [Using predefined values](../core/predefined)
- [Resolving types](../core/resolve)
- [Injecting generated instances](../core/inject)

## JavaDoc
Latest JavaDoc can be found [here](https://www.javadoc.io/doc/io.github.wouter-bauweraerts/unit-socializer-junit-mockito/latest/index.html){:target="_blank"}