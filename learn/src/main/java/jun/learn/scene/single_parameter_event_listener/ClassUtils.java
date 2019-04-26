package jun.learn.scene.single_parameter_event_listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {
    private static final Logger log = LoggerFactory.getLogger(ClassUtils.class);
    private static final ClassUtils.ClassLoaderAccessor THREAD_CL_ACCESSOR = new ClassUtils.ExceptionIgnoringAccessor() {
        protected ClassLoader doGetClassLoader() throws Throwable {
            return Thread.currentThread().getContextClassLoader();
        }
    };
    private static final ClassUtils.ClassLoaderAccessor CLASS_CL_ACCESSOR = new ClassUtils.ExceptionIgnoringAccessor() {
        protected ClassLoader doGetClassLoader() throws Throwable {
            return ClassUtils.class.getClassLoader();
        }
    };
    private static final ClassUtils.ClassLoaderAccessor SYSTEM_CL_ACCESSOR = new ClassUtils.ExceptionIgnoringAccessor() {
        protected ClassLoader doGetClassLoader() throws Throwable {
            return ClassLoader.getSystemClassLoader();
        }
    };

    public ClassUtils() {
    }

    public static InputStream getResourceAsStream(String name) {
        InputStream is = THREAD_CL_ACCESSOR.getResourceStream(name);
        if (is == null) {
            if (log.isTraceEnabled()) {
                log.trace("Resource [" + name + "] was not found via the thread context ClassLoader.  Trying the " + "current ClassLoader...");
            }

            is = CLASS_CL_ACCESSOR.getResourceStream(name);
        }

        if (is == null) {
            if (log.isTraceEnabled()) {
                log.trace("Resource [" + name + "] was not found via the current class loader.  Trying the " + "system/application ClassLoader...");
            }

            is = SYSTEM_CL_ACCESSOR.getResourceStream(name);
        }

        if (is == null && log.isTraceEnabled()) {
            log.trace("Resource [" + name + "] was not found via the thread context, current, or " + "system/application ClassLoaders.  All heuristics have been exhausted.  Returning null.");
        }

        return is;
    }

    public static Class forName(String fqcn) throws RuntimeException {
        Class clazz = THREAD_CL_ACCESSOR.loadClass(fqcn);
        if (clazz == null) {
            if (log.isTraceEnabled()) {
                log.trace("Unable to load class named [" + fqcn + "] from the thread context ClassLoader.  Trying the current ClassLoader...");
            }

            clazz = CLASS_CL_ACCESSOR.loadClass(fqcn);
        }

        if (clazz == null) {
            if (log.isTraceEnabled()) {
                log.trace("Unable to load class named [" + fqcn + "] from the current ClassLoader.  " + "Trying the system/application ClassLoader...");
            }

            clazz = SYSTEM_CL_ACCESSOR.loadClass(fqcn);
        }

        if (clazz == null) {
            String msg = "Unable to load class named [" + fqcn + "] from the thread context, current, or " + "system/application ClassLoaders.  All heuristics have been exhausted.  Class could not be found.";
            throw new RuntimeException(msg);
        } else {
            return clazz;
        }
    }

    public static boolean isAvailable(String fullyQualifiedClassName) {
        try {
            forName(fullyQualifiedClassName);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static Object newInstance(String fqcn) {
        return newInstance(forName(fqcn));
    }

    public static Object newInstance(String fqcn, Object... args) {
        return newInstance(forName(fqcn), args);
    }

    public static Object newInstance(Class clazz) {
        if (clazz == null) {
            String msg = "Class method parameter cannot be null.";
            throw new IllegalArgumentException(msg);
        } else {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static Object newInstance(Class clazz, Object... args) {
        Class[] argTypes = new Class[args.length];

        for(int i = 0; i < args.length; ++i) {
            argTypes[i] = args[i].getClass();
        }

        Constructor ctor = getConstructor(clazz, argTypes);
        return instantiate(ctor, args);
    }

    public static Constructor getConstructor(Class clazz, Class... argTypes) {
        try {
            return clazz.getConstructor(argTypes);
        } catch (NoSuchMethodException var3) {
            throw new IllegalStateException(var3);
        }
    }

    public static Object instantiate(Constructor ctor, Object... args) {
        try {
            return ctor.newInstance(args);
        } catch (Exception var4) {
            String msg = "Unable to instantiate Permission instance with constructor [" + ctor + "]";
            throw new RuntimeException(msg);
        }
    }

    public static List<Method> getAnnotatedMethods(Class<?> type, Class<? extends Annotation> annotation) {
        List<Method> methods = new ArrayList();

        for(Class clazz = type; !Object.class.equals(clazz); clazz = clazz.getSuperclass()) {
            Method[] currentClassMethods = clazz.getDeclaredMethods();
            Method[] var5 = currentClassMethods;
            int var6 = currentClassMethods.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Method method = var5[var7];
                if (annotation == null || method.isAnnotationPresent(annotation)) {
                    methods.add(method);
                }
            }
        }

        return methods;
    }

    private abstract static class ExceptionIgnoringAccessor implements ClassUtils.ClassLoaderAccessor {
        private ExceptionIgnoringAccessor() {
        }

        public Class loadClass(String fqcn) {
            Class clazz = null;
            ClassLoader cl = this.getClassLoader();
            if (cl != null) {
                try {
                    clazz = cl.loadClass(fqcn);
                } catch (ClassNotFoundException var5) {
                    if (ClassUtils.log.isTraceEnabled()) {
                        ClassUtils.log.trace("Unable to load clazz named [" + fqcn + "] from class loader [" + cl + "]");
                    }
                }
            }

            return clazz;
        }

        public InputStream getResourceStream(String name) {
            InputStream is = null;
            ClassLoader cl = this.getClassLoader();
            if (cl != null) {
                is = cl.getResourceAsStream(name);
            }

            return is;
        }

        protected final ClassLoader getClassLoader() {
            try {
                return this.doGetClassLoader();
            } catch (Throwable var2) {
                if (ClassUtils.log.isDebugEnabled()) {
                    ClassUtils.log.debug("Unable to acquire ClassLoader.", var2);
                }

                return null;
            }
        }

        protected abstract ClassLoader doGetClassLoader() throws Throwable;
    }

    private interface ClassLoaderAccessor {
        Class loadClass(String var1);

        InputStream getResourceStream(String var1);
    }
}