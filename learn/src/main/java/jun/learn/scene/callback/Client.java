package jun.learn.scene.callback;

public class Client {
	public static void main(String[] args) {
		/**
		 * XmlReader职责: 
		 * 	解析xml, 并将加载的数据注入至factory 
		 */
		Factory factory = new Factory();
		XmlReader xml = new XmlReader(factory);
		xml.loadXml("path");
		factory.show();
		/**
		 * Factory : {
		 * 		void addBean(Bean bean);
		 * 		void show();
		 * }
		 * 
		 * XmlReader : {
		 * 		职责分离, Factory并不在乎Bean如何得到,只提供Bean的注册入口
		 * 		Reader负责解析xml, 并向Factory注册Bean 
		 * 		loadXml(String path) {
		 * 			String content = resolveXml(path);
		 * 			Bean bean = buildBean(content);
		 * 			factory.addBean(bean);
		 * 		}
		 * }
		 * 
		 *  
		    // 内部注入factory
		    Factory factory = new Factory();
			XmlReader reader = new XmlReader(factory);
			reader.loadXml("path");
			factory.show();
			
			vs
			
			// 外部注入factory
			Factory factory = new Factory();
			XmlReader reader = new XmlReader();
			Bean bean = buildBean(reader.loadXml("path"));
			factory.addBean(bean);
			factory.show();
		 * 
		 */
		
	}
}
