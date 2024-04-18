// Configs.

/** Key to store HTTP responses. */
const RESPONSE_CACHE = "responses-v1";

/** 
 * Whether all static resources should be cached by this service worker.
 * 
 * Without this option the corresponding caching is backed by default browser means,
 * what leads to issues on attempts to open the application in the offline mode.
 * 
 * If the offline mode is not very important, 
 * disabling this option introduces more flexibility in caching statics, gives clearer network logs.
 */
const CACHE_STATICS_BY_SERVICE_WORKER = true;

/** URL prefix to determine all static resources to be cached by the service worker. */
const STATICS_URL_PREFIX = `${self.location.origin}/static/`;



// Fetching.

/** An entry point to handle all requests. */
const fetchStrategy = event => {
    const destination = getDestinationToBeProcessedByServiceWorker(event);
    if (destination) {
        event.respondWith(fetchModifiedWithFallbackToCached(destination));
    }
};

/**
 * Prepares a destination to be processed by the service worker 
 * or returns `null` if the provided `event` should not be handled by the service worker.
 */
const getDestinationToBeProcessedByServiceWorker = event => {
    // No referrer value implies an attempt to fetch the root `index.html`:
    // it can have different paths, but to avoid unnecessary requests for each of these paths,
    // only `/` will be used to fetch and cache its contents.
    if (!event.request.referrer) {
        return "/";
    }
    // Caching for static resources by the service worker's means - if enabled.
    if (CACHE_STATICS_BY_SERVICE_WORKER && event.request.url.startsWith(STATICS_URL_PREFIX)) {
        return event.request.url;
    }
    // All other types of requests will be processed by default browser means (including caching).
    return null;
};

/**
 * Tries to fetch a `destination` and cache it if it was modified,
 * returns its cached version if there are no updates.
 * 
 * It seems inevitable to perform at least one additional network request inside a service worker
 * for each response has been already fetched by the browser before the service worker's activation:
 * there is no straightforward way to share service worker caches with default browser ones
 * or get response data for some static request performed by the browser to fetch a resource.
 */
const fetchModifiedWithFallbackToCached = async destination => {
    const cachedResponse = await getCachedResponse(destination);
    const request        = createRequestWithProperCacheHeader(destination, cachedResponse);
    let networkResponse;
    try {
        networkResponse = await fetch(request);
    } catch (error) {
        if (!cachedResponse) {
            throw error; // there could be some other fallback instead of just propagating the error
        }
    }
    if (await cacheNetworkResponseIfModified(destination, networkResponse)) {
        return networkResponse;
    }
    return cachedResponse;
};

/**
 * If there is some `cachedResponse` available, 
 * creates a request with a header to fetch the `destination` only if it was modified.
 * 
 * Returns just a pure `destination` otherwise.
 */
const createRequestWithProperCacheHeader = (destination, cachedResponse) => {
    if (cachedResponse) {
        return new Request(destination, {
            headers: new Headers({
                "If-Modified-Since": cachedResponse.headers.get("Last-Modified")
            })
        });
    } else {
        return destination;
    }
};

/**
 * Caches a provided `networkResponse` only if its status implies that it was modified:
 * returns `true` in this case, `false` - otherwise.
 */
const cacheNetworkResponseIfModified = async (destination, networkResponse) => {
    if (networkResponse && networkResponse.ok) {
        await cacheResponse(destination, networkResponse.clone());
        return true;
    } else {
        return false;
    }
};



// Caching.

const getCachedResponse = async destination => { 
    return await (await openResponseCache()).match(destination);
};

const cacheResponse = async (destination, response) => {
    await (await openResponseCache()).put(destination, response);
};

const openResponseCache = async () => await caches.open(RESPONSE_CACHE);



// Service worker's setup.

// Seems like there is no reason to perform any caching during the installation phase,
// because methods like `Cache.add(...)` are still performing fetching under the hood
// and do not allow to avoid one more additional request per each resource expected to be cached
// after the browser has already fetched it.
self.addEventListener("fetch", fetchStrategy);
