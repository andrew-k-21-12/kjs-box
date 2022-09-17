package io.github.andrewk2112.extensions

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Replaces all dots in the [String] with slashes.
 * */
internal fun String.dotsToSlashes(): String = replace(".", "/")

/**
 * Replaces all slashes in the [String] with dots.
 * */
internal fun String.slashesToDots(): String = replace("/", ".")

/**
 * Writes a [String] into the [file] - just a syntax sugar for [writeText].
 * */
@Throws(FileNotFoundException::class, SecurityException::class, IOException::class)
internal fun String.writeTo(file: File) = file.writeText(this)
