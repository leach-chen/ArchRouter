package com.archrouter.arch.compiler;


import com.archrouter.arch.annotation.RouterService;
import com.archrouter.arch.other.Const;
import com.archrouter.arch.service.ServiceImpl;
import com.google.auto.service.AutoService;
import com.sun.tools.javac.code.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RouterServiceProcessor extends BaseProcessor {

    private final HashMap<String, Entity> mEntityMap = new HashMap<>();
    private String mHash = null;


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver()) {
            generateInitClass();
        } else {
            //System.out.println("xxx");
            //mProcEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,"xxx");
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(RouterService.class);
            if (elements.isEmpty()) return true;
            for (Element element : elements) {
                if (!(element instanceof Symbol.ClassSymbol)) {
                    continue;
                }
                Symbol.ClassSymbol cls = (Symbol.ClassSymbol) element;
                if (mHash == null) {
                    mHash = hash(cls.className());
                }
                TypeElement typeElement = (TypeElement) element;
                RouterService routerService = typeElement.getAnnotation(RouterService.class);
                if (routerService == null) {
                    continue;
                }
                List<? extends TypeMirror> typeMirrors = getInterface(routerService);
                String[] keys = routerService.key();

                String implementationName = cls.className();
                boolean singleton = routerService.singleton();
                final boolean defaultImpl = routerService.defaultImpl();
                if (typeMirrors != null && !typeMirrors.isEmpty()) {
                    for (TypeMirror mirror : typeMirrors) {
                        if (mirror == null) {
                            continue;
                        }
                        if (!isConcreteSubType(cls, mirror)) {
                            String msg = cls.className() + "没有实现注解" + RouterService.class.getName()
                                    + "标注的接口" + mirror.toString();
                            throw new RuntimeException(msg);
                        }
                        String interfaceName = getClassName(mirror);
                        Entity entity = mEntityMap.get(interfaceName);
                        if (entity == null) {
                            entity = new Entity(interfaceName);
                            mEntityMap.put(interfaceName, entity);
                        }

                        if (defaultImpl) {
                            //如果设置为默认实现，则手动添加一个内部标识默认实现的key
                            entity.put(ServiceImpl.DEFAULT_IMPL_KEY, implementationName, singleton);
                        }
                        if (keys.length > 0) {
                            for (String key : keys) {
                                if (key.contains(":")) {
                                    String msg = String.format("%s: 注解%s的key参数不可包含冒号",
                                            implementationName, RouterService.class.getName());
                                    throw new RuntimeException(msg);
                                }
                                entity.put(key, implementationName, singleton);
                            }
                        } else {
                            entity.put(null, implementationName, singleton);
                        }

                    }
                }

            }
            return true;
        }
        return true;
    }

    private void generateInitClass() {
        if (mEntityMap.isEmpty() || mHash == null) {
            return;
        }
        ServiceInitClassBuilder generator = new ServiceInitClassBuilder("ServiceInit" + Const.SPLITTER + mHash);
        for (Map.Entry<String, Entity> entry : mEntityMap.entrySet()) {
            for (ServiceImpl service : entry.getValue().getMap().values()) {
                generator.put(entry.getKey(), service.getKey(), service.getImplementation(), service.isSingleton());
            }
        }
        generator.build();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(RouterService.class.getCanonicalName());
        return types;
    }

    private static List<? extends TypeMirror> getInterface(RouterService service) {
        try {
            service.interfaces();
        } catch (MirroredTypesException mte) {
            return mte.getTypeMirrors();
        }
        return null;
    }

    public static class Entity {
        private final String mInterfaceName;

        private final Map<String, ServiceImpl> mMap = new HashMap<>();

        public Entity(String interfaceName) {
            mInterfaceName = interfaceName;
        }

        public Map<String, ServiceImpl> getMap() {
            return mMap;
        }

        public void put(String key, String implementationName, boolean singleton) {
            if (implementationName == null) {
                return;
            }
            ServiceImpl impl = new ServiceImpl(key, implementationName, singleton);
            ServiceImpl prev = mMap.put(impl.getKey(), impl);
            String errorMsg = ServiceImpl.checkConflict(mInterfaceName, prev, impl);
            if (errorMsg != null) {
                throw new RuntimeException(errorMsg);
            }
        }

        public List<String> getContents() {
            List<String> list = new ArrayList<>();
            for (ServiceImpl impl : mMap.values()) {
                list.add(impl.toConfig());
            }
            return list;
        }
    }
}
