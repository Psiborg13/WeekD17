import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GoodSudokuSolver {

	static SudokuNumber[][] lastBoard = new SudokuNumber[9][9];
	static SudokuNumber[][] board = new SudokuNumber[9][9];

	public static void main(String[] args) throws IOException {
		loadBoard();
		printBoard();
		boolean completed = false;
		makePossibilities();
		while(!completed){
			reduceNumbers();
			completed = isFull();
			printBoard();
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
				ArrayList<Integer> nien = new ArrayList<Integer>();	
				for(int m = 1; m < 10; m++){
					nien.add(m);
				}
				ArrayList<Integer> nums = new ArrayList<Integer>();
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						if(board[i*3+k][j*3+l].num != 0){
							nums.add(board[i*3+k][j*3+l].num);
						}
					}
				}
				nien.removeAll(nums);
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						if(board[i*3+k][j*3+l].num == 0){
							board[i*3+k][j*3+l].setPossibilities(nien);
						}
					}
				}
			}
		}
	}

	public static void reduceNumbers(){
		if(!equalBoards()){
			int x = -1;
			int y = -1;
			for (int i = 2; i < 9; i++) {
				for (int j = 0; j < board.length; j++) {
					for (int k = 0; k < board.length; k++) {
						if(x==-1&&board[j][k].num==0&&board[j][k].getPossibilities().size() == i){
							x = j;
							y = k;
						}
					}
				}
			}
			if(x!=-1){
				SudokuNumber[][] result = guess(x,y);
				if(result.length == 1){
					board[x][y].getPossibilities().remove(0);
				} else {
					board = result;
					printBoard();
					System.exit(0);
				}
			} else {
				System.err.println("Code not working. This is totally an error. What do you get when you multiply six by nine?");
			}
		}
		else{
			lastBoard = board;
			for (int x = 0; x < board.length; x++) {
				for (int y = 0; y < board[0].length; y++) {
					if(board[x][y].num == 0){			
						ArrayList<Integer> cant = new ArrayList<Integer>();
						cant.clear();
						for (int i = 0; i < board.length; i++) {
							cant.add(board[x][i].num);
							cant.add(board[i][y].num);
						}
						for (int i = (x/3)*3; i < ((x/3)+1)*3; i++) {
							for (int j = (y/3)*3; j < ((y/3)+1)*3; j++) {
								cant.add(board[i][j].num);
							}
						}
						ArrayList<Integer> temp=new ArrayList<Integer>();
						for(Integer e:board[x][y].getPossibilities()){
							temp.add(e);
						}
						temp.removeAll(cant);
						board[x][y].setPossibilities(temp);
						if(board[x][y].getPossibilities().size()==1){
							board[x][y].setNum(board[x][y].getPossibilities().get(0));
						} else if(board[x][y].getPossibilities().size() == 0){
							System.err.println("Code not working. This is totally an error. This is the one and only error.");
							printBoard();
						}
					}
				}
			}
		}
	}

	public static SudokuNumber[][] guess(int x, int y){
		SudokuNumber[][] newBoard = board;
		newBoard[x][y].num = newBoard[x][y].getPossibilities().get(0);
 		return new SudokuNumber[1][1];
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

	public static boolean equalBoards(){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(!board[i][j].equals(lastBoard[i][j])){
					return false;
				}
			}
		}
		return true;
	}

}
