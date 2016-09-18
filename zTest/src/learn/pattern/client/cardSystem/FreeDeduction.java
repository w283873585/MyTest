package learn.pattern.client.cardSystem;

public class FreeDeduction implements IDeduction{

	@Override
	public boolean exec(Card card, Trade trade) {
		int halfMoney = trade.getAmount() / 2;
		card.setFreeMoney(card.getFreeMoney() - halfMoney);
		card.setSteadMoney(card.getSteadMoney() - halfMoney);
		return true;
	}
}
