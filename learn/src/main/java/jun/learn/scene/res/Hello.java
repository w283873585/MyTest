package jun.learn.scene.res;

public enum Hello implements ResInfo{
	noInUse("", "");
	
	private final String resCode;
	private final String resDesc;
	private Hello(String resCode, String resDesc) {
		this.resCode = resCode;
		this.resDesc = resDesc;
	}
	@Override
	public String getResCode() {
		return resCode;
	}
	@Override
	public String getResDesc() {
		return resDesc;
	}
}
