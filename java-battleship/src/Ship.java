/**
 * @author Harris Ransom
 */
public class Ship {
	private int length;
	private char identifier;
	private boolean vertical;
	private String name;
	private int xCoord;
	private int yCoord;

	/**Default constructor
	 */
	public Ship() {
		this.setLength(0);
		this.setIdentifier('n');
		this.setVertical(true);
	}

	/**Parameterized constructor
	 * @param length
	 * @param indentifier
	 * @param vertical
	 */
	public Ship(int length, char indentifier, boolean vertical) {
		this.setLength(length);
		this.setIdentifier(indentifier);
		this.setVertical(vertical);
	}

	/**Gets name
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**Sets name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**Gets xCoord
	 * @return the xCoord
	 */
	public int getxCoord() {
		return xCoord;
	}

	/**Sets xCoord
	 * @param xCoord the xCoord to set
	 */
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	/**Gets yCoord
	 * @return the yCoord
	 */
	public int getyCoord() {
		return yCoord;
	}

	/**Sets yCoord
	 * @param yCoord the yCoord to set
	 */
	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	/**Returns vertical
	 * @return Vertical boolean value
	 */
	public boolean isVertical() {
		return vertical;
	}

	/**Sets vertical
	 * @param vertical
	 */
	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	/**Gets identifier
	 * @return Character identifier for the ship
	 */
	public char getIdentifier() {
		return identifier;
	}

	/**Sets identifier
	 * @param identifier
	 */
	public void setIdentifier(char identifier) {
		this.identifier = identifier;
	}

	/**Gets length
	 * @return Integer length
	 */
	public int getLength() {
		return length;
	}

	/**Sets length
	 * @param length
	 */
	private void setLength(int length) {
		this.length = length;
	}
}