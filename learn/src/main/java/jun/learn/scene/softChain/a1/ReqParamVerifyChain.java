package jun.learn.scene.softChain.a1;

import java.util.Arrays;

public class ReqParamVerifyChain{
	private Node[] nodes = null;
	private Object target = null;
	private Mode mode = Mode.and; 
	
	private ReqParamVerifyChain(Node[] nodes, Object target) {
		this.target = target;
		this.nodes = nodes;
	}
	
	ReqParamVerifyChain(Node[] nodes, Object target, Mode mode) {
		this(nodes, target);
		this.mode = mode;
	}
	
	public static ReqParamVerifyChain bulidChain(Node[] type, Object target) {
		Node[] types = Arrays.copyOf(type, type.length);
		return new ReqParamVerifyChain(types, target);
	}
	
	public Result execute() {
		for (Node cur : nodes) {
			Result r = cur.exec(target, this);
			if (mode.needQuitImmediate(r))
				return r;
		}
		return new Result(mode.finalStatus(), null);
	}
	
	public void setTarget(Object target) {
		this.target = target;
	}
	
	public Object getTarget() {
		return this.target;
	}
	
	public interface Node{
		public Result exec(Object target, ReqParamVerifyChain chain);
		
		public int ordinal();
	}
	
	
	public enum Mode{
		and,
		or;
		public boolean finalStatus() {
			return this == and;
		}
		
		public boolean needQuitImmediate(Result r) {
			return r.isSuccess() == (this == or); 
		}
	}

	public static class Result{
		private String result;
		private boolean success;
		
		public Result(boolean suc, String o) {
			this.success = suc;
			this.result = o;
		}
		public boolean isSuccess() {
			return success;
		}
		public String getResult() {
			return result;
		}
	}
}