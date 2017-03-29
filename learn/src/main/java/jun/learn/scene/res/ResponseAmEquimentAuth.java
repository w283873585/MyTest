package jun.learn.scene.res;


public class ResponseAmEquimentAuth extends BaseResInfo<Hello>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7009761585651952742L;

	
    private String equTitle; 				// 设备名称，字符串
    private Integer equStatus;				// 设备状态，正整数，非空，0表示正常使用中，1表示授权已过期
    private String authorized_start_time; 	// 授权开始时间，字符串，格式; //年月日时分秒
    private String authorized_end_time; 	// 授权结束时间，字符串，格式; //年月日时分秒
    private Integer authorized_day; 		// 授权天数，正整数
	private Integer surplus_authorized_day;	// 剩余授权天数，整数，非空
	private String equipmentQrCodeUrl;
	private String equPassword;				// 设备密码，字符串，非空，-1表示当前设备未设备任何密码
    
    public String getEquTitle() {
		return equTitle;
	}
	public void setEquTitle(String equTitle) {
		this.equTitle = equTitle;
	}
	public String getAuthorized_start_time() {
		return authorized_start_time;
	}
	public void setAuthorized_start_time(String authorized_start_time) {
		this.authorized_start_time = authorized_start_time;
	}
	public String getAuthorized_end_time() {
		return authorized_end_time;
	}
	public void setAuthorized_end_time(String authorized_end_time) {
		this.authorized_end_time = authorized_end_time;
	}
	public Integer getAuthorized_day() {
		return authorized_day;
	}
	public void setAuthorized_day(Integer authorized_day) {
		this.authorized_day = authorized_day;
	}
	public Integer getEquStatus() {
		return equStatus;
	}
	public void setEquStatus(Integer equStatus) {
		this.equStatus = equStatus;
	}
	public Integer getSurplus_authorized_day() {
		return surplus_authorized_day;
	}
	public void setSurplus_authorized_day(Integer surplus_authorized_day) {
		this.surplus_authorized_day = surplus_authorized_day;
	}
	public String getEquipmentQrCodeUrl() {
		return equipmentQrCodeUrl;
	}
	public void setEquipmentQrCodeUrl(String equipmentQrCodeUrl) {
		this.equipmentQrCodeUrl = equipmentQrCodeUrl;
	}
	public String getEquPassword() {
		return equPassword;
	}
	public void setEquPassword(String equPassword) {
		this.equPassword = equPassword;
	}
}
