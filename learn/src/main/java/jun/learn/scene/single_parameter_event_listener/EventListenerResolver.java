package jun.learn.scene.single_parameter_event_listener;

import java.util.List;

public interface EventListenerResolver {
    List<EventListener> getEventListeners(Object var1);
}