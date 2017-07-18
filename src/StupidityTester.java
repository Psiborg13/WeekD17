import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileReader;
import java.io.IOException;

public class StupidityTester {

	static int[][] board = new int[9][9];
	static BigInteger fill;
	static BigInteger fillO;
	static BigInteger maxStupidity;
	static int bigIntLength = 81;

	public static void main(String[] args) throws IOException {
		loadBoard();
		printBoard();
		String temp = "";
		for (int i = 0; i < bigIntLength; i++) {
			temp+="1";
		}
		fill = new BigInteger(temp);
		String temp1 = "";
		for (int i = 0; i < bigIntLength; i++) {
			temp1+="9";
		}
		maxStupidity = new BigInteger(temp1);
		fillO = fill;
		boolean rows = false;
		boolean columns = false;
		boolean squares = false;
		long startTime = System.currentTimeMillis();
		while(fill.max(fillO.add(new BigInteger("100000")))!=fill){
			int[][] newBoard = increment();
			rows = checkRows(newBoard);
			columns = checkColumns(newBoard);
			squares = checkSquares(newBoard);
		}
		long tenThousandTime = System.currentTimeMillis() - startTime;
		System.out.println((maxStupidity.subtract(fill).divide(new BigInteger("100000")).
				multiply(new BigInteger(tenThousandTime+"").divide(new BigInteger("1000")
						.divide(new BigInteger("60").divide(new BigInteger("60").divide(new BigInteger("24"))))))));
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
		boolean hasZero = true;
		while(hasZero){
			fill = fill.add(new BigInteger("1"));
			String temp = fill+"";
			boolean temp1 = false;
			for (int i = 0; i < temp.length(); i++) {
				if(temp.charAt(i) == '0'){
					temp1 = true;
					break;
				}
			}
			hasZero = temp1;
		}
		int[][] newBoard = board;
		int count = 0;
		for (int i = 0; i < newBoard.length; i++) {
			for (int j = 0; j < newBoard.length; j++) {
				if(newBoard[i][j] != 0){
					newBoard[i][j] = Integer.parseInt(fill.toString().charAt(count)+"");
					count++;
				}
			}
		}
		return newBoard;
	}

	public static boolean checkRows(int[][] board){
		for (int i = 0; i < board.length; i++) {
			ArrayList<Integer> nums = new ArrayList<Integer>();
			for (int j = 0; j < board[0].length; j++) {
				if(nums.contains(board[i][j])){
					return false;
				}
				else{
					nums.add(board[i][j]);
				}
			}
		}
		return true;
	}

	public static boolean checkColumns(int[][] board){
		for (int i = 0; i < board.length; i++) {
			ArrayList<Integer> nums = new ArrayList<Integer>();
			for (int j = 0; j < board[0].length; j++) {
				if(nums.contains(board[j][i])){
					return false;
				}
				else{
					nums.add(board[j][i]);
				}
			}
		}
		return true;
	}
	
	public static boolean checkSquares(int[][] board){
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				ArrayList<Integer> nums = new ArrayList<Integer>();
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						if(nums.contains(board[i*3+k][j*3+l])){
							return false;
						}
						else{
							nums.add(board[i*3+k][j*3+l]);
						}
					}
				}
			}
		}
		return true;
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
