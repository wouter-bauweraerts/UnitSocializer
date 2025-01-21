## Resolving subtypes

Very often you don't want your code to rely on specific implementations, 
you inject an interface or abstract type instead of a concrete implementation.
This allows you to easily change the implementation with minimal code changes.

If you rely on abstract types, or concrete types with subclasses, 
you want to be able to inject an instance of the desired implementation.
You want to achieve this with minimal effort, that's why you decided to use UnitSocializer.

UnitSocializer provides the option to configure type resolvers in the `@TestSubject` annotation.
There is a typeResolvers attribute present, which takes an array of `@Resolve` anotations as value.
By default we provide an empty array.

The code below demonstrates how you can do this by using type resolvers.
> For this example you may assume that the configration to mock repositories is present!

```java
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /*
            This service has two dependencies
            - a productRepository that will connect to the database
            - a productMapper (which is an interface, using Mapstruct for example)
     */
    
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Optional<ProductDto> getProduct(int id) {
        return productRepository.findById(id)
                .map(productMapper::mapProduct);
    }
}
```

Now let's write the tests!
As you can see in the code below, we want to test our ProductService.
We manually instantiate the ProductMapper to have full control.
This instance will be injected into the ProductService instance by the UnitSocializer framework.

```java
@SociableTest
class ProductServiceTest {

    /*
            we configure which types need to be resolved by using the 
            typeResolvers attribute of the @TestSubject annotation.
     */
    @TestSubject(
            typeResolvers = {
                    @Resolve(forClass = ProductMapper.class, use = ProductMapperImpl.class)
            }
    )
    ProductService productService;

    @InjectTestInstance
    ProductRepository productRepository;

    @Test
    void getProduct_givenProductExists_returnsExpeted() {
        ProductEntity productEntity = ProductEntityFixtures.aProductEntity();
        ProductDto productDto = new ProductDto(
                productEntity.getId(),
                productEntity.getBrand(),
                productEntity.getType(),
                productEntity.getDescription(),
                productEntity.getUnitPrice()
        );

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(productEntity));

        assertThat(productService.getProduct(productEntity.getId())).contains(productDto);
    }
    
    // ...
}
```

If you only add one resolver, you can simplify this as follows:
 ```java
@TestSubject(
    typeResolvers = @Resolve(
        forClass = ProductMapper.class, use = ProductMapperImpl.class
    )
)
ProductService productService;
```

> The setup with predefined is not required. We also provide alternative approaches;
> - [Classpath scanning](./classpath-scanning)
> - [Predefined values](./predefined)