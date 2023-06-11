package com.grace.foresee.storage

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import com.grace.foresee.storage.utils.MediaStoreUtil
import java.io.FileNotFoundException
import java.nio.charset.StandardCharsets

object MediaStoreReader {

    /**
     * 从媒体库文件读取数据
     * @param context 上下文对象
     * @param mediaType 媒体类型：[MediaType.IMAGES], [MediaType.VIDEO], [MediaType.AUDIO],
     * [MediaType.DOWNLOADS], [MediaType.DOCUMENTS]
     * @param directory 目录名称，如果文件所在的目录为媒体库根目录，则传 null
     * @param filename 文件名称
     * @return 返回文件内容，如果文件不存在或没有找到则返回 null
     * @see readForBytes
     */
    fun read(
        context: Context,
        mediaType: MediaType,
        directory: String? = null,
        filename: String
    ): String? {
        return readForBytes(context, mediaType, directory, filename)?.let {
            String(it, StandardCharsets.UTF_8)
        }
    }

    /**
     * 从媒体库文件读取数据
     * @param context 上下文对象
     * @param mediaType 媒体类型：[MediaType.IMAGES], [MediaType.VIDEO], [MediaType.AUDIO],
     * [MediaType.DOWNLOADS], [MediaType.DOCUMENTS]
     * @param directory 目录名称，如果文件所在的目录为媒体库根目录，则传 null
     * @param filename 文件名称
     * @return 返回文件内容，如果文件不存在或没有找到则返回 null
     */
    fun readForBytes(
        context: Context, mediaType: MediaType, directory: String? = null, filename: String
    ): ByteArray? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val projection = arrayOf(MediaStore.MediaColumns._ID)
            val selection =
                "${MediaStore.MediaColumns.DISPLAY_NAME}=? and ${MediaStore.MediaColumns.RELATIVE_PATH} like '${
                    Path.of(
                        MediaStoreUtil.getMediaFolder(mediaType),
                        directory
                    )
                }%'"

            val externalContentUri = MediaStoreUtil.getExternalContentUri(mediaType)

            val args = arrayOf(filename)

            // 查找文件
            resolver.query(externalContentUri, projection, selection, args, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    // 获取文件 uri
                    ContentUris.withAppendedId(externalContentUri, cursor.getLong(0))
                        .also { fileUri ->
                            try {
                                // 读取文件数据
                                resolver.openInputStream(fileUri)?.also { inputStream ->
                                    return StorageReader.readForBytes(inputStream)
                                }
                            } catch (e: FileNotFoundException) {
                                e.printStackTrace()
                            }
                        }
                }
            }
        } else {
            return StorageReader.readForBytes(
                Path.of(
                    MediaStoreUtil.getMediaDirectory(mediaType).absolutePath, directory, filename
                )
            )
        }

        return null
    }

}