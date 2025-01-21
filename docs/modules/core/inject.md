## Injecting an instance in your test

At some point you'll need an instance of a (transitive) dependency in your test.
You'll need to be able to stub method calls with repositories or verify interactions with a specific method in your test.

One way you could achieve this, is by using a [predefined value](./predefined), but that's far from optimal.

To simplify this, UnitSocializer provides the `@InjectTestInstance` annotation.
After initializing the test unit, UnitSocializer will check for fields annotated with this annotation.
If an instance is found, UnitSocializer will inject it into your test.

The injected value will be a mock or a spy (using the mocking library you're working with), 
to allow stubbing method calls and/or verifying interactions.
- if the instance is a mock, the mock will be returned
- if the instance is not a mock, a spy will be returned

### Example
In the example below, you can see that the creation of a test unit, being the ProductService and its dependencies.
The ProductService dependes on the ProductRepository to retrieve product information.
Because you don't want to interact with a 'real' database in your unit tests, 
you may assume that UnitSocializer has been configured to mock this repository.

You can see that we stub de findById method in the test to return an empty Optional.
```java
@SociableTest
class ProductServiceTest {

    @TestSubject
    ProductService productService;

    @InjectTestInstance
    ProductRepository productRepository;

    @Test
    void getProduct_givenProductDoesNotExist_returnsEmpty() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThat(productService.getProduct(Instancio.create(Integer.class))).isEmpty();
    }
    // ...
}
```