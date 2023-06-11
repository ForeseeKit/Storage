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

        val content = "Hello world!"

        // Read or write for cache
        "test.txt".also { filename ->
            StorageWriter.write(getAppCachePath(filename), content)
            StorageWriter.write(getAppFilesPath(filename), content)

            Log.i(TAG, "from cache: ${StorageReader.read(getAppCachePath(filename))}")
            Log.i(TAG, "from files: ${StorageReader.read(getAppFilesPath(filename))}")
        }

        // Read or write for media store
        // For Downloads
        "test.txt".also { filename ->
            MediaStoreWriter.write(
                this,
                MediaType.DOWNLOADS,
                "text/plain",
                filename = filename,
                content = content
            )
            Log.i(
                TAG,
                "from Downloads: ${
                    MediaStoreReader.read(
                        this,
                        MediaType.DOWNLOADS,
                        filename = filename
                    )
                }"
            )
        }

        // For Documents
        "test.txt".also { filename ->
            MediaStoreWriter.write(
                this,
                MediaType.DOCUMENTS,
                "text/plain",
                filename = filename,
                content = content
            )
            Log.i(
                TAG,
                "from Documents: ${
                    MediaStoreReader.read(
                        this,
                        MediaType.DOCUMENTS,
                        filename = filename
                    )
                }"
            )
        }

        // For Images
        "test.png".also { filename ->
            MediaStoreWriter.write(
                this,
                MediaType.IMAGES,
                "image/*",
                filename = filename,
                content = content
            )
            Log.i(
                TAG,
                "from Images: ${MediaStoreReader.read(this, MediaType.IMAGES, filename = filename)}"
            )
        }


        // For Video
        "test.mp4".also { filename ->
            MediaStoreWriter.write(
                this,
                MediaType.VIDEO,
                "video/mp4",
                filename = filename,
                content = content
            )
            Log.i(
                TAG,
                "from Video: ${MediaStoreReader.read(this, MediaType.VIDEO, filename = filename)}"
            )
        }

        // For Audio
        "test.mp3".also { filename ->
            MediaStoreWriter.write(
                this,
                MediaType.AUDIO,
                "audio/mp3",
                filename = filename,
                content = content
            )
            Log.i(
                TAG,
                "from Audio: ${MediaStoreReader.read(this, MediaType.AUDIO, filename = filename)}"
            )
        }

    }

    companion object {
        private const val TAG = "MainActivity"
    }

}