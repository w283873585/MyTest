package jun.learn.foundation.enums;

public enum EnumConstant {
	DATE_TIME{
		String getInfo() {
			return "DATE_TIME";
		}
	},
	CLASSPATH{
		String getInfo() {
			return "CLASSPATH";
		}
	},
	VERSION{
		String getInfo() {
			return "DATE_TIME";
		}
	};
	
	abstract String getInfo();
	
	public static void main(String[] args) {
		for (EnumConstant e : EnumConstant.values()) {
			System.out.println(e.getInfo());
		}
	}
}
