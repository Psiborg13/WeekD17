import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GoodSudokuSolver {

	static SudokuNumber[][] board = new SudokuNumber[9][9];

	public static void main(String[] args) throws IOException {
		loadBoard();
		printBoard();
		boolean completed = false;
		makePossibilities();
		while(!completed){
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					
				}
			}
			completed = isFull();
		}
		printBoard();
	}

	public static void loadBoard() throws IOException{
		BufferedReader read = null;
		try {
			read = new BufferedReader(new FileReader(new File("C:\\Users\\SummerTech\\"
					+ "Documents\\Eclipse Workspace\\Sam Perlman\\src\\SudokuBoard")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				int num = 0;
				try {
					char c = (char) read.read();
					num = Integer.parseInt(c+"");
				} catch (Exception e) {} 
				board[i][j] = new SudokuNumber(num);
			}
			read.read();
			read.read();
		}
	}

	public static void makePossibilities(){
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				ArrayList<Integer> nums = new ArrayList<Integer>();
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						if(board[i*3+k][j*3+l].num != 0){
							nums.add(board[i*3+k][j*3+l].num);
						}
					}
				}
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						if(board[i*3+k][j*3+l].num != 0){
							board[i*3+k][j*3+l].setPossibilities(nums);
						}
					}
				}
			}
		}
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
						if(board[i*3+a][j*3+j2].num == 0){
							System.out.print("*");
						}
						else{
							System.out.print(board[i*3+a][j*3+j2]);
						}
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
				if(board[i][j].num == 0){
					return false;
				}
			}
		}
		return true;
	}

}
