import java.util.ArrayList;

public class SudokuNumber {
	int num = 0;
	ArrayList<Integer> possibilities = new ArrayList<Integer>();
	public SudokuNumber(int num) { this.num = num;}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public ArrayList<Integer> getPossibilities() {
		return possibilities;
	}
	public void setPossibilities(ArrayList<Integer> possibilities) {
		this.possibilities = possibilities;
	}

}
