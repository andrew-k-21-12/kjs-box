Any route is intended to help manage paths (both relative and absolute) and bound parameters (both URL and search ones),
nothing else should be included into the scope of routes.

Logic between various routes can be quite different,
so it's better to avoid inheritance and use compositions or interface-based mutators as much as possible instead
until there is no clear picture about routes' common features and relations.
