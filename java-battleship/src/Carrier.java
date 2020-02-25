/**
 * @author Harris Ransom
 */
public class Carrier extends Ship {
	public Carrier(boolean vertical) {
		super(4, '^', vertical);
		this.setName("Carrier");
	}
}