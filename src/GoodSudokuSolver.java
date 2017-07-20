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
			//printBoard();
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
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				lastBoard[i][j] = new SudokuNumber(0);
				lastBoard[i][j].setNum(board[i][j].num);
				lastBoard[i][j].possibilities = board[i][j].possibilities;
			}
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
		if(equalBoards()){
			int x = -1;
			int y = -1;
			for (int i = 2; i <= 9; i++) {
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
				System.exit(0);
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
							System.exit(0);
							//printBoard();
						}
					}
				}
			}
		}
	}

	public static SudokuNumber[][] guess(int x, int y){
		SudokuNumber[][] newBoard;
		SudokuNumber[][] lastNewBoard;
		newBoard = new SudokuNumber[9][9];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				newBoard[i][j] = new SudokuNumber(0);
				newBoard[i][j].setNum(board[i][j].num);
				newBoard[i][j].possibilities = board[i][j].possibilities;
			}
		}
		lastNewBoard = new SudokuNumber[9][9];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				lastNewBoard[i][j] = new SudokuNumber(0);
				lastNewBoard[i][j].setNum(board[i][j].num);
				lastNewBoard[i][j].possibilities = board[i][j].possibilities;
			}
		}
		newBoard[x][y].num = newBoard[x][y].getPossibilities().get(0);
		boolean completed = false;
		boolean working = true;
		while(!completed && working){
			working = guessAtReducingNumbers(newBoard, lastNewBoard);
			completed = isFull();
			printBoard();
		}
		if(working){
			return newBoard;
		} else{
			return new SudokuNumber[1][1];
		}
	}
	
	public static boolean guessAtReducingNumbers(SudokuNumber[][] newBoard, SudokuNumber[][] lastNewBoard){
		boolean geves = true;
		for (int i = 0; i < newBoard.length; i++) {
			for (int j = 0; j < newBoard.length; j++) {
				if(newBoard[i][j].num!=lastNewBoard[i][j].num){
					System.out.println(newBoard[i][j]+"x"+lastNewBoard[i][j]);
					geves = false;
				}
			}
		}
		if(geves){
			int x = -1;
			int y = -1;
			for (int i = 2; i < 9; i++) {
				for (int j = 0; j < newBoard.length; j++) {
					for (int k = 0; k < newBoard.length; k++) {
						if(x==-1&&newBoard[j][k].num==0&&newBoard[j][k].getPossibilities().size() == i){
							x = j;
							y = k;
						}
					}
				}
			}
			if(x!=-1){
				SudokuNumber[][] result = guess(x,y);
				if(result.length == 1){
					newBoard[x][y].getPossibilities().remove(0);
				} else {
					newBoard = result;
				}
			} else {
				System.err.println("Code not working. This is totally an error. You're turning into a penguin. Stop it.");
			}
		}
		else{
			lastNewBoard = newBoard;
			for (int x = 0; x < newBoard.length; x++) {
				for (int y = 0; y < newBoard[0].length; y++) {
					if(newBoard[x][y].num == 0){			
						ArrayList<Integer> cant = new ArrayList<Integer>();
						cant.clear();
						for (int i = 0; i < newBoard.length; i++) {
							cant.add(newBoard[x][i].num);
							cant.add(newBoard[i][y].num);
						}
						for (int i = (x/3)*3; i < ((x/3)+1)*3; i++) {
							for (int j = (y/3)*3; j < ((y/3)+1)*3; j++) {
								cant.add(newBoard[i][j].num);
							}
						}
						ArrayList<Integer> temp=new ArrayList<Integer>();
						for(Integer e:newBoard[x][y].getPossibilities()){
							temp.add(e);
						}
						temp.removeAll(cant);
						newBoard[x][y].setPossibilities(temp);
						if(newBoard[x][y].getPossibilities().size()==1){
							newBoard[x][y].setNum(newBoard[x][y].getPossibilities().get(0));
						} else if(newBoard[x][y].getPossibilities().size() == 0){
							return false;
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
				if(board[i][j].num!=lastBoard[i][j].num){
					return false;
				}
			}
		}
		return true;
	}

}
