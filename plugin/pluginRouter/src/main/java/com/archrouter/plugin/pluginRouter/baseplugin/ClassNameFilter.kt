package com.archrouter.plugin.pluginRouter.baseplugin;

interface ClassNameFilter {
    fun filter(className: String): Boolean
}