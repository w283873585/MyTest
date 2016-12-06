package jun.learn.scene.chain;

import java.util.ArrayList;
import java.util.List;

public class PipelineSupport implements Pipeline{

	private Valve basic;
	
	private List<Valve> valves = new ArrayList<Valve>();
	
	@Override
	public Valve getBasic() {
		return basic;
	}

	@Override
	public void setBasic(Valve basic) {
		this.basic = basic;
	}

	@Override
	public void addValve(Valve valve) {
		valves.add(valve);
	}

	@Override
	public Valve[] getValves() {
		return (Valve[]) valves.toArray();
	}

	@Override
	public void invoke() {
		new MyContext().invokeNext();
	}

	@Override
	public void removeValve(Valve valve) {
		valves.remove(valve);
	}
	
	private class MyContext implements ValveContext {
		protected int stage = 0;
		
		@Override
		public String getInfo() {
			return null;
		}

		@Override
		public void invokeNext() {
			 int subscript = stage;
		     stage = stage + 1;
		      // Invoke the requested Valve for the current request thread
		     if (subscript < valves.size()) {
		    	 valves.get(subscript).invoke(this);
		     } else if ((subscript == valves.size()) && (basic != null)) {
		    	 basic.invoke(this);
		     } else {
		        throw new RuntimeException("No valve");
		     }
		}
	}
}
