//Java Battleship v0.1
//By: Harris Ransom

import java.util.Scanner;

public class javaBattleship {
	//Declares variables
		Scanner scnr = new Scanner(System.in);
		static char[][] board = new char[31][20];
		static final int BOARD_WIDTH = 31;
		static final int BOARD_HEIGHT = 20;
		static final int MIDLINE = 10;
		boolean win = false;
	
	//MAIN
	public static void main(String[] args) {
		//Get input to start game
		System.out.println("Welcome to Java Battleship!");
		System.out.println("By: Harris Ransom");
		boardSetup();
		printBoard(board);
		
	}
	
	//Sets up the board 
	public static void boardSetup() {
		//Creates board
		for (int r = 0; r < BOARD_HEIGHT; r++) { //For every column
			for (int c = 0; c < BOARD_WIDTH; c++) { //For every row 
				board[c][r] = '*';  //FIXME: XY coordinate system in Battleship 
			}
		}
		
		//Creates dividing line
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			board[i][MIDLINE] = '|'; 
		}
		
	}
	
	//Outputs the current board 
	public static void printBoard(char[][] boardArr) {
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++) {
				 System.out.print(boardArr[i][j]);
			}
			System.out.print("\n");
		}
	}

}
