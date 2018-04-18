package jun.learn.scene.callback;

import java.util.ArrayList;
import java.util.List;

public class Factory {
	private List<Bean> msgBox = new ArrayList<Bean>();
	
	public void addBean(Bean bean) {
		msgBox.add(bean);
	}
	
	public void show() {
		for (Bean bean : msgBox) {
			System.out.println(bean.getMsg());
		}
	}
	
	/**
	 * 主动   vs 被动回调
	 * 
	 * 被动回调让factory不依赖reader, 而让reader依赖factory, 减少factory的职责
	 * 
	 * 让factory更具有内聚性. (更关注自己的职责, 职责分离)
	 */
	
	/**
	 * 依赖于xmlReader, 主动将xmlReader的加载的数据取出来, 并添加到自己的数据中
	 * 
	public void add(String path, XmlReader reader) {
		reader.loadXml(path);
	}
	*/
	
	
	
}
