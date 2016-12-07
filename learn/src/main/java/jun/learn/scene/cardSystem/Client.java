package jun.learn.scene.cardSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

	public static void main(String[] args) {
		
		/**
		 * 初始化一张卡
		 * 
		 * 创建一个消费对象,
		 * 
		 * 用门面对象处理消费对象 {
		 * 
		 * 		根据消费对象id获取消费策略, (工厂 + 策略)
		 * 
		 * 		生成一个消费上下文, 
		 * 
		 * 		上下文执行消费策略		
		 * }
		 */
		
		
		
		
		Card card = initIC();
		
		System.out.println("****************  初始卡信息    ******************");
		
		showCard(card);
		
		boolean flag = true;
		
		while (flag) {
			Trade trade = createTrade();
			DeductionFacade.deduct(card, trade);
			System.out.println("\n============交易凭证=============");
			System.out.println(trade.getTradeNo() + "   交易成功");
			System.out.println("本次发生的交易金额为：" + trade.getAmount() / 100.0 + "元");
			showCard(card);
			System.out.println("是否需要退出?(y/n)");
			if (getInput().equalsIgnoreCase("y")) {
				flag = false;
			}
		}
	}
	
	private static Card initIC() {
		Card card = new Card();
		card.setCardNo("101010010");
		card.setFreeMoney(1000000);
		card.setSteadMoney(1000000);
		return card;
	}
	
	private static Trade createTrade() {
		Trade trade = new Trade();
		System.out.println("请输入交易编号：");
		trade.setTradeNo(getInput());
		System.out.println("请输入交易金额：");
		trade.setAmount(Integer.parseInt(getInput()));
		return trade;
	}

	private static void showCard(Card card) {
		System.out.println("IC卡编号：" + card.getCardNo());
		System.out.println("固定类型余额：" + card.getSteadMoney());
		System.out.println("自由类型余额：" + card.getFreeMoney());
	}
	
	private static String getInput() {
		String str = "";
		try {
			str = (new BufferedReader(new InputStreamReader(System.in))).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
