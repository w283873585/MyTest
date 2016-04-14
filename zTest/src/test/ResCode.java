package test;

public enum ResCode {
	success("0"), 		  		// 成功
	passwordError("-90"), 		// 密码错误
	pdataExist("-91"), 	  		// 数据已存在
	unsupportedBusiness("-92"), // 不支持的业务或者类型
	rsaError("-93"), 			// RSA解密失败
	parmsIsNull("-94"), 		// 空参数或参数不合法
	md5AuthError("-95"), 		// MD5鉴权失败
	handleError("-96"),			// 处理失败或者操作失败
	dataNotExist("-97"), 		// 数据不存在
	serverError("-99"); 		// 服务器异常
	
	ResCode(String code) {
		this.code = code;
	}
	private String code = "";
	public boolean match(String str) {
		return code.equals(str);
	}
	public String value() {
		return code;
	}
	public static void main(String[] args) {
		System.out.println(ResCode.success.name());
	}
}