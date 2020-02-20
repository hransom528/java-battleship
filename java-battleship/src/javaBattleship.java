//Java Battleship v0.1
//By: Harris Ransom

import java.util.Scanner;

public class javaBattleship {
	//Declares variables
		Scanner scnr = new Scanner(System.in);
		static final int BOARD_WIDTH = 15;
		static final int BOARD_HEIGHT = 14;
		static final int MIDLINE = 7;
		static Table<Character> board = new Table<Character>(BOARD_WIDTH, BOARD_HEIGHT, Character.class);
		boolean win = false;
	
	//MAIN
	public static void main(String[] args) {
		//Get input to start game
		System.out.println("Welcome to Java Battleship!");
		System.out.println("By: Harris Ransom");
		boardSetup();
		printBoard();
		
	}
	
	//Sets up the board 
	public static void boardSetup() {
		//Fills board with spaces
		for (int i = 0; i < board.getySize(); i++) {
			for (int j = 0; j < board.getxSize(); j++) {
				board.set(j, i, '*');
			}
		}
		
		//Creates dividing line
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			board.set(MIDLINE, i, '|');
		}
	}
	
	//Outputs the current board 
	public static void printBoard() {
		Character[][] table = board.getTable();
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				sb.append(table[i][j]);
				if (j < table[0].length - 1) {
					sb.append(" ");
				}
				else {
					sb.append("\n");
				}
			}
		}
		System.out.println(sb.toString());
	}
}