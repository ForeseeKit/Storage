package com.grace.foresee.storage

import java.io.File

object StorageEraser {

    fun erase(path: String) = erase(File(path))

    fun erase(file: File): Boolean {
        if (file.exists()) {
            return file.delete()
        }
        return false
    }

}