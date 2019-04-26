package jun.learn.scene.single_parameter_event_listener;

public interface TypedEventListener extends EventListener{
    Class getEventType();
}
