package jun.learn.scene.res;
/**
 * 通用接口
 */
public enum Common{
	/**
	 * 操作成功
	 */
	@ResInfo(resCode="0", resDesc="操作成功")
	success,
	
	/**
	 * 操作失败
	 */
	@ResInfo(resCode="-1", resDesc="操作失败")
	failed,
	
	/**
	 * 系统错误
	 */
	@ResInfo(resCode="-99", resDesc="服务器错误")
	serverError;
}