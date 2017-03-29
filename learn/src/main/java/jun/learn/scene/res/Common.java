package jun.learn.scene.res;
/**
 * 通用接口
 */
public enum Common implements ResInfo{
	success("0", "操作成功"),
	failed("-1", "操作失败"),
	serverError("-99", "服务器错误");
	
	private Common(String resCode, String resDesc) {
		this.resCode = resCode;
		this.resDesc = resDesc;
	}
	
	private String resDesc;
	private String resCode;
	public String getResCode() { return resCode; }
	public String getResDesc() { return resDesc; }
}