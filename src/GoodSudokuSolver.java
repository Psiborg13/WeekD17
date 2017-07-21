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
		printBoard(board);
		boolean completed = false;
		makePossibilities();
		while(!completed){	
			reduceNumbers();
			completed = isFull(board);
			//printBoard(board);
		}
		//printBoard(board);
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
		lastBoard[0][0].num = 10;
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
				SudokuNumber[][] result = guess(board,x,y);
				if(result.length == 1){
					board[x][y].getPossibilities().remove(0);
				} else {
					board = result;
					printBoard(board);
				}
			} else {
				System.out.println("Code not working. This is totally an error. What do you get when you multiply six by nine?");
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
							System.out.println("No possibilities at "+x+" "+y);
							System.exit(0);
							//printBoard(board);
						}
					}
				}
			}
		}
	}

	
	
	
	
	
	
	
	public static SudokuNumber[][] guess(SudokuNumber[][] board, int x, int y){
		System.out.println("Guessing: "+x+" "+y);
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
		lastNewBoard[0][0].num = 10;
		boolean completed = false;
		boolean working = true;
		while(!completed && working){
			//working is false if any number runs out of possibilities 
			DumbPair d = guessAtReducingNumbers(newBoard, lastNewBoard);
			working = d.isBool();
			lastNewBoard = d.getLast();
			if(d.isGuessMade()){
				newBoard = d.getArray();
			}
			//completed is the same as "is the board full"
			completed = isFull(newBoard);
			//printBoard(newBoard);
		}
		
		if(working){
			return newBoard;
		} else{
			return new SudokuNumber[1][1];
		}
	}
	
	
	
	
	
	
	
	
	public static DumbPair guessAtReducingNumbers(SudokuNumber[][] newBoard, SudokuNumber[][] lastNewBoard){
		boolean guessMade = false;
		boolean guessFailed = false;
		boolean geves = true;
		for (int i = 0; i < newBoard.length; i++) {
			for (int j = 0; j < newBoard.length; j++) {
				if(newBoard[i][j].num!=lastNewBoard[i][j].num){
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
						//System.out.println(x+" "+newBoard[j][k].num+" "+newBoard[j][k].getPossibilities().size()+" "+i);
						//System.out.println(newBoard[j][k].num+" "+newBoard[j][k].getPossibilities()+" "+j+" "+k);
						//System.out.println(newBoard[j][k].getPossibilities());
						//System.out.println();
						if(x==-1&&newBoard[j][k].num==0&&newBoard[j][k].getPossibilities().size() == i){
							x = j;
							y = k;
							System.out.println("X: " + x);
							System.out.println("Y: " + y);
						}
					}
				}
			}
			if(x!=-1){
				System.out.println("guessing guessing "+x);
				SudokuNumber[][] result = guess(newBoard,x,y);
				guessMade = true;
				System.out.println("meme #"+result.length);
				guessFailed = result.length == 1;
				if(result.length == 1){
					newBoard[x][y].getPossibilities().remove(0);
				} else {
					newBoard = result;
				}
			} else {
				System.out.println("x is -1");
				return new DumbPair(false, guessMade, guessFailed, newBoard, lastNewBoard);
				//System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
				//System.exit(0);
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
						System.out.println("temp: " + temp);
						newBoard[x][y].setPossibilities(temp);
						if(newBoard[x][y].getPossibilities().size()==1){
							newBoard[x][y].setNum(newBoard[x][y].getPossibilities().get(0));
						} else if(newBoard[x][y].getPossibilities().size() == 0){
							//System.out.println("Iffleflip");
							//printBoard(newBoard);
							return new DumbPair(false, false, false, newBoard, lastNewBoard);
						}
					}
				}
			}
		}
		
		return new DumbPair(true, guessMade, guessFailed, newBoard, lastNewBoard);
	}
	
	
	
	
	
	
	
	
	
	public static void printBoard(SudokuNumber[][] board){
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
	
	
	
	
	
	
	

	public static boolean isFull(SudokuNumber[][] board){
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
