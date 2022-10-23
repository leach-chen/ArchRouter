package com.archrouter.plugin.baseplugin;

interface ClassNameFilter {
    fun filter(className: String): Boolean
}