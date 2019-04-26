package jun.learn.scene.single_parameter_event_listener;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class SingleArgumentMethodEventListener implements TypedEventListener{
    private Method method;
    private Object target;

    public SingleArgumentMethodEventListener(Object target, Method method) {
        this.method = method;
        this.target = target;
        assertPublicMethod(method);
    }

    public Method getMethod() {
        return method;
    }

    public Object getTarget() {
        return target;
    }

    private void assertPublicMethod(Method method) {
        int modifiers = method.getModifiers();
        if (!Modifier.isPublic(modifiers)) {
            throw new IllegalArgumentException("Event handler method [" + method + "] must be public.");
        }
    }

    public Class getEventType() {
        return this.getMethodArgumentType(method);
    }

    public boolean accepts(Object event) {
        return event != null && event.getClass() == this.getEventType();
    }

    public void onEvent(Object event) {
        Method method = this.getMethod();

        try {
            method.invoke(this.getTarget(), event);
        } catch (Exception var4) {
            throw new IllegalStateException("Unable to invoke event handler method [" + method + "].", var4);
        }
    }

    protected Class getMethodArgumentType(Method method) {
        Class[] paramTypes = method.getParameterTypes();
        if (paramTypes.length != 1) {
            String msg = "Event handler methods must accept a single argument.";
            throw new IllegalArgumentException(msg);
        } else {
            return paramTypes[0];
        }
    }
}
