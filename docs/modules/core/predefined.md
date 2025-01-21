## Predefined test instances

In some cases you'll want to be able to define the instance that your test will use yourself.
This could be particularly useful when you have a class that does some calculation.
Especially when you need to provide some inputs to the constructor that will be used for these calculations.

To allow this, we added the `@Predefined` annotation. 
This allows you to create the instance that will be used in your test unit before creation,
giving you full control on how the instance is created. 
This facilitates easy assertions on the behavior of the entire unit

The code below demonstrates how you can do this. 
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
    
    @Predefined // <-- dependency that you want to instantiate yourself
    ProductMapper productMapper = new ProductMapperImpl();

    @TestSubject
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

> The setup with predefined is not required. We also provide alternative approaches;
> - [Classpath scanning](./classpath-scanning)
> - [Type resolution](./resolve)