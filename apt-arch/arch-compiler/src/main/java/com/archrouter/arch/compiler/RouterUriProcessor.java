package com.archrouter.arch.compiler;

import com.archrouter.arch.annotation.RouterUri;
import com.archrouter.arch.other.Const;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import com.sun.tools.javac.code.Symbol;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RouterUriProcessor extends BaseProcessor {
    
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.isEmpty()) return false;
        CodeBlock.Builder builder = CodeBlock.builder();
        String hash = null;
        for (Element element : roundEnvironment.getElementsAnnotatedWith(RouterUri.class)) {
            if (!(element instanceof Symbol.ClassSymbol)) {
                continue;
            }
            boolean isActivity = isActivity(element);
            boolean isHandler = isHandler(element);
            if (!isActivity && !isHandler) {
                continue;
            }

            Symbol.ClassSymbol cls = (Symbol.ClassSymbol) element;
            RouterUri uri = cls.getAnnotation(RouterUri.class);
            if (uri == null) {
                return false;
            }
            if (hash == null) {
                hash = hash(cls.className());
            }
            CodeBlock handler = buildHandler(isActivity, cls);
            CodeBlock interceptors = buildInterceptors(getInterceptors(uri));

            String[] pathList = uri.path();
            for (String path : pathList) {
                builder.addStatement("handler.register($S, $S, $S, $L, $L$L)",
                        uri.scheme(),
                        uri.host(),
                        path,
                        handler,
                        uri.exported(),
                        interceptors);
            }
        }

        if (hash == null) {
            hash = randomHash();
        }
        buildHandlerInitClass(builder.build(), "UriAnnotationInit" + Const.SPLITTER + hash,
                Const.URI_ANNOTATION_HANDLER_CLASS, Const.URI_ANNOTATION_INIT_CLASS);
        return true;
    }

    private static List<? extends TypeMirror> getInterceptors(RouterUri scheme) {
        try {
            scheme.interceptors();
        } catch (MirroredTypesException mte) {
            return mte.getTypeMirrors();
        }
        return null;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Collections.singletonList(RouterUri.class.getName()));
    }
}
