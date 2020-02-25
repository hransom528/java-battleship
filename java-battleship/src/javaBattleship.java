//Java Battleship v0.1
//By: Harris Ransom
import java.util.Scanner;

public class javaBattleship {
	//Declares variables
	static final int BOARD_WIDTH = 15;
	static final int BOARD_HEIGHT = 14;
	static final int MIDLINE = 7;
	static Table<Character> board = new Table<Character>(BOARD_WIDTH, BOARD_HEIGHT, Character.class);
	static Ship[] ships = new Ship[4];
	boolean win = false;

	//MAIN
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Welcome to Java Battleship!");
		System.out.println("By: Harris Ransom");
		System.out.println("" + BOARD_WIDTH + " x " + BOARD_HEIGHT + " board, Player 1 on the left.");
		boardSetup();
		try {
			input(scnr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		gameSetup();
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

	//Gets player input at start of game
	public static void input(Scanner scnr) throws Exception {
		int nextInput;
		String nextString;

		String[] names = {"Battleship", "Carrier", "Cruiser", "Frigate"};
		boolean[] vertical = new boolean[4];


		for (int i = 0; i < ships.length; i++) {
			System.out.println("Vertical " + names[i] + " (yes/no)?");
			nextString = Character.toString(scnr.next().charAt(0));
			if (nextString.equalsIgnoreCase("y")) {
				vertical[i] = true; 
			}
			else {
				vertical[i] = false; 
			}

			switch (i) {
			case 0:
				ships[i] = new Battleship(vertical[i]); 
				break;
			case 1:
				ships[i] = new Carrier(vertical[i]) ;
			case 2:
				ships[i] = new Cruiser(vertical[i]);
			case 3:
				ships[i] = new Frigate(vertical[i]);
			default:
				throw new Exception("Error in initializing ships!");
			}
			//TODO: Finish Battleship input

			System.out.println("" + names[i] + " X coordinate: ");
			nextInput = scnr.nextInt();
			while (nextInput > BOARD_WIDTH / 2) {
				System.out.println("Input valid X coordinate on your side: ");
				nextInput = scnr.nextInt();	
			}
			ships[i].setxCoord(nextInput);

			System.out.println("" + names[i] + " Y coordinate: ");
			nextInput = scnr.nextInt();
			while (nextInput > BOARD_HEIGHT / 2) {
				System.out.println("Input valid Y coordinate on your side: ");
				nextInput = scnr.nextInt();	
			}
			ships[i].setyCoord(nextInput);
		}
	}

	//Sets up game for playing
	public static void gameSetup() {
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].getLength(); j++) {
				if (ships[i].isVertical()) {
					board.set(ships[i].getxCoord(), ships[i].getyCoord() + j, ships[i].getIdentifier());
				}
				else {
					board.set(ships[i].getxCoord() + j, ships[i].getyCoord(), ships[i].getIdentifier());
				}
			}	
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