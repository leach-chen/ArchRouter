package com.archrouter.arch.compiler;

import com.archrouter.arch.annotation.Print;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.processing.JavacFiler;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TestProcess extends AbstractProcessor {
    protected ProcessingEnvironment mProcEnv;
    private Filer mFiler; //文件相关工具类：用于保存生成的java类文件

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        mProcEnv = processingEnvironment;
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //获取到所有添加@Print注解的成员变量
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Print.class);
        if (elements.isEmpty()) {
            return true;
        }

        try {
            //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            JavaFileObject fileObject = mProcEnv.getFiler().createSourceFile("com.archrouter.app.PrintUtil");
            Writer writer = fileObject.openWriter();
            writer.write("package com.archrouter.app;\n");
            writer.write("\n");
            writer.write("public class PrintUtil {\n");

            for(Element element : elements) {
                VariableElement variableElement = (VariableElement) element;
                String activityName = variableElement.getEnclosingElement().getSimpleName().toString();
                Class aClass = variableElement.getEnclosingElement().getClass();

                TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
                String packageName = mProcEnv.getElementUtils().getPackageOf(enclosingElement).toString();

                //System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb:"+activityName+" "+" "+packageName);

                //获取到成员变量名
                Name simpleName = element.getSimpleName();
                writer.write("      //输出" + simpleName+"\n");
                writer.write("      public static void print$$" + simpleName + "() {\n");
                writer.write("          System.out.println(\"Hello " + simpleName + "\");\n     }\n\n");
            }
            writer.write("}");
            writer.flush();
            writer.close();
            //System.out.println("cccccccccccccccccccccccccccccccc");

            //创建类
            /*TypeSpec bindProxyClass = TypeSpec.classBuilder("PrintUtil")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .build();
            //创建java文件
            JavaFile bindProxyFile = JavaFile
                    .builder("com.archrouter.app", bindProxyClass)
                    .build();
            bindProxyFile.writeTo(mFiler);*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Print.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
