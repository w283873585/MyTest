package jun.learn.foundation.patterns.visitor2;

import java.util.HashMap;
import java.util.Map;

public class Counter extends Visitor{

	private int count = 0;
	private int maleCount = 0;
	private int femaleCount = 0;
	
	Map<String, Integer> scoreTable = new HashMap<String, Integer>();
	{
		scoreTable.put("joinWork", 7);
		scoreTable.put("doHaha", -1);
		scoreTable.put("cook", 5);
		scoreTable.put("wash", 2);
	}
	
	// 在伟大的毛爷爷年代
	// 不干活是没有公分的
	// 没有公分就没饭吃
	// 我是一个快乐的计数员
	// 专门统计男人和女的工分
	@Override
	public void visit(Male m) {
		if (m.doHaHa()) {
			count += scoreTable.get("doHaha");
			maleCount += scoreTable.get("doHaha");
		}
		if (m.doJoinWork()) {
			count += scoreTable.get("joinWork");
			maleCount += scoreTable.get("joinWork");
		}
	}

	@Override
	public void visit(Female f) {
		if (f.hasCook()) {
			count += scoreTable.get("cook");
			femaleCount += scoreTable.get("cook");
		}
		
		if (f.hasWash()) {
			count += scoreTable.get("wash");
			femaleCount += scoreTable.get("wash");
		}
	}
	
	public void report() {
		System.out.println("totalCount is: " + count);
		System.out.println("total maleCount is: " + maleCount);
		System.out.println("total femaleCount is: " + femaleCount);
	}
}
