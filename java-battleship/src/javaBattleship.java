//Java Battleship v0.1
//By: Harris Ransom

import java.util.Scanner;

public class javaBattleship {
	//Declares variables
	static final int BOARD_WIDTH = 15;
	static final int BOARD_HEIGHT = 14;
	static final int MIDLINE = 7;
	static Table<Character> board = new Table<Character>(BOARD_WIDTH, BOARD_HEIGHT, Character.class);
	boolean win = false;

	//MAIN
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Welcome to Java Battleship!");
		System.out.println("By: Harris Ransom");
		System.out.println("15 x 14 board, Player 1 on the left");
		boardSetup();
		input(scnr);

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

	//Gets player input at start of game
	public static void input(Scanner scnr) {
		int nextInput;
		Ship[] ships = new Ship[4];
		String[] names = {"Battleship", "Carrier", "Cruiser", "Frigate"};
		boolean[] vertical = new boolean[4];
		
		
		for (int i = 0; i < ships.length; i++) {
			System.out.println("Vertical " + names[i] + "?");
			vertical[i] = scnr.nextBoolean(); 
			//TODO: Finish Battleship input
			
			System.out.println("" + names[i] + " X coordinate: ");
			nextInput = scnr.nextInt();
			while (nextInput > BOARD_WIDTH) {
				System.out.println("Input valid X coordinate: ");
				nextInput = scnr.nextInt();	
			}
			ships[i].setxCoord(nextInput);
			
			System.out.println("" + names[i] + " Y coordinate: ");
			nextInput = scnr.nextInt();
			while (nextInput > BOARD_HEIGHT) {
				System.out.println("Input valid Y coordinate: ");
				nextInput = scnr.nextInt();	
			}
			ships[i].setyCoord(nextInput);
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