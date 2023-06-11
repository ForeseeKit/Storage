package com.grace.foresee.storage

import java.io.File

object Path {
    fun of(first: String, vararg more: String?): String {
        val path = if (first.endsWith(File.separator) && more.isNotEmpty()) {
            StringBuilder(first.substring(0, first.lastIndex))
        } else {
            StringBuilder(first)
        }

        more.forEachIndexed { index, item ->
            item?.let {
                // 在头部添加分隔符
                if (it.startsWith(File.separator).not()) {
                    File.separator + it
                } else it
            }?.let {
                // 去掉除最后一项之外的尾部分隔符
                if (index != more.lastIndex && item.endsWith(File.separator)) {
                    it.substring(0, it.lastIndex)
                } else it
            }?.also {
                path.append(it)
            }
        }

        return path.toString()
    }
}