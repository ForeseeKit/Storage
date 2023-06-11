package com.grace.foresee.storage.extensions

import android.content.Context
import android.os.Environment
import kotlin.io.path.Path
import kotlin.io.path.pathString

/**
 * 当前应用的缓存路径（清理缓存时会删除该目录，手机存储空间不足时也可能会自动清理该目录）
 * @see appCachePath
 */
fun Context.getAppCachePath(firstSubPath: String, vararg moreSubPaths: String): String {
    return Path(appCachePath, firstSubPath, *moreSubPaths).pathString
}

/**
 * 当前应用的缓存路径（清理缓存时会删除该目录，手机存储空间不足时也可能会自动清理该目录）
 */
val Context.appCachePath: String
    get() {
        return try {
            // 判断是否装载了外部存储
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
                !Environment.isExternalStorageRemovable()
            ) {
                // 获取手机外部存储的当前应用的缓存路径
                externalCacheDir?.absolutePath
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } ?: cacheDir.absolutePath // 获取手机内部存储的当前应用的缓存路径
    }

/**
 * 当前应用的文件缓存路径（只有当卸载应用的时候才会自动清理）
 * @see appFilesPath
 */
fun Context.getAppFilesPath(firstSubPath: String, vararg moreSubPaths: String): String {
    return Path(appFilesPath, firstSubPath, *moreSubPaths).pathString
}

/**
 * 当前应用的文件缓存路径（只有当卸载应用的时候才会自动清理）
 */
val Context.appFilesPath: String
    get() {
        return try {
            // 判断是否装载了外部存储
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
                !Environment.isExternalStorageRemovable()) {
                // 获取手机外部存储的当前应用的文件缓存路径
                getExternalFilesDir("")?.absolutePath
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } ?: filesDir.absolutePath // 获取手机内部存储的当前应用的文件缓存路径
    }