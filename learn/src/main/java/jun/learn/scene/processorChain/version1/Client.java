package jun.learn.scene.processorChain.version1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
	public static void main(String[] args) {
		SyncManager manager = new SyncManager();
		manager.registerProcessor(new AProcessor());
		manager.registerListener(new AListener());
		
		manager.process("A", new DataProvider() {
			int index = 0;
			String[] values = {"2", "1", "4", "5"};
			@Override
			public Map<String, Object> next() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", values[index]);
				map.put("sex", values[index]);
				index++;
				return map;
			}
			
			@Override
			public boolean hasNext() {
				return index < values.length;
			}
		});
	}
	
	
	public static class AProcessor implements DataProcessor<A> {
		@Override
		public String getName() {
			return "A";
		}

		@Override
		public void process(A t, Context<A> ctx) {
			System.out.println("A.name => " + t.getName());
			System.out.println("A.sex => " + t.getSex());
		}
	}
	
	public static class A{
		private String name;
		private String sex;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
	}
	
	public static class AListener implements Listener<A> {
		@Override
		public String getName() {
			return "A";
		}

		@Override
		public void preHandle(List<A> originData) {
			System.out.println(originData);
		}
	}
}
