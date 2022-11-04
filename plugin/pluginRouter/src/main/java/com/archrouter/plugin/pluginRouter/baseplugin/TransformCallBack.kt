package com.archrouter.plugin.pluginRouter.baseplugin;

interface TransformCallBack {
    fun process(className: String, classBytes: ByteArray?): ByteArray?
}