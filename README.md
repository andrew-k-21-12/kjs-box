# Deployment

1. Enable the history API fallback (a fallback to the index page) to process SPA's routes correctly.

2. Use gzip content encoding for all responses.

3. Cache static sources (i.e. the ones with hashes in names) as much as possible,
cache static resources in some way (e.g. with ETag - as it can be difficult to handle hashes for them) as well
while keeping `index.html` always fresh.
