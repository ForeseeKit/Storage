package com.grace.foresee.storage

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.contentValuesOf
import com.grace.foresee.storage.MediaStoreWriter.writeBytes
import com.grace.foresee.storage.utils.MediaStoreUtil
import java.io.FileNotFoundException

object MediaStoreWriter {

    /**
     * 将数据写入媒体库文件
     * @param context 上下文对象
     * @param mediaType 媒体类型：[MediaType.IMAGES], [MediaType.VIDEO], [MediaType.AUDIO],
     * [MediaType.DOWNLOADS], [MediaType.DOCUMENTS]
     * @param mimeType MIME 类型：["audio/mp4"], ["image/jpeg"], [...]
     * @param directory 目录名称，如果文件所在的目录为媒体库根目录，则传 null
     * @param filename 文件名称
     * @param content 写入的内容
     * @see writeBytes
     */
    fun write(
        context: Context,
        mediaType: MediaType,
        mimeType: String,
        directory: String? = null,
        filename: String,
        content: String
    ) {
        return writeBytes(context, mediaType, mimeType, directory, filename, content.toByteArray())
    }

    /**
     * 将数据写入媒体库文件
     * @param context 上下文对象
     * @param mediaType 媒体类型：[MediaType.IMAGES], [MediaType.VIDEO], [MediaType.AUDIO],
     * [MediaType.DOWNLOADS], [MediaType.DOCUMENTS]
     * @param mimeType MIME 类型：["audio/mp4"], ["image/jpeg"], [...]
     * @param directory 目录名称，如果文件所在的目录为媒体库根目录，则传 null
     * @param filename 文件名称
     * @param content 写入的内容
     */
    fun writeBytes(
        context: Context,
        mediaType: MediaType,
        mimeType: String,
        directory: String? = null,
        filename: String,
        content: ByteArray
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val projection = arrayOf(MediaStore.MediaColumns._ID)

            val externalContentUri = MediaStoreUtil.getExternalContentUri(mediaType)
            val mediaFolder = MediaStoreUtil.getMediaFolder(mediaType)
            val relativePath = Path.of(mediaFolder, directory)

            val selection = "${MediaStore.MediaColumns.DISPLAY_NAME}=? and ${MediaStore.MediaColumns.RELATIVE_PATH} like '${relativePath}%'"
            val args = arrayOf(filename)

            var fileUri: Uri? = null
            // 查找文件
            resolver.query(externalContentUri, projection, selection, args, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    fileUri = ContentUris.withAppendedId(externalContentUri, cursor.getLong(0))
                } else null
            } ?: run {
                // 文件没有找到，创建新文件
                contentValuesOf(
                    MediaStore.MediaColumns.TITLE to filename,
                    MediaStore.MediaColumns.MIME_TYPE to mimeType,
                    MediaStore.MediaColumns.DISPLAY_NAME to filename,
                    MediaStore.MediaColumns.RELATIVE_PATH to relativePath
                ).let { values ->
                    resolver.insert(externalContentUri, values)
                }?.also {
                    fileUri = it
                }
            }

            // 写入文件
            fileUri?.also {
                try {
                    resolver.openOutputStream(it)?.also { outputStream ->
                        StorageWriter.write(outputStream, content)
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        } else {
            MediaStoreUtil.getMediaDirectory(mediaType).also { mediaDirectory ->
                StorageWriter.write(Path.of(mediaDirectory.absolutePath, directory, filename), content)
            }
        }
    }

}