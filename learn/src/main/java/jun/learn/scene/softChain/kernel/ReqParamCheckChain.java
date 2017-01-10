package jun.learn.scene.softChain.kernel;

import java.util.Arrays;
import java.util.Comparator;

public class ReqParamCheckChain{
	private int index = -1;
	private Node[] nodes = null;
	private Object target = null;
	
	private ReqParamCheckChain(Node[] nodes, Object target) {
		this.target = target;
		this.nodes = nodes;
	}
	
	public static ReqParamCheckChain bulidChain(Node[] type, Object target) {
		Node[] types = Arrays.copyOf(type, type.length);
		sort(types);
		return new ReqParamCheckChain(types, target);
	}
	
	private static void sort(Node[] types) {
		Arrays.sort(types, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.ordinal() - o2.ordinal();
			}
		});
	}
	
	public Result proceed() {
		Node node = nodes[++index];
		return node.exec(target, this);
	}
	
	public void setTarget(Object target) {
		this.target = target;
	}
	
	public Object getTarget() {
		return this.target;
	}
	
	public interface Node{
		public Result exec(Object target, ReqParamCheckChain chain);
		
		public int ordinal();
	}

	public static class Result{
		private Object result;
		private boolean success;
		
		public Result(boolean suc, Object o) {
			this.success = suc;
			this.result = o;
		}
		public boolean isSuccess() {
			return success;
		}
		public Object getResult() {
			return result;
		}
	}
}