package jun.learn.foundation.patterns.observer2;

import java.util.ArrayList;
import java.util.List;

public class Boss {
	
	private List<Observer> list = new ArrayList<Observer>();
	
	public void noticeAll(){
		for (Observer ob : list) {
			ob.execute();
		}
	}
	
	public void add(Observer ob) {
		list.add(ob);
	}
	
	public static void main(String[] args) {
		Boss boss = new Boss();
		Observer li = new XiaoLi();
		Observer wang = new XiaoWang();
		boss.add(li);
		boss.add(wang);
		boss.noticeAll();
	}
}
