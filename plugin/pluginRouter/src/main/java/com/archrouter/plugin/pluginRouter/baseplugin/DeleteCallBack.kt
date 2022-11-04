package com.archrouter.plugin.pluginRouter.baseplugin;

interface DeleteCallBack {
    fun delete(className: String, classBytes: ByteArray)
}