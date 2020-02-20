
/**
 * @author Harris Ransom
 */
public class Ship {
	public int length;
	public char identifier;
	public boolean vertical;
	public String name;
	public int xCoord;
	public int yCoord;
	
	/**Default constructor
	 */
	public Ship() {
		this.length = 0;
		this.identifier = 'n';
		this.vertical = true;
	}
	
	/**Parameterized constructor
	 * @param length
	 * @param indentifier
	 * @param vertical
	 */
	public Ship(int length, char indentifier, boolean vertical) {
		this.length = length;
		this.identifier = indentifier;
		this.vertical = vertical;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the xCoord
	 */
	public int getxCoord() {
		return xCoord;
	}

	/**
	 * @param xCoord the xCoord to set
	 */
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * @return the yCoord
	 */
	public int getyCoord() {
		return yCoord;
	}

	/**
	 * @param yCoord the yCoord to set
	 */
	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}
}