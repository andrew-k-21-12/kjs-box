Such structure helps to separate concerns, reuse endpoint values from a single place and reduce string allocations.
To propagate smaller collections of endpoints (which are represented by classes, not `object`s)
through all target child components, a dependency inversion can be used:
root components representing the scope for resources (collections) construct (assign) child components at their level
(which is also referred as "component composition").

The same can be achieved with dependency injection,
but the corresponding dependency injection component should have limited lifespan
(or there would be no difference with Kotlin `object`s).

Other (bad) alternatives:

- resources provided by webpack - type safety becomes lost, or some generator is needed; no ways to manage allocation
- wrapping values into functions - new types are needed anyway for classes with multiple properties, no shared usage
- Kotlin objects - have no usage scopes: their values can not be substituted in a fine-grained manner
- React context - an overkill for scoped resources as it's designed for global things, adds implicit coupling
- DI scopes - too cumbersome to configure and use, weak scopes bound to object availability are supported only in JVM
