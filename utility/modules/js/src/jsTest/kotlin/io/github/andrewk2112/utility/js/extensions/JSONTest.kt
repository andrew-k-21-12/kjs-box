package io.github.andrewk2112.utility.js.extensions

import kotlin.test.Test
import kotlin.test.assertEquals

class JSONTest {

    @Test
    fun testMinify() {
        arrayOf(
            "{\"key\" : \n  \"value 1\"}"        to "{\"key\":\"value 1\"}",
            "{\"key\" : \n  123}"                to "{\"key\":123}",
            "{\"key\" : \n  true}"               to "{\"key\":true}",
            "{\"nested\": {  \n \"key\" : 123}}" to "{\"nested\":{\"key\":123}}",
            "{\"array\": [1, 2,\n3 , 4]}"        to "{\"array\":[1,2,3,4]}"
        ).forEach { (source, expected) ->
            assertEquals(expected, JSON.minify(source))
        }
    }

}
