package io.github.andrewk2112.utility.bytes

/**
 * Reads 2 bytes starting from the [startIndex]
 * and combines them into an integer value taking the second byte as the first part of the result number.
 */
@Throws(IndexOutOfBoundsException::class)
fun ByteArray.decode2LittleEndianBytesToInt(startIndex: Int): Int =
    get(startIndex).toIntWithoutSign() or (get(startIndex + 1).toIntWithoutSign() shl 8)

/**
 * Reads 3 bytes starting from the [startIndex]
 * and combines them into an integer value
 * taking the last byte as the first part of the result number
 * and the first byte as the last part of it.
 */
@Throws(IndexOutOfBoundsException::class)
fun ByteArray.decode3LittleEndianBytesToInt(startIndex: Int): Int =
    decode2LittleEndianBytesToInt(startIndex) or (get(startIndex + 2).toIntWithoutSign() shl 16)

/**
 * Converts a [Byte] to [Int] with dropping the sign value.
 */
private fun Byte.toIntWithoutSign(): Int = toInt() and 0xFF
