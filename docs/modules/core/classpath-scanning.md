## Classpath scanning
UnitSocializer provides the possibility to find subtypes of an abstract type by performing a classpath scan.
Classpath scanning means that we investigate the classes that are present on the classpath.
The behavior of the classpath scan is the following:

### Single implementation present
When an abstract type has a single implementation present on the classpath, we return this type. 
UnitSocializer will then attempt to create an instance of this implementation, 
by recursively trying to populate the dependencies.

If everything succeeds, the instance of the retrieved implementation will be injected in your test unit.

### No implementations present
When the classpath scan returns no results, UnitSocializer will fail to initialize your test unit.
This probably means that you have a dependency issue in your project, resulting in some classes not being available
on the classpath.

### Multiple implementations present
When the classpath scan returns multiple results, UnitSocializer will fail to initialize your test unit.
UnitSocializer can not decide for you which concrete implementation you want to use.

Specify which implementation is the correct one by using
- [a predefined value](./predefined)
- [type resolution](./resolve)

> # Important note
> Classpath scanning is an expensive operation. 
> The time required to scan the entire classpath depends on the size of your project
> and the number of dependencies. We provide it only to avoid test failures when something changes.
> 
> In a small project (only using basic Spring dependencies), 
> UnitSocializer needed 20 seconds to run a test that used classpath scanning.
> The same test only took 500 **milliseconds** to complete when using type resolution or a predefined value.
> 
> This is why we advise to avoid the usage of classpath scanning as much as possible!
