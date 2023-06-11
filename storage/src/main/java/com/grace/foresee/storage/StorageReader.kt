package com.grace.foresee.storage

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

object StorageReader {
    fun read(path: String): String? {
        return readForBytes(path)?.takeIf { it.isNotEmpty() }?.let { String(it) }
    }

    fun read(inputStream: InputStream): String? {
        return readForBytes(inputStream)?.takeIf { it.isNotEmpty() }?.let { String(it) }
    }

    fun readForBytes(path: String): ByteArray? {
        File(path).also {
            if (it.canRead()) {
                try {
                    return readForBytes(FileInputStream(path))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    fun readForBytes(inputStream: InputStream): ByteArray? {
        return try {
            BufferedInputStream(inputStream).use { bis ->
                bis.readBytes()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}