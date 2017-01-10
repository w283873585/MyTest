package jun.learn.scene.softChain.annotation;

import java.util.Arrays;
import java.util.Comparator;

public class Chain{
	private int index = -1;
	private Node[] nodes = null;
	private Object target = null;
	
	private Chain(Node[] nodes, Object target) {
		this.target = target;
		this.nodes = nodes;
	}
	
	public static Chain bulidChain(ReqParamRestrictType[] type, Object target) {
		ReqParamRestrictType[] types = Arrays.copyOf(type, type.length);
		sort(types);
		return new Chain(types, target);
	}
	
	private static void sort(ReqParamRestrictType[] types) {
		Arrays.sort(types, new Comparator<ReqParamRestrictType>() {
			@Override
			public int compare(ReqParamRestrictType o1, ReqParamRestrictType o2) {
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
		public Result exec(Object target, Chain chain);
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