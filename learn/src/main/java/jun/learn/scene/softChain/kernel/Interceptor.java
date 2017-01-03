package jun.learn.scene.softChain.kernel;

public interface Interceptor {
	
	public void intercept(MetaData metaData, ReqData reqData, ReqResult result);
}
