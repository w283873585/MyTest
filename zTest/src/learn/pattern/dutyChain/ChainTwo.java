package learn.pattern.dutyChain;

import java.util.ArrayList;
import java.util.List;

public class ChainTwo {
	public List<Node> chain = new ArrayList<Node>();
	
	public void addNode(Node node) {
		chain.add(node);
	}
	
	public String invoke(Object arg) {
		return new ChainContext().invokeNext(arg);
	}
	
	public class ChainContext {
		int index = 0;
		public String invokeNext(Object arg) {
			if (index < chain.size()) {
				return chain.get(index++).exec(arg, this);
			} else {
				System.out.println("没有人处理这个");
				return null;
			}
		}
	}
	
	public static class Node {
		public String exec(Object arg, ChainContext context) { 
			String result = dosomething(arg);
			if (result == "what i want")
				return result;
			else 
				return context.invokeNext(arg);
		}

		private String dosomething(Object arg) {
			return null;
		}
	}
	/**
	 * 
	 * chain.exec(arg)
	 * 
	 * 
	 * 
	 */
}