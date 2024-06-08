package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.imagemetadata

import io.github.andrewk2112.utility.bytes.decode2LittleEndianBytesToInt
import io.github.andrewk2112.utility.bytes.decode3LittleEndianBytesToInt
import io.github.andrewk2112.utility.common.utility.Result
import java.awt.Dimension
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

/**
 * Works with metadata only of WebP images.
 */
internal class WebpImageMetadataReader : ImageMetadataReader {

    // Implementation.

    override fun readDimension(image: File): Result<Dimension, Exception> =
        Result.runCatchingTypedException {
            checkWebpExtension(image)
            val imageHeader = readImageHeader(image)
            checkWebpHeader(image, imageHeader)
            when (val webpFormat = readWebpFormat(imageHeader)) {
                "VP8 " -> readVp8Dimension(imageHeader)
                "VP8X" -> readVp8xDimension(imageHeader)
                "VP8L" -> readVp8lDimension(imageHeader)
                else   -> throw IllegalArgumentException(
                              "Unknown WebP format \"$webpFormat\" is present in ${image.absolutePath}"
                          )
            }
        }



    // Private.

    /**
     * @throws IllegalArgumentException If [image]'s extension is not equal to ".webp".
     */
    @Throws(IllegalArgumentException::class)
    private fun checkWebpExtension(image: File) {
        if (!image.extension.equals("webp", true)) {
            throw IllegalArgumentException(
                "Images having extensions other than \".webp\" are not supported: ${image.absolutePath}"
            )
        }
    }

    /**
     * Reads a batch of first bytes containing all required metadata.
     */
    @Throws(FileNotFoundException::class, IOException::class, SecurityException::class)
    private fun readImageHeader(image: File): ByteArray = FileInputStream(image).use { it.readNBytes(30) }

    /**
     * @throws IllegalArgumentException If some required WebP header entry is not present in the provided [header].
     */
    @Throws(IllegalArgumentException::class, IndexOutOfBoundsException::class)
    private fun checkWebpHeader(image: File, header: ByteArray) {
        if (String(header, 0, 4) != "RIFF" || String(header, 8, 4) != "WEBP") {
            throw IllegalArgumentException(
                "An image doesn't have WebP headers present: ${image.absolutePath}"
            )
        }
    }

    /**
     * All WebP formats start with 12 bytes of a file header followed by 8 bytes of a chunk header.
     * The first 4 bytes of a chunk header contain an exact WebP format name.
     */
    @Throws(IndexOutOfBoundsException::class)
    private fun readWebpFormat(webpHeader: ByteArray) = String(webpHeader, 12, 4)

    /**
     * Simple File Format (Lossy) case -
     * see [VP8 Data Format and Decoding Guide](https://datatracker.ietf.org/doc/html/rfc6386#section-19.1).
     *
     * VP8 header is little-endian.
     *
     * Keep in mind that 2 bytes for `horizontal_size_code` and 2 bytes for `vertical_size_code`
     * contain not only width and height correspondingly, but also `horizontal_scale` and `vertical_scale`.
     * The size part itself is located inside the last 14 bits,
     * so the first 2 bits must be removed with the corresponding `0x3FFF` mask.
     */
    @Throws(IndexOutOfBoundsException::class)
    private fun readVp8Dimension(webpHeader: ByteArray) = Dimension(
        webpHeader.decode2LittleEndianBytesToInt(26) and 0x3FFF,
        webpHeader.decode2LittleEndianBytesToInt(28) and 0x3FFF
    )

    /**
     * Extended File Format case -
     * see [WebP Container Specification - Extended File Format](https://developers.google.com/speed/webp/docs/riff_container#extended_file_format).
     *
     * VP8X header is little-endian.
     *
     * The offsets for width and height are present in the link above.
     * Each side is represented by 3 bytes;
     * to get correct width and height, their original values should be increased by 1.
     */
    @Throws(IndexOutOfBoundsException::class)
    private fun readVp8xDimension(webpHeader: ByteArray) = Dimension(
        1 + (webpHeader.decode3LittleEndianBytesToInt(24)),
        1 + (webpHeader.decode3LittleEndianBytesToInt(27))
    )

    /**
     * Simple File Format (Lossless) case -
     * see [Specification for WebP Lossless Bitstream](https://developers.google.com/speed/webp/docs/webp_lossless_bitstream_specification#3_riff_header).
     *
     * VP8L header is little-endian.
     *
     * Width and height start at the byte number 21 (sum all header offsets described in points 1-6 in the link above)
     * and are placed consecutively taking 14 bits each.
     * To get correct width and height, their original values should be increased by 1.
     *
     * A little visualisation to understand how width and height are placed relatively to bytes:
     * ```
     *                           width |                                  height |
     * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 <- LE goes from here
     *           byte |                 byte |                  byte |                  byte |
     * ```
     */
    @Throws(IndexOutOfBoundsException::class)
    private fun readVp8lDimension(webpHeader: ByteArray): Dimension {
        val firstBytes = webpHeader.decode2LittleEndianBytesToInt(21)
        return Dimension(
            1 + (firstBytes and 0x3FFF), // everything excepting bits number 15 and 16 from the doc's visualisation
            1 + (
                    (webpHeader.decode2LittleEndianBytesToInt(23) and 0xFFF shl 2) or // bits 17-28 as the first part
                    (firstBytes and 0xC000 shr 14) // bits number 15 and 16 as the end of the height value
                )
        )
    }

}
