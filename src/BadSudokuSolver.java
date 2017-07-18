import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BadSudokuSolver {

	static int[][] board = new int[9][9];

	public static void main(String[] args) throws IOException {
		loadBoard();
		printBoard();
		boolean completed = false;
		while(!completed){
			for (int i = 1; i < 10; i++) {
				fillHorizontal(i);
			}
			for (int i = 1; i < 10; i++) {
				fillVertical(i);
			}
			fillSquare();
			fillRow();
			fillColumn();
			printBoard();
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
				board[i][j] = num;
			}
			read.read();
			read.read();
		}
	}

	public static void fillHorizontal(int num){
		for(int i = 0; i < 3; i++){
			int x1 = -1;
			int row1 = -1;
			int x2 = -1;
			int row2 = -1;
			boolean row3 = false;
			for (int j = 0; j < 3; j++) {
				for(int k = 0; k < 9; k++){
					if(board[(i*3)+j][k] == num){
						if(row1 != -1 && row2 != -1){
							row3 = true;
						}
						else if(row1 != -1){
							row2 = j;
							x2 = k/3;
						} else{
							row1 = j;
							x1 = k/3;
						}
					}
				}
			}
			if(row1 != -1 && row2 != -1 && !row3){
				int oddRow = whichLine(row1, row2);
				int unblocked = -1;
				int oddX = whichLine(x1, x2);
				for (int j = oddX*3; j < (oddX+1)*3; j++) {
					if(board[oddRow+i*3][j] == 0){
						boolean colGood = true;
						for (int k = 0; k < board.length; k++) {
							if(board[k][j] == num){
								colGood = false;
								break;
							}
						}
						if(colGood){
							if(unblocked == -1){
								unblocked = j;
							} else if (unblocked != -2 && unblocked != -1){
								unblocked = -2;
							}
						}
					}
				}
				if(unblocked != -2 && unblocked != -1){
					board[oddRow+i*3][unblocked] = num; 
				}
			}
		}
	}

	public static void fillVertical(int num){
		for(int i = 0; i < 3; i++){
			int column1 = -1;
			int y1 = -1;
			int column2 = -1;
			int y2 = -1;
			boolean column3 = false;
			for (int j = 0; j < 3; j++) {
				for(int k = 0; k < 9; k++){
					if(board[k][i*3+j] == num){
						if(column1 != -1 && column2 != -1){
							column3 = true;
						}
						else if(column1 != -1){
							column2 = j;
							y2 = k/3;
						} else{
							column1 = j;
							y1 = k/3;
						}
					}
				}
			}
			if(column1 != -1 && column2 != -1 && !column3){
				int oddRow = whichLine(column1, column2);
				int unblocked = -1;
				int oddY = whichLine(y1, y2);
				for (int j = oddY*3; j < (oddY+1)*3; j++) {
					if(board[j][oddRow+i*3] == 0){
						boolean rowGood = true;
						for (int k = 0; k < board.length; k++) {
							if(board[j][k] == num){
								rowGood = false;
								break;
							}
						}
						if(rowGood){
							if(unblocked == -1){
								unblocked = j;
							} else if (unblocked != -2){
								unblocked = -2;
							}
						}
					}
				}
				if(unblocked != -2 && unblocked != -1){
					board[unblocked][oddRow+i*3] = num; 
				}
			}
		}
	}

	public static void fillSquare(){
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				ArrayList<Integer> nums = new ArrayList<Integer>();
				int x = -1;
				int y = -1;
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						if(board[i*3+k][j*3+l] != 0){
							nums.add(board[i*3+k][j*3+l]);
						} else {
							x = i*3+k;
							y = j*3+l;
						}
					}
				}
				if(nums.size()==8){
					Object[] array0 = nums.toArray();
					int[] array = new int[array0.length];
					for (int k = 0; k < array0.length; k++) {
						array[k] = Integer.parseInt(array0[k].toString());
					}
					Arrays.sort(array);
					for (int k = 0; k < array.length; k++) {
						if(array[k]!=k+1){
							board[x][y] = k+1;
							return;
						}
					}
					board[x][y] = 9;
				}
			}
		}
	}

	public static void fillRow(){
		for (int i = 0; i < 9; i++) {
			ArrayList<Integer> nums = new ArrayList<Integer>();
			int x = -1;
			int y = -1;
			for (int j = 0; j < board.length; j++) {
				if(board[i][j] != 0){
					nums.add(board[i][j]);
				} else {
					x = i;
					y = j;
				}
			}
			if(nums.size()==8){
				Object[] array0 = nums.toArray();
				int[] array = new int[array0.length];
				for (int k = 0; k < array0.length; k++) {
					array[k] = Integer.parseInt(array0[k].toString());
				}
				Arrays.sort(array);
				for (int k = 0; k < array.length; k++) {
					if(array[k]!=k+1){
						board[x][y] = k;
						return;
					}
				}
				board[x][y] = 9;
			}
		}
	}

	public static void fillColumn(){
		for (int i = 0; i < 9; i++) {
			ArrayList<Integer> nums = new ArrayList<Integer>();
			int x = -1;
			int y = -1;
			for (int j = 0; j < board.length; j++) {
				if(board[j][i] != 0){
					nums.add(board[j][i]);
				} else {
					x = j;
					y = i;
				}
			}
			if(nums.size()==8){
				Object[] array0 = nums.toArray();
				int[] array = new int[array0.length];
				for (int k = 0; k < array0.length; k++) {
					array[k] = Integer.parseInt(array0[k].toString());
				}
				Arrays.sort(array);
				for (int k = 0; k < array.length; k++) {
					if(array[k]!=k+1){
						board[x][y] = k;
						return;
					}
				}
				board[x][y] = 9;
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
