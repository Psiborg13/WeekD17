
public class DumbPair {

	boolean bool;
	boolean guessMade;
	boolean guessFailed;
	SudokuNumber[][] array;
	SudokuNumber[][] last;
	
	public DumbPair(boolean b, boolean m, boolean g, SudokuNumber[][] s, SudokuNumber[][] last) {
		bool = b;
		guessMade = m;
		guessFailed = g;
		array = s;
		this.last = last;
	}

	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

	public SudokuNumber[][] getArray() {
		return array;
	}

	public void setArray(SudokuNumber[][] array) {
		this.array = array;
	}

	public boolean isGuessMade() {
		return guessMade;
	}

	public void setGuessMade(boolean guessMade) {
		this.guessMade = guessMade;
	}

	public boolean isGuessFailed() {
		return guessFailed;
	}

	public void setGuessFailed(boolean guessFailed) {
		this.guessFailed = guessFailed;
	}

	public SudokuNumber[][] getLast() {
		return last;
	}

	public void setLast(SudokuNumber[][] last) {
		this.last = last;
	}

	
}
