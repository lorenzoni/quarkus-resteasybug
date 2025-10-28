# Bug in ResteasyReactiveProcessor

Quarkus Version 3.27.0

When implementing a REST service (resource) that uses a **JAX-RS sub-resource**, 
and you inject a `@RestClient` either in the main resource or in the sub-resource,
the service always responds with **“405 Method Not Allowed”** instead of **“200 OK”**.

## Steps to Reproduce

### Bug Example

An example using an injected `@RestClient` — which fails:

```
GET http://localhost:8080/bugs/sub
```

Results in:

```
405 Method Not Allowed
```

You can also run the test `BugsResourceTest`.
Once the bug is fixed, this test will return **OK**.

### Working Example

An example without an injected `@RestClient` — which works as expected:

```
GET http://localhost:8080/works/sub
```

Results in:

```
200 OK
```

## Root Cause

The issue originates in the class `ResteasyReactiveProcessor` within the Maven module `quarkus-rest-deployment`.

A performance optimization was introduced in this class:

```java
// Provides a predicate for filtering classes/methods that have annotations from one of the client
// packages. This only reduces the false positives as a "base" interface could be derived and
// client-related annotation applies. Although it seems unlikely that an endpoint without any
// client annotations violates the specification for server resources methods; this type of false
// positive would only mean needless processing as there would be no exception thrown.
```

For class filtering, the method
`classInfo.annotations().stream().anyMatch(knownClientAnnotation)`
is used.

However, this method returns **all annotations used anywhere within the resource class**,
instead of **only the annotations that are present at the class level**.

ResteasyReactiveProcessor:

```
          if (classInfo.annotations().stream().anyMatch(knownClientAnnotation)
                            || method.annotations().stream().anyMatch(knownClientAnnotation)
                            || method.parameters().stream().flatMap(p -> p.annotations().stream())
                                    .anyMatch(knownClientAnnotation)) {
                        continue;
          }
```

should be


classInfo.asClass().declaredAnnotations()

```
          if (classInfo.asClass().declaredAnnotations().stream().anyMatch(knownClientAnnotation)
                            || method.annotations().stream().anyMatch(knownClientAnnotation)
                            || method.parameters().stream().flatMap(p -> p.annotations().stream())
                                    .anyMatch(knownClientAnnotation)) {
                        continue;
          }
```

