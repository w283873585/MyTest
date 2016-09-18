package learn.pattern.client.cardSystem;

public class SteadyDeduction implements IDeduction{

	@Override
	public boolean exec(Card card, Trade trade) {
		card.setFreeMoney(card.getFreeMoney() - trade.getAmount());
		return true;
	}
}
