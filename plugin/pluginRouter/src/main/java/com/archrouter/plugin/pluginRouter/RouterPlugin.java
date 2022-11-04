package com.archrouter.plugin.pluginRouter;

import com.android.build.gradle.BaseExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * 插件所做工作：将注解生成器生成的初始化类汇总到ServiceLoaderInit，运行时直接调用ServiceLoaderInit
 */
public class RouterPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        RouterExtension extension = project.getExtensions()
                .create(Const.NAME, RouterExtension.class);

        RouterLogger.info("register transform");
        project.getExtensions().findByType(BaseExtension.class)
                .registerTransform(new RouterTransform());

        project.afterEvaluate(p -> RouterLogger.setConfig(extension));
    }
}
