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
	static boolean playAgain = true;

	//MAIN
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Welcome to Java Battleship!");
		System.out.println("By: Harris Ransom");
		System.out.println("" + BOARD_WIDTH + " x " + BOARD_HEIGHT + " board, Player 1 on the left.");

		//Loops until player quits game
		while (playAgain) {
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
				checkSunkShips();
				printBoard();
			}

			//Determines who won
			if (checkForPlayerWin()) {
				System.out.println("You won!");
			}
			else {
				System.out.println("You lost!");
			}

			//Checks to see if player wants to play again
			System.out.println("Play again?");
			char nextChar = scnr.next().charAt(0);
			if ((nextChar == 'y') || (nextChar == 'Y')) {
				playAgain = true;
				reset();
			}
			else {
				playAgain = false;
				System.out.println("Thank you for playing Java Battleship!");
				System.exit(0);
			}
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
		//Sets board with player ships
		for (int a = 0; a < ships.length; a++) {
			for (int i = 0; i < ships[a].getLength(); i++) {
				if (ships[a].isVertical()) {
					board.set(ships[a].getxCoord(), ships[a].getyCoord() + i, ships[a].getIdentifier());
				}
				else {
					board.set(ships[a].getxCoord() + i, ships[a].getyCoord(), ships[a].getIdentifier());
				}
			}	
		}

		//Initializes computer ships
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

			//Generates random coordinates
			int xCoord = rnd.nextInt(MIDLINE) + MIDLINE;
			int yCoord = rnd.nextInt(BOARD_HEIGHT - 2);
			while ((xCoord < 0) || (xCoord > BOARD_WIDTH - 1) || (xCoord < MIDLINE)) {
				xCoord = rnd.nextInt(MIDLINE) + MIDLINE;
			}
			while ((yCoord < 0) || (yCoord > BOARD_HEIGHT - 2)) {
				yCoord = rnd.nextInt(BOARD_HEIGHT - 2);
			}

			//Sets random coordinates
			computerShips[i].setxCoord(xCoord);
			computerShips[i].setyCoord(yCoord);
		}

		//Sets computer ships
		for (int a = 0; a < computerShips.length; a++) {
			for (int i = 0; i < computerShips[a].getLength(); i++) {
				if (computerShips[a].isVertical()) {
					board.set(computerShips[a].getxCoord(), computerShips[a].getyCoord() + i, computerShips[a].getIdentifier());
				}
				else {
					board.set(computerShips[a].getxCoord() + i, computerShips[a].getyCoord(), computerShips[a].getIdentifier());
				}
			}	
		}
	}

	//Outputs the current board 
	public static void printBoard() {
		Character[][] table = board.getTable();
		StringBuilder sb = new StringBuilder();

		//Adds column numbers
		sb.append("   ");
		for (int i = 1; i <= board.getxSize(); i++) {
			if (i < 10) {
				sb.append(i + "  ");
			}
			else {
				sb.append(i + " ");
			}
		}
		sb.append("\n");


		for (int i = 0; i < board.getySize(); i++) {
			//Adds row numbers
			if (i <= 8) {
				sb.append(i + 1 + "  ");
			}
			else {
				sb.append(i + 1 + " ");
			}

			//Adds characters
			for (int j = 0; j < board.getxSize(); j++) {
				//Masks computer ships
				if (j > MIDLINE) {
					if (table[i][j] == '~') {
						sb.append("~");
					}
					else if (table[i][j] == 'X') {
						sb.append('X');
					}
					else {
						sb.append("*");
					}

					if (j < table[0].length - 1) {
						sb.append("  ");
					}
					else {
						sb.append("\n");
					}
				}
				else {
					sb.append(table[i][j]);
					if (j < table[0].length - 1) {
						sb.append("  ");
					}
					else {
						sb.append("\n");
					}
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
			playerXGuess = (int) scnr.nextInt() - 1;
			while ((playerXGuess <= MIDLINE) || (playerXGuess < 1) || (playerXGuess > BOARD_WIDTH)) {
				System.out.println("Input valid X coordinate: ");
				playerXGuess = (int) scnr.nextInt() - 1;
			}
		} catch (InputMismatchException e) {
			System.out.println("Cannot accept that type of input!");
			e.printStackTrace();
		}
		catch (Exception e) {
			System.err.println("Unexpected error in X coordinate input!");
			e.printStackTrace();
		}


		//Gets Y coord guess
		System.out.println("Your Y coordinate guess?");
		try {
			playerYGuess = (int) scnr.nextInt() - 1;
			while ((playerYGuess < 1) || (playerYGuess > BOARD_HEIGHT)) {
				System.out.println("Input valid Y coordinate: ");
				playerYGuess = (int) scnr.nextInt() - 1;
			}
		} catch (InputMismatchException e) {
			System.out.println("Cannot accept that type of input!");
			e.printStackTrace();
		}
		catch (Exception e) {
			System.err.println("Unexpected error in Y coordinate input!");
			e.printStackTrace();
		}


		//Tests for a hit
		if ((board.get(playerXGuess, playerYGuess) != '*') && (board.get(playerXGuess, playerYGuess) != '~')) {
			System.out.println("Player 1 HIT!");
			board.set(playerXGuess, playerYGuess, 'X');
		}
		else {
			System.out.println("Player 1 Miss!");
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
			System.out.println("Computer HIT!");
			board.set(computerXGuess, computerYGuess, 'X');
		}
		else {
			System.out.println("Computer Miss!");
			board.set(computerXGuess, computerYGuess, '~');
		}
	}

	//Checks for a player win condition
	public static boolean checkForPlayerWin() {
		boolean playerWin = true;
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = MIDLINE; j < BOARD_WIDTH; j++) {
				for (int a = 0; a < computerShips.length; a++) {
					if (board.get(j, i) == computerShips[a].getIdentifier()) {
						playerWin = false;
					}
				}
			}
		}
		return playerWin;
	}

	//Checks for a computer win condition
	public static boolean checkForComputerWin() {
		boolean computerWin = true;
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < MIDLINE; j++) {
				for (int a = 0; a < ships.length; a++) {
					if (board.get(j, i) == ships[a].getIdentifier()) {
						computerWin = false;
					}
				}
			}
		}
		return computerWin;
	}

	//Checks if any ships have been sunk
	public static void checkSunkShips() {
		//TODO: Finish checkSunkShips
	}

	//Resets the game for a rematch
	public static void reset() {
		ships = new Ship[4];
		computerShips = new Ship[4];
		board = new Table<Character>(BOARD_WIDTH, BOARD_HEIGHT, Character.class);
		playerWin = false;
		computerWin = false;
	}
}