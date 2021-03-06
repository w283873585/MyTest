package jun.learn.scene.lifecycle;

import java.util.EventObject;

public final class LifecycleEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3202482852524326366L;

	public LifecycleEvent(Lifecycle lifecycle, String type) {
		this(lifecycle, type, null);
	}

	public LifecycleEvent(Lifecycle lifecycle, String type, Object data) {
		super(lifecycle);
		this.lifecycle = lifecycle;
		this.type = type;
		this.data = data;
	}

	private Object data = null;
	private Lifecycle lifecycle = null;
	private String type = null;

	public Object getData() {
		return (this.data);
	}

	public Lifecycle getLifecycle() {
		return (this.lifecycle);
	}

	public String getType() {
		return (this.type);
	}
}