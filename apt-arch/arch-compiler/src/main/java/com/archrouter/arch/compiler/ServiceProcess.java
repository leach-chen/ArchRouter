package com.archrouter.arch.compiler;


import com.archrouter.arch.annotation.RouterService;
import com.google.auto.service.AutoService;
import com.sun.tools.javac.code.Symbol;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ServiceProcess extends BaseProcessor{
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1111111111111111111111111111111");
        //mProcEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(RouterService.class);
        if(elements.isEmpty())return true;
        for(Element element : elements) {
            if (!(element instanceof Symbol.ClassSymbol)) {
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            RouterService routerService = typeElement.getAnnotation(RouterService.class);
            if(routerService == null){
                continue;
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(RouterService.class.getCanonicalName());
        return types;
    }
}
