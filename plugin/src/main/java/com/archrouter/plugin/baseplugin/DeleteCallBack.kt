package com.archrouter.plugin.baseplugin;

interface DeleteCallBack {
    fun delete(className: String, classBytes: ByteArray)
}