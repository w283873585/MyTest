package jun.learn.scene.single_parameter_event_listener;

public interface EventListener {
    boolean accepts(Object event);

    void onEvent(Object event);
}
