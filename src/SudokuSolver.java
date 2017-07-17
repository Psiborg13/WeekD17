import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class SudokuSolver {

	static int[][] board = new int[9][9];

	public static void main(String[] args) {
		printBoard();
		boolean completed = false;
		while(!completed){
			for (int i = 1; i < 10; i++) {
				fillHorizontal(i);
			}
			fillVertical();
			fillSquare();
			completed = isFull();
		}
		printBoard();
	}

	public static void fillHorizontal(int num){
		for(int i = 0; i < 3; i++){
			int row1 = -1;
			int row2 = -1;
			boolean row3 = false;
			int iVal = i;
			for (int j = 0; j < 3; j++) {
				for(int k = 0; k < 9; k++){
					if(board[i*3+j][k] == num){
						if(row1 != -1 && row2 != -1){
							row3 = true;
							j = 3;
						}
						else if(row1 != -1){
							row2 = j;
						} else{
							row1 = j;
						}
					}
				}
			}
			if(row1 != -1 && row2 != -1 && !row3){
				int oddRow = whichLine(row1, row2);
			}
			
		}
	}

	public static void fillVertical(){

	}

	public static void fillSquare(){

	}

	public static void printBoard(){
		for (int i = 0; i < 13; i++) {
			System.out.print("-");
		}
		System.out.println();
		for(int i = 0; i < 3; i++){
			for(int a = 0; a < 3; a++){
				System.out.print("|");
				for(int j = 0; j < 3; j++){
					for (int j2 = 0; j2 < 3; j2++) {
						System.out.print(board[i*3+a][j*3+j2]);
					}
					System.out.print("|");
				}
				System.out.println();
			}
			for (int o = 0; o < 13; o++) {
				System.out.print("-");
			}
			System.out.println();
		}
	}

	public static boolean isFull(){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if(board[i][j] == 0){
					return false;
				}
			}
		}
		return true;
	}

	public static int whichLine(int i1, int i2){
		int[] temp = new int[2];
		temp[0]= i1;
		temp[1] = i2;
		Arrays.sort(temp);
		for (int i = 0; i < temp.length; i++) {
			if(temp[i] != i){
				return i;
			}
		}
		return 2;
	}
}
