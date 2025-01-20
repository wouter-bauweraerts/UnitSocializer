## Mock Configuration
Configure which components need to be mocked by adding a file named `unit-socializer.yaml` in the root of your resource directory.
Below you can find an example of a configuration.

1. Root element is `unit-socializer`
2. Supported elements are `packages`, `classes` and `annotations`
3. Provide the fully qualified name of the element you want to mock

### Example configuration
```yaml
unit-socializer:
    packages:
      - "org.springframework.data.jpa.repository"
    classes:
      - "org.springframework.web.client.RestTemplate"
    annotations:
      - " org.springframework.stereotype.Repository"
```