package learn.client.callback;

import java.util.ArrayList;
import java.util.List;

public class Factory {
	private List<String> msgBox = new ArrayList<String>();
	
	public void add(String msg) {
		msgBox.add(msg);
	}
	
	/**
	 * 主动 vs 被动回调
	 * 
	 * 被动回调让factory不依赖reader, 而让reader依赖factory, 减少factory的职责
	 * 
	 * 让factory更具有内聚性. (更关注自己的职责, 职责分离)
	public void add(String path, XmlReader reader) {
		reader.loadXml(path);
	}
	*/
	public void show() {
		for (String msg : msgBox) {
			System.out.println(msg);
		}
	}
}
