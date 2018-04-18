package jun.learn.scene.processorChain.version3;

import java.util.List;

public class Client {
	public static void main(String[] args) {
		DataProvider dataProvider = new DatabaseDataProvider("Patient");
		SyncManager manager = new SyncManager();
		manager.registerProcessor(new DataProcessor<A>() {
			public String getName() {
				return "Patient";
			}
			public void preProcess(DataProvider p, Context ctx) {
				List<String> nameList = p.getByName("name", String.class);
				System.out.println(nameList);
			}
			public void process(A t, Context ctx) {
				System.out.println("process->" + t.getName());
			}
		});
		manager.process(dataProvider);
	}
	
	
	public static class A {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
