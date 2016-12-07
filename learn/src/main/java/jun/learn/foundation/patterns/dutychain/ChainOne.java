package jun.learn.foundation.patterns.dutychain;
public class ChainOne {
	public ChainOne next;
	
	public void setNext(ChainOne _next) {
		this.next = _next;
	}
	
	public void invoke(Object arg) {
		if (this.exec() == "what i wanted") {
			// dosomething
			// then just return;
			return;
		} else {
			// pass the request
			next.invoke(arg);
		}
	}
	
	public String exec() {
		return null;
	}
	
	
	public static void main(String[] args) {
		
	}
	
	/**
	 * 
	 * chain.exec(arg)
	 * 
	 * 
	 * 
	 */
}