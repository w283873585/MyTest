package jun.learn.scene.chain;

public class AClient {
	public static void main(String[] args) {
		
		AContainer container = new AContainer();
		
		container.addValve(getValve("1"));
		container.addValve(getValve("2"));
		container.addValve(getValve("3"));
		
		container.setBasic(getValve("4", true));
		
		container.invoke();
	}
	
	private static Valve getValve(final String id) {
		return getValve(id, false);
	}
	
	private static Valve getValve(final String id, final boolean isBasic) {
		return new Valve() {
			public void invoke(ValveContext context) {
				System.out.println(id + ": hello world");
				if (!isBasic)
					context.invokeNext();
			}
			
			public String getInfo() {
				return null;
			}
		};
	}
}
