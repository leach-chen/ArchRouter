package com.archrouter.plugin.baseplugin;

interface TransformCallBack {
    fun process(className: String, classBytes: ByteArray?): ByteArray?
}