package learn.client.callback;

public class XmlReader {
	private Factory factory;
	
	public XmlReader(Factory factory) {
		this.factory = factory;
	}
	
	public void loadXml(String path) {
		factory.add("<hello></hello>");
	}
}
