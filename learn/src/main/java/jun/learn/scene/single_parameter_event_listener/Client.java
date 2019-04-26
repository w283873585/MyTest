package jun.learn.scene.single_parameter_event_listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
    public static void main(String[] args) {
        List<EventListener> eventListenerList = new AnnotationEventListenerResolver()
                .getEventListeners(new EventConfig());
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("123",1123);
        for (EventListener eventListener : eventListenerList) {
            eventListener.onEvent(param);
        }
    }


    public static class EventConfig{
        @Subscribe
        public void hello(Map<String, Object> map) {
            System.out.print("map的值是：" + map + "，当前方法=EventConfig.hello()");
        }
    }
}
