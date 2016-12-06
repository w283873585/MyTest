package jun.learn.foundation.enums;

import java.util.EnumMap;
import java.util.Map;


public class EnumMaps {
	interface Command{
		void action();
	}
	
	enum CommandKey{
		RUN, DUMP;
	}
	
	public static void main(String[] args) {
		EnumMap<CommandKey, Command> em = new EnumMap<CommandKey, Command>(CommandKey.class);
		em.put(CommandKey.RUN, new Command(){
			public void action() {
				System.out.println("i can run anywhere");
			}
		});
		em.put(CommandKey.DUMP, new Command(){
			public void action() {
				System.out.println("i can dump anywhere");
			}
		});
		
		for (Map.Entry<CommandKey, Command> m : em.entrySet()) {
			m.getValue().action();
		}
		
		try { // If there's no value for a particular key:
			em.get(CommandKey.DUMP).action();
		} catch(Exception e) {
			System.out.println(e);
		}
		/*
		 * 常量相关方法相比�?
		 * EnumMap允许程序员改变�?对象，�?不是在编译时确定
		 * */
	}
}
