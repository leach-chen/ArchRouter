package com.archrouter.router;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.archrouter.arch.annotation.RouterProvider;
import com.archrouter.arch.annotation.RouterService;
import com.archrouter.arch.service.ServiceImpl;
import com.archrouter.router.common.PageAnnotationHandler;
import com.archrouter.router.core.Debugger;
import com.archrouter.router.core.RootUriHandler;
import com.archrouter.router.core.UriRequest;
import com.archrouter.router.exception.DefaultServiceException;
import com.archrouter.router.method.Func0;
import com.archrouter.router.method.Func1;
import com.archrouter.router.method.Func2;
import com.archrouter.router.method.Func3;
import com.archrouter.router.method.Func4;
import com.archrouter.router.method.Func5;
import com.archrouter.router.method.Func6;
import com.archrouter.router.method.Func7;
import com.archrouter.router.method.Func8;
import com.archrouter.router.method.Func9;
import com.archrouter.router.method.FuncN;
import com.archrouter.router.service.IFactory;
import com.archrouter.router.service.ServiceLoader;

import java.util.List;

/**
 * <p>包结构说明：
 * <pre>
 * - core：路由核心接口和实现类，提供通用能力<br/>
 * - utils：通用工具类<br/>
 * - components: 辅助组件<br/>
 * - activity：Activity相关<br/>
 * - regex：正则相关<br/>
 * - common：UriHandler、UriInterceptor、UriRequest通用实现类<br/>
 * - service: ServiceLoader模块<br/>
 * - method：方法通用接口<br/>
 * </pre>
 * </p>
 */
public class Router {

    @SuppressLint("StaticFieldLeak")
    private static RootUriHandler ROOT_HANDLER;

    /**
     * 此初始化方法必须在主线程调用。
     */
    public static void init(@NonNull RootUriHandler rootUriHandler) {
        if (!Debugger.isLogSetting()) {
            Log.w(Debugger.LOG_TAG, "!!当前未设置Logger,建议通过 Debugger.setLogger()方法设置Logger");
            Log.w(Debugger.LOG_TAG, "!!并在测试环境通过 Debugger.EnableLog(true)方法开启日志");
            Log.w(Debugger.LOG_TAG, "!!通过Debugger.setEnableDebug(true)方法在测试环境及时抛出严重类型异常");
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Debugger.fatal("初始化方法init应该在主线程调用");
        }
        if (ROOT_HANDLER == null) {
            ROOT_HANDLER = rootUriHandler;
        } else {
            Debugger.fatal("请勿重复初始化UriRouter");
        }
    }

    /**
     * 此初始化方法的调用不是必须的。
     * 使用时会按需初始化；但也可以提前调用并初始化，使用时会等待初始化完成。
     * 本方法线程安全。
     */
    public static void lazyInit() {
        ServiceLoader.lazyInit();
        getRootHandler().lazyInit();
    }

    public static RootUriHandler getRootHandler() {
        if (ROOT_HANDLER == null) {
            throw new RuntimeException("请先调用init初始化UriRouter");
        }
        return ROOT_HANDLER;
    }

    public static void startUri(UriRequest request) {
        getRootHandler().startUri(request);
    }

    public static void startUri(Context context, String uri) {
        getRootHandler().startUri(new UriRequest(context, uri));
    }

    /**
     * 启动@RouterPage注解的Activity，自动拼装PageAnnotationHandler.SCHEME_HOST和path
     * @param context
     * @param path
     */
    public static void startPageUri(Context context, String path) {
        startUri(context, PageAnnotationHandler.SCHEME_HOST + path);
    }

    /**
     * 根据接口获取 {@link ServiceLoader}
     */
    public static <T> ServiceLoader<T> loadService(Class<T> clazz) {
        return ServiceLoader.load(clazz);
    }

    /**
     * 创建 指定的clazz的默认实现类的实例，如果没有任何一个实现类指定了{@link RouterService#defaultImpl()},
     * 则会判断 指定的clazz的实现类是否只有一个，如果只有一个则会使用该实现类构造
     * 如果发现有多个 指定的clazz的实现类，则会抛出异常
     *
     * @return 找不到或获取、构造失败，则返回null
     */
    public static <I, T extends I> I getService(Class<I> clazz) {
        final I service = ServiceLoader.load(clazz).get(ServiceImpl.DEFAULT_IMPL_KEY);
        if (service != null) {
            return service;
        }else {
            final List<I> services = getAllServices(clazz);
            if (services.size() == 1) {
                return services.get(0);
            } else if (services.size() > 1) {
                Debugger.fatal(DefaultServiceException.foundMoreThanOneImpl(clazz));
            }
        }
        return null;
    }

    /**
     * 创建 指定的clazz的默认实现类的实例，使用context参数构造，如果没有任何一个实现类指定了{@link RouterService#defaultImpl()},
     * 则会判断 指定的clazz的实现类是否只有一个，如果只有一个则会使用该实现类构造
     * 如果发现有多个 指定的clazz的实现类，则会抛出异常
     *
     * @return 找不到或获取、构造失败，则返回null
     */
    public static <I, T extends I> I getService(Class<I> clazz, Context context) {
        final I service = ServiceLoader.load(clazz).get(ServiceImpl.DEFAULT_IMPL_KEY,context);
        if (service != null) {
            return service;
        }else {
            final List<I> services = getAllServices(clazz,context);
            if (services.size() == 1) {
                return services.get(0);
            } else if (services.size() > 1) {
                Debugger.fatal(DefaultServiceException.foundMoreThanOneImpl(clazz));
            }
        }
        return null;
    }

