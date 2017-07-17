
public class Deck {

	Card[] deck = new Card[52];
	
	public Deck(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 13; j++){
				deck[j+13*i] = new Card(j+2, i);
			}
		}
	}

	public Card[] getDeck() {
		return deck;
	}

	public void setDeck(Card[] deck) {
		this.deck = deck;
	}
	
	public String toString(){
		String retVal = "";
		for(int i = 0; i < deck.length; i++){
			retVal+=deck[i].toString()+"\n";
		}
		return retVal;
	}

}
