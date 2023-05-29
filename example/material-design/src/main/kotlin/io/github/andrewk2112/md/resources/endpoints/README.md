Such structure helps to separate concerns, reuse endpoint values from a single place and reduce string allocations.
To propagate smaller collections of endpoints (which are represented by classes, not objects) 
through all target child components, a dependency inversion can be used:
root components representing the scope for resources (collections) construct (assign) child components at their level
(which is also referred as "component composition").

Other alternatives:
- resources provided by webpack - type safety becomes lost, or some generator is needed; no ways to manage allocation
- wrapping values into functions - new types are needed anyway for classes with multiple properties, no shared usage
- Kotlin objects - must be allocated all the time - have no usage scopes; objects' values are not inlined
- React context - an overkill for scoped resources as it's designed for global things, adds implicit coupling
- DI scopes - too cumbersome to configure and use, weak scopes bound to object availability are supported only in JVM
