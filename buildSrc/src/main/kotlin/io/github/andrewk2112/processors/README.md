The API Gradle provides to describe tasks requires abstract classes to be used.
This makes a generalization of similar tasks via inheritance mechanisms inconvenient:
in this case internal details become inevitable to be exposed to public.

To tackle the described problem, there is a separation into tasks and processors.
The tasks are only responsible for declaring inputs and outputs.
The processors in this package perform all actual jobs for the tasks.
