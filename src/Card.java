
public class Card {

	int suit;
	int value;
	
	public Card(int v, int s) {
		value = v;
		suit = s;
	}

	public int getSuit() {
		return suit;
	}

	public void setSuit(int suit) {
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString(){
		String v = "";
		switch(value){
		case 11: v = "Jack"; break;
		case 12: v = "Queen"; break;
		case 13: v = "King"; break;
		case 14: v = "Ace"; break;
		default: v = ""+value; break;
		}
		String s = "";
		switch(suit){
		case 0: s = "diamonds"; break;
		case 1: s = "clubs"; break;
		case 2: s = "hearts"; break;
		case 3: s = "spades"; break;
		}
		return v+" of "+s;
	}

}
