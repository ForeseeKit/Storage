package com.grace.foresee.storage.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.grace.foresee.storage.MediaStoreReader
import com.grace.foresee.storage.MediaStoreWriter
import com.grace.foresee.storage.MediaType
import com.grace.foresee.storage.StorageReader
import com.grace.foresee.storage.StorageWriter
import com.grace.foresee.storage.extensions.getAppCachePath
import com.grace.foresee.storage.extensions.getAppFilesPath

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filename = "hello.txt"
        val content = "Hello world!"

        StorageWriter.write(getAppCachePath(filename), content)
        StorageWriter.write(getAppFilesPath(filename), content)

        Log.i(TAG, "cache content: ${StorageReader.read(getAppCachePath(filename))}")
        Log.i(TAG, "files content: ${StorageReader.read(getAppFilesPath(filename))}")

        MediaStoreWriter.write(this, MediaType.DOWNLOADS, "text/plain", filename = filename, content = content)

        Log.i(TAG, "media content: ${MediaStoreReader.read(this, MediaType.DOWNLOADS, filename = filename)}")
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}