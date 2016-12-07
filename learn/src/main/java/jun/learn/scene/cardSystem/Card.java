package jun.learn.scene.cardSystem;

public class Card {

	private String cardNo = "";
	
	private int steadMoney = 0;
	
	private int freeMoney = 0;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public int getSteadMoney() {
		return steadMoney;
	}

	public void setSteadMoney(int steadMoney) {
		this.steadMoney = steadMoney;
	}

	public int getFreeMoney() {
		return freeMoney;
	}

	public void setFreeMoney(int freeMoney) {
		this.freeMoney = freeMoney;
	}
}
