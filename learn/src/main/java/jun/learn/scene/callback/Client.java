package jun.learn.scene.callback;

public class Client {
	public static void main(String[] args) {
		Factory factory = new Factory();
		XmlReader xml = new XmlReader(factory);
		/**
		 * XmlReader职责: 
		 * 	解析xml, 并将加载的数据注入至factory 
		 */
		xml.loadXml("path");
		factory.show();
	}
}
