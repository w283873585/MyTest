package learn.client.check;

import java.io.Serializable;

/**
 * 用户手机表中码注册并送红包口令服务接口请求参数
 * 
 * @author 易茂剑
 * 
 */
public class RequestActivityUserMobileRegisterVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3934761184117127438L;

	@VR_Constraint({VR_ConstraintEnum.NoEmpty, VR_ConstraintEnum.Rsa})
	private String mobile; // 手机号码，非空，字符串，RSA加密

	@VR_Constraint({VR_ConstraintEnum.NoEmpty, VR_ConstraintEnum.Rsa})
	private String loginPassword; // 登陆密码，非空，字符串，MD5加密后再进行RSA加密

	@VR_Constraint({VR_ConstraintEnum.NoEmpty, VR_ConstraintEnum.Rsa, VR_ConstraintEnum.number, VR_ConstraintEnum.length6})
	private String smsCode; // 短信验证码，非空，字符串，6位数字，RSA加密

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
}
