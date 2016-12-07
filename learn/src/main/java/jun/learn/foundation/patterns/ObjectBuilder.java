package jun.learn.foundation.patterns;

//  Java构建器模式
public class ObjectBuilder {
	private Object a;
	private Object b;
	private Object c;
	private Object d;
	
	
	private ObjectBuilder(ObjBuilderHelper o) {
		this.a = o.a;
		this.b = o.b;
		this.c = o.c;
		this.d = o.d;
	}
	
	public void dosomething() {
		System.out.println("" + a + b + c + d);
	}
	
	public static class ObjBuilderHelper {
		private Object a;
		private Object b;
		private Object c;
		private Object d;
		public ObjBuilderHelper setA(Object a) {
			this.a = a;
			return this;
		}
		public ObjBuilderHelper setB(Object a) {
			this.b = a;
			return this;
		}
		public ObjBuilderHelper setC(Object a) {
			this.c = a;
			return this;
		}
		public ObjBuilderHelper setD(Object a) {
			this.d = a;
			return this;
		}
		public ObjectBuilder build() {
			ObjectBuilder obj = new ObjectBuilder(this);
			return obj;
		}
	}
	
	
	public static void main(String[] args) {
		ObjectBuilder o = new ObjectBuilder.ObjBuilderHelper()
				.setA(new Object())
				.setB(new Object())
				.setC(new Object())
				.setD(new Object())
				.build();
		o.dosomething();
	}
}
