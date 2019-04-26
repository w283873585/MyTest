package jun.learn.scene.single_parameter_event_listener;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AnnotationEventListenerResolver implements EventListenerResolver {
    private Class<? extends Annotation> annotationClass = Subscribe.class;

    public AnnotationEventListenerResolver() {
    }

    public List<EventListener> getEventListeners(Object instance) {
        if (instance == null) {
            return Collections.emptyList();
        } else {
            List<Method> methods = ClassUtils.getAnnotatedMethods(instance.getClass(), this.getAnnotationClass());
            if (methods != null && !methods.isEmpty()) {
                List<EventListener> listeners = new ArrayList(methods.size());
                Iterator var4 = methods.iterator();

                while(var4.hasNext()) {
                    Method m = (Method)var4.next();
                    listeners.add(new SingleArgumentMethodEventListener(instance, m));
                }

                return listeners;
            } else {
                return Collections.emptyList();
            }
        }
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return this.annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }
}