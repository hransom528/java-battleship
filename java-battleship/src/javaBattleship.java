//Java Battleship v0.1
//By: Harris Ransom
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;

public class javaBattleship {
	//Declares variables
	static final int BOARD_WIDTH = 30;
	static final int BOARD_HEIGHT = 14;
	static final int MIDLINE =  (int) (30.0 / 2.0) - 1;
	static Table<Character> board = new Table<Character>(BOARD_WIDTH, BOARD_HEIGHT, Character.class);
	static Ship[] ships = new Ship[4];
	static Ship[] computerShips = new Ship[4];
	static boolean playerWin = false;
	static boolean computerWin = false;

	//MAIN
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Welcome to Java Battleship!");
		System.out.println("By: Harris Ransom");
		System.out.println("" + BOARD_WIDTH + " x " + BOARD_HEIGHT + " board, Player 1 on the left.");

		//Sets up game
		boardSetup();
		printBoard();
		try {
			input(scnr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		gameSetup();
		printBoard();

		//While the game does not have a winner
		while ((!playerWin) || (!computerWin)) {
			playerMove(scnr);
			computerMove();
			printBoard();
		}
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
	public static void input(Scanner scnr) throws Exception {
		int nextInput;
		char nextChar;
		String[] names = {"Battleship", "Carrier", "Cruiser", "Frigate"};
		boolean[] vertical = new boolean[4];

		//For every ship type
		for (int i = 0; i < ships.length; i++) {
			//Gets if vertical
			System.out.println("Vertical " + names[i] + " (yes/no)?");
			nextChar = scnr.next().charAt(0);
			if ((nextChar == 'y') || (nextChar == 'Y')) {
				vertical[i] = true; 
			}
			else {
				vertical[i] = false; 
			}

			//Initializes new ship
			if (i == 0) {
				ships[i] = new Battleship(vertical[i]); 
			}
			else if (i == 1) {
				ships[i] = new Carrier(vertical[i]);
			}
			else if (i == 2) {
				ships[i] = new Cruiser(vertical[i]);
			}
			else if (i == 3) {
				ships[i] = new Frigate(vertical[i]);
			}
			else {
				throw new Exception("Error in initializing ships!");
			}

			//Gets ship X coordinate
			System.out.println("" + names[i] + " X coordinate: ");
			nextInput = (int) scnr.nextInt();
			while ((nextInput >= MIDLINE) || (nextInput < 1) || (nextInput > BOARD_WIDTH)) {
				System.out.println("Input valid X coordinate on your side: ");
				nextInput = (int) scnr.nextInt();	
			}
			ships[i].setxCoord(nextInput - 1);

			//Gets ship Y coordinate
			System.out.println("" + names[i] + " Y coordinate: ");
			nextInput = (int) scnr.nextInt();
			while ((nextInput > BOARD_HEIGHT) || (nextInput < 1)) {
				System.out.println("Input valid Y coordinate on your side: ");
				nextInput = (int) scnr.nextInt();	
			}
			ships[i].setyCoord(nextInput - 1);
		}
	}

	//Sets up game for playing
	public static void gameSetup() {
		Random rnd = new Random();
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].getLength(); j++) {
				if (board.get(j, i) != '*') {
					if (ships[i].isVertical()) {
						board.set(ships[i].getxCoord(), ships[i].getyCoord() + j, ships[i].getIdentifier());
					}
					else {
						board.set(ships[i].getxCoord() + j, ships[i].getyCoord(), ships[i].getIdentifier());
					}
				}
				else {
					throw new IllegalStateException("Ships cross over each other at (" + j + ", " + i + ")!");
				}
			}	
		}

		for (int i = 0; i < computerShips.length; i++) {
			if (i == 0) {
				computerShips[i] = new Battleship(rnd.nextBoolean());
			}
			else if (i == 1) {
				computerShips[i] = new Carrier(rnd.nextBoolean()); 
			}
			else if (i == 2) {
				computerShips[i] = new Cruiser(rnd.nextBoolean());
			}
			else if (i == 3) {
				computerShips[i] = new Frigate(rnd.nextBoolean()); 
			}
			
			computerShips[i].setxCoord(xCoord);
		}
		//TODO: Initialize computer ships
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

	//Does 1 turn for the player
	public static void playerMove(Scanner scnr) {
		int playerXGuess = -1;
		int playerYGuess = -1;

		//Gets X coord guess
		System.out.println("Your X coordinate guess?");
		try {
			playerXGuess = (int) scnr.nextInt();
			while ((playerXGuess <= MIDLINE) || (playerXGuess < 1) || (playerXGuess > BOARD_WIDTH)) {
				System.out.println("Input valid X coordinate: ");
				playerXGuess = (int) scnr.nextInt();
			}
		} catch (InputMismatchException e) {
			System.out.println("Cannot accept that type of input!");
			e.printStackTrace();
		}


		//Gets Y coord guess
		System.out.println("Your Y coordinate guess?");
		try {
			playerYGuess = (int) scnr.nextInt();
			while ((playerYGuess < 1) || (playerYGuess > BOARD_HEIGHT)) {
				System.out.println("Input valid Y coordinate: ");
				playerYGuess = (int) scnr.nextInt();
			}
		} catch (InputMismatchException e) {
			System.out.println("Cannot accept that type of input!");
			e.printStackTrace();
		}


		//Tests for a hit
		if ((board.get(playerXGuess, playerYGuess) != '*') && (board.get(playerXGuess, playerYGuess) != '~')) {
			System.out.println("HIT!");
			board.set(playerXGuess, playerYGuess, 'X');
		}
		else {
			System.out.println("Miss!");
			board.set(playerXGuess, playerYGuess, '~');
		}
	}

	//Does 1 turn for the computer
	public static void computerMove() {
		Random rnd = new Random();
		int computerXGuess;
		int computerYGuess;

		//Gets random computer guess
		computerXGuess = rnd.nextInt(MIDLINE);
		computerYGuess = rnd.nextInt(BOARD_HEIGHT);
		while ((board.get(computerXGuess, computerYGuess) == 'X') || (board.get(computerXGuess, computerYGuess) == '~')) {
			computerXGuess = rnd.nextInt(MIDLINE);
			computerYGuess = rnd.nextInt(BOARD_HEIGHT);
		}

		//Tests for a hit
		if (board.get(computerXGuess, computerYGuess) != '*') {
			System.out.println("HIT!");
			board.set(computerXGuess, computerYGuess, 'X');
		}
		else {
			System.out.println("Miss!");
			board.set(computerXGuess, computerYGuess, '~');
		}
	}

	//Checks for a win condition
	public static void checkForWin() {

	}
}