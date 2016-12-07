package jun.learn.scene.cardSystem;

public class DeductionFacade {

	public static Card deduct(Card card, Trade trade) {
		StrategyMan reg = getDeductionType(trade);
		
		IDeduction deduction = StrategyFactory.getDeduction(reg);
		
		DeductionContext context = new DeductionContext(deduction);
		
		context.exec(card, trade);
		
		return card;
	}
	
	private static StrategyMan getDeductionType(Trade trade) {
		if (trade.getTradeNo().contains("abc")) {
			return StrategyMan.FreeDeduction;
		} else {
			return StrategyMan.SteadyDeduction;
		}
	}
}
