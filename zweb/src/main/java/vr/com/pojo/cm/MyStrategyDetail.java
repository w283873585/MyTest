package vr.com.pojo.cm;

public class MyStrategyDetail {
	/**
	 * 具体时间点做一件事情
	 * 
	 * 每周一，
	 * 每月的15号 	
	 * 具体日期： 10月23日
	 * 
	 * 5:30:00
	 */
	
	/**
	 * 周为null时，表示每周
	 */
	private Integer week;
	
	private Integer weekDay;
	
	/**
	 * 月份为null时， 表示每个月
	 */
	private Integer month;
	
	private Integer monthDay;
	
	private String desc;
	
	/**
	 * 具体时间点  15:00:00
	 */
	private String time;

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getMonthDay() {
		return monthDay;
	}

	public void setMonthDay(Integer monthDay) {
		this.monthDay = monthDay;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
