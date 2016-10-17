package vr.com.util.security;

/**
 * 返回码常量
 * 
 * @author 易茂剑
 * 
 */
public class ResCodeConstans {

	public final static String success = "0";// 成功

	public final static String passwordError = "-90"; // 密码错误

	public final static String dataExist = "-91"; // 数据已存在

	public final static String unsupportedBusiness = "-92"; // 不支持的业务或者类型

	public final static String rsaError = "-93"; // RSA解密失败

	public final static String parmsIsNull = "-94"; // 空参数或参数不合法

	public final static String md5AuthError = "-95"; // MD5鉴权失败

	public final static String handleError = "-96";// 处理失败或者操作失败

	public final static String dataNotExist = "-97"; // 数据不存在

	public final static String serverError = "-99"; // 服务器异常

}
