package com.grace.foresee.storage.utils

import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.grace.foresee.storage.MediaType
import java.io.File

object MediaStoreUtil {
    @RequiresApi(Build.VERSION_CODES.Q)
    fun getExternalContentUri(mediaType: MediaType): Uri {
        return when (mediaType) {
            MediaType.AUDIO -> MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            MediaType.IMAGES -> MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            MediaType.VIDEO -> MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            MediaType.DOWNLOADS -> MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL)
            MediaType.DOCUMENTS -> MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        }
    }

    fun getMediaDirectory(mediaType: MediaType): File {
        return Environment.getExternalStoragePublicDirectory(getMediaFolder(mediaType))
    }

    fun getMediaFolder(mediaType: MediaType) = when(mediaType) {
        MediaType.AUDIO -> Environment.DIRECTORY_MUSIC
        MediaType.IMAGES -> Environment.DIRECTORY_PICTURES
        MediaType.VIDEO -> Environment.DIRECTORY_MOVIES
        MediaType.DOWNLOADS -> Environment.DIRECTORY_DOWNLOADS
        MediaType.DOCUMENTS -> Environment.DIRECTORY_DOCUMENTS
    }

}