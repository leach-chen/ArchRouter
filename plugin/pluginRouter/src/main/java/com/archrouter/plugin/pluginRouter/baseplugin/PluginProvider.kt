package com.archrouter.plugin.pluginRouter.baseplugin;

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @Author LiABao
 * @Since 2021/1/26
 */
interface PluginProvider {

    fun getPlugin(): Class<out Plugin<Project>>


    fun dependOn(): List<String>
}