    /**
     * 创建 指定的clazz的默认实现类的实例，使用指定的Factory构造，如果没有任何一个实现类指定了{@link RouterService#defaultImpl()},
     * 则会判断 指定的clazz的实现类是否只有一个，如果只有一个则会使用该实现类构造
     * 如果发现有多个 指定的clazz的实现类，则会抛出异常
     *
     * @return 找不到或获取、构造失败，则返回null
     */
    public static <I, T extends I> I getService(Class<I> clazz, IFactory factory) {
        final I service = ServiceLoader.load(clazz).get(ServiceImpl.DEFAULT_IMPL_KEY, factory);
        if (service != null) {
            return service;
        }else {
            final List<I> services = getAllServices(clazz, factory);
            if (services.size() == 1) {
                return services.get(0);
            } else if (services.size() > 1) {
                Debugger.fatal(DefaultServiceException.foundMoreThanOneImpl(clazz));
            }
        }
        return null;
    }


    /**
     * 创建指定key的实现类实例，使用 {@link RouterProvider} 方法或无参数构造。对于声明了singleton的实现类，不会重复创建实例。
     *
     * @return 找不到或获取、构造失败，则返回null
     */
    public static <I, T extends I> T getService(Class<I> clazz, String key) {
        return ServiceLoader.load(clazz).get(key);
    }

    /**
     * 创建指定key的实现类实例，使用Context参数构造。对于声明了singleton的实现类，不会重复创建实例。
     *
     * @return 找不到或获取、构造失败，则返回null
     */
    public static <I, T extends I> T getService(Class<I> clazz, String key, Context context) {
        return ServiceLoader.load(clazz).get(key, context);
    }

    /**
     * 创建指定key的实现类实例，使用指定的Factory构造。对于声明了singleton的实现类，不会重复创建实例。
     *
     * @param factory 用于从Class构造实例
     * @return 找不到或获取、构造失败，则返回null
     */
    public static <I, T extends I> T getService(Class<I> clazz, String key, IFactory factory) {
        return ServiceLoader.load(clazz).get(key, factory);
    }

    /**
     * 创建所有实现类的实例，使用 {@link RouterProvider} 方法或无参数构造。对于声明了singleton的实现类，不会重复创建实例。
     *
     * @return 可能返回EmptyList，List中的元素不为空
     */
    public static <I, T extends I> List<T> getAllServices(Class<I> clazz) {
        return ServiceLoader.load(clazz).getAll();
    }

    /**
     * 创建所有实现类的实例，使用Context参数构造。对于声明了singleton的实现类，不会重复创建实例。
     *
     * @return 可能返回EmptyList，List中的元素不为空
     */
    public static <I, T extends I> List<T> getAllServices(Class<I> clazz, Context context) {
        return ServiceLoader.load(clazz).getAll(context);
    }

    /**
     * 创建所有实现类的实例，使用指定Factory构造。对于声明了singleton的实现类，不会重复创建实例。
     *
     * @return 可能返回EmptyList，List中的元素不为空
     */
    public static <I, T extends I> List<T> getAllServices(Class<I> clazz, IFactory factory) {
        return ServiceLoader.load(clazz).getAll(factory);
    }

    /**
     * 根据key获取实现类的Class。注意，对于声明了singleton的实现类，获取Class后还是可以创建新的实例。
     *
     * @return 找不到或获取失败，则返回null
     */
    public static <I, T extends I> Class<T> getServiceClass(Class<I> clazz, String key) {
        return ServiceLoader.load(clazz).getClass(key);
    }

    /**
     * 获取所有实现类的Class。注意，对于声明了singleton的实现类，获取Class后还是可以创建新的实例。
     *
     * @return 可能返回EmptyList，List中的元素不为空
     */
    public static <I, T extends I> List<Class<T>> getAllServiceClasses(Class<I> clazz) {
        return ServiceLoader.load(clazz).getAllClasses();
    }

    /**
     * 调用方法。方法应该实现 {@link Func0} ~ {@link FuncN} 接口，根据参数个数匹配接口。
     */
    @SuppressWarnings("unchecked")
    public static <T> T callMethod(String key, Object... args) {
        switch (args.length) {
            case 0:
                return (T) getService(Func0.class, key).call();
            case 1:
                return (T) getService(Func1.class, key).call(args[0]);
            case 2:
                return (T) getService(Func2.class, key).call(args[0], args[1]);
            case 3:
                return (T) getService(Func3.class, key).call(args[0], args[1], args[2]);
            case 4:
                return (T) getService(Func4.class, key).call(
                        args[0], args[1], args[2], args[3]);
            case 5:
                return (T) getService(Func5.class, key).call(
                        args[0], args[1], args[2], args[3], args[4]);
            case 6:
                return (T) getService(Func6.class, key).call(
                        args[0], args[1], args[2], args[3], args[4], args[5]);
            case 7:
                return (T) getService(Func7.class, key).call(
                        args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            case 8:
                return (T) getService(Func8.class, key).call(
                        args[0], args[1], args[2], args[3],
                        args[4], args[5], args[6], args[7]);
            case 9:
                return (T) getService(Func9.class, key).call(
                        args[0], args[1], args[2], args[3],
                        args[4], args[5], args[6], args[7], args[8]);
            default:
                return (T) getService(FuncN.class, key).call(args);
        }
    }
}
