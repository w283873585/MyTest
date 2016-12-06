package jun.learn.scene.callback;

public class Client {
	public static void main(String[] args) {
		Factory factory = new Factory();
		XmlReader xml = new XmlReader(factory);
		xml.loadXml("path");
		factory.show();
	}
}
