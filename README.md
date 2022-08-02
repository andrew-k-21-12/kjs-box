# My fellow brothers!

This is a training project to try Kotlin in Web development.
Don't take anything inside here seriously - lots of things are going to be rewritten eventually:
look for the `TODO`s in the code.
The project should not be huge and complicated - 
just big enough for a kind of sandbox covering the most important development use cases.

Have a nice time!

⣿⣿⣿⣿⡟⠛⠁⠄⠄⠄⠄⢀⣀⣀⠄⠄⠄⠄⣤⣽⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⡋⠁⠄⠄⠄⣠⣶⣾⣿⣿⣿⣿⠄⢦⡄⠐⠬⠛⢿⣿⣿⣿⣿⣿⣿
⣿⡿⠇⠁⠄⠄⣠⣾⣿⣿⡿⠟⠋⠁⠄⠄⠈⠁⠄⠄⠄⠄⠙⢿⣿⣿⣿⣿
⣿⠃⠄⠄⠄⠘⣿⣿⣿⣿⢀⣠⠄⠄⠄⠄⣰⣶⣀⠄⠄⠄⠄⠸⣿⣿⣿⣿
⣏⠄⠄⠄⠄⠄⣿⣿⣿⡿⢟⣁⠄⣀⣠⣴⣿⣿⠿⠷⠶⠒⠄⠄⢹⣿⣿⣿
⡏⠄⠄⠄⠄⢰⣿⣿⣿⣿⣿⣿⣿⣿⡟⠄⠛⠁⠄⠄⠄⠄⠄⠄⢠⣿⣿⣿
⡇⠄⠄⠄⠄⠈⢿⣿⣿⣿⣿⣿⣿⣿⡇⠄⣼⣿⠇⠘⠄⠁⠄⠄⠄⢻⣿⣿
⣇⠄⠄⠄⠄⠄⠸⢿⣿⣿⣿⣿⣿⣿⠁⠸⠟⠁⣠⣤⣤⣶⣤⠄⠄⠄⢻⣿
⣿⡄⠄⡤⢤⣤⡀⠈⣿⣿⣿⣿⣿⣿⡆⠄⠄⠘⠋⠁⠄⠄⠈⠄⠄⠄⢸⣿
⣿⣿⡜⢰⡾⢻⣧⣰⣿⣿⣿⣿⣿⣿⣷⠄⣼⣷⣶⣶⡆⠄⠄⠄⠄⠄⠄⣿
⣿⣿⣧⢸⠄⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣿⣿⣿⣿⠄⠄⠄⠄⠄⠄⠄⣿
⣿⣿⣿⣿⡿⢿⡟⠉⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⠄⠄⢀⡀⠄⠘⣿
⣿⣿⣿⣿⣿⣆⢻⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠋⠄⠄⠈⠁⠄⠄⣿
⣿⣿⣿⣿⣿⣿⡆⢻⣿⣿⣿⣿⣿⣿⡿⠛⠛⠛⠃⠄⠄⠄⠄⠄⠄⠄⢀⣿
⣿⣿⣿⣿⣿⣿⣿⣆⣻⣿⣿⣿⣿⣿⣷⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢸⣿


# Deployment

1. Enable the history API fallback (a fallback to the index page) to process SPA's routes correctly.

2. Use gzip content encoding for all responses.

3. Cache static sources (i.e. the ones with hashes in names) as much as possible,
cache static resources in some way (e.g. with ETag - as it can be difficult to handle hashes for them) as well
while keeping `index.html` always fresh.
