import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileReader;
import java.io.IOException;

public class BadSudokuSolver {

	static int[][] board = new int[9][9];
	static BigInteger fill;
	static int bigIntLength = 81;

	public static void main(String[] args) throws IOException {
		loadBoard();
		printBoard();
		String temp = "";
		for (int i = 0; i < bigIntLength; i++) {
			temp+="1";
		}
		fill = new BigInteger(temp);
		boolean rows = false;
		boolean columns = false;
		boolean squares = false;
		while(!rows || !columns || !squares || false){
			int[][] newBoard = increment();
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
					bigIntLength--;
				} catch (Exception e) {} 
				board[i][j] = num;
			}
			read.read();
			read.read();
		}
	}
	
	public static int[][] increment(){
		
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
						if(board[i*3+a][j*3+j2] == 0){
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
