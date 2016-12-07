package jun.learn.foundation.patterns.command1;

public class ZhangSan extends Receiver{

	@Override
	public void goDie() {
		System.out.println("十年后又是一条好油条！");
		
	}

	@Override
	public void goHappy() {
		
		System.out.println("炸的真爽！");
		
	}

	@Override
	public void goMeeting() {
		System.out.println("大王叫我去约会！");
		
	}

	@Override
	public void goWork() {
		System.out.println("大王叫我去上班！");
		
	}

}
