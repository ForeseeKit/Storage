package com.grace.foresee.storage

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

object StorageWriter {

    fun write(path: String, content: String, append: Boolean = false) {
        write(path, content.toByteArray(), append)
    }

    fun write(path: String, content: ByteArray, append: Boolean = false) {
        File(path).also {
            if (canWrite(it)) {
                try {
                    write(FileOutputStream(path, append), content)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun write(outputStream: OutputStream, content: String) {
        write(outputStream, content.toByteArray())
    }

    fun write(outputStream: OutputStream, content: ByteArray) {
        try {
            BufferedOutputStream(outputStream).use { it.write(content) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun canWrite(file: File): Boolean {
        var result = file.exists() && file.canWrite()

        if (result.not() && file.exists().not()) {
            // 通过创建目录或文件再删除的方式，检测是否可写
            try {
                if (file.isDirectory) {
                    result = file.mkdirs() && file.delete()
                } else {
                    file.parentFile?.also { parentDirectory ->
                        result = if (parentDirectory.exists().not()) {
                            parentDirectory.mkdirs() && file.createNewFile() && file.delete()
                        } else {
                            file.createNewFile() && file.delete()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return result
    }

}