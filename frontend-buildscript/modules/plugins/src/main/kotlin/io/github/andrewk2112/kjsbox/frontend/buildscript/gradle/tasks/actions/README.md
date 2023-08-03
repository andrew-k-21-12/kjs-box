The API Gradle provides to describe tasks requires abstract classes to be used.
This makes a generalization of similar tasks via inheritance mechanisms inconvenient:
in this case internal details become inevitable to be exposed to public.
Also, in lots of related cases preferring the inheritance over the composition 
can become a nightmare to support.

To tackle the described problems, there is a separation into tasks and actions.
The tasks are only responsible for declaring inputs and outputs and combining the actions,
which in turn perform all actual jobs for the tasks.
