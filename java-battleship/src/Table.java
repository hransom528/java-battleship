import java.util.Collection;
import java.util.List;
import java.lang.reflect.Array;

/**The Table class is a 2D array of type T that has a fixed size
 * @author Harris Ransom
 * @param <T>
 * @version 1.0
 */
public class Table<T> implements Cloneable  {
	private int xSize;
	private int ySize;
	private final Class<? extends T> cls;
	private T[][] table;

	/**Table constructor
	 * @param xSize 
	 * @param ySize
	 * @param T type (in class form)
	 */
	@SuppressWarnings("unchecked")
	public Table(int xSize, int ySize, Class<? extends T> cls) {
		super();
		this.cls = cls;
		this.xSize = xSize;
		this.ySize = ySize;
		table = (T[][]) Array.newInstance(cls, this.ySize, this.xSize);
	}

	/**Gets X size
	 * @return X dimension
	 */
	public int getxSize() {
		return xSize;
	}

	/**Gets Y size
	 * @return Y dimension
	 */
	public int getySize() {
		return ySize;
	}

	/**Gets total size of table
	 * @return Integer ammount of elements in table
	 */
	public int size() {
		int returnSize = 0;
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				returnSize++;
			}
		}
		return returnSize;
	}

	/**Gets class
	 * @return Class type 
	 */
	public Class<? extends T> getCls() {
		return cls;
	}

	/**Gets table as 2D array
	 * @return the table
	 */
	public T[][] getTable() {
		return table;
	}

	/**Determines if table is empty
	 * @return Boolean if table contains only null
	 */
	public boolean isEmpty() {
		boolean isEmpty = true;
		for (T[] row : table) {
			for (T item : row) {
				if (item != null) {
					isEmpty = false;
				}
			}
		}
		return isEmpty;
	}

	/**
	 * @param o Object to search for
	 * @return Boolean if table contains object o
	 */
	public boolean contains(Object o) {
		boolean contains = false;
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				if (table[j][i].equals(o)) {
					contains = true;
				}
			}
		}
		return contains;
	}
	
	/**
	 * @param c
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean containsAll(Collection c) {
		boolean containsAll = true;
		boolean[] bools = new boolean[c.size()];
		Object[] objs = new Object[c.size()];
		objs = c.toArray(objs);
		
		for (int a = 0; a < objs.length; a++) {
			for (int i = 0; i < this.table.length; i++) {
				for (int j = 0; j < this.table[0].length; j++) {
					if (this.table[i][j].equals(objs[a])) {
						bools[a] = true; 
					}
				}
			}
		}
		
		for (int i = 0; i < bools.length; i++) {
			if (!bools[i]) {
				containsAll = false;
			}
		}
		return containsAll;
	}

	/**
	 * @param xCoord
	 * @param yCoord
	 * @param o
	 */
	@SuppressWarnings("unchecked")
	public void set(int xCoord, int yCoord, Object o) {
		this.table[yCoord][xCoord] = (T) o;
	}

	/**
	 * @param xCoord
	 * @param yCoord
	 * @return
	 */
	public T get(int xCoord, int yCoord) {
		return this.table[yCoord][xCoord];
	}
	
	/**Clears array by setting all elements to null
	 */
	public void clear() {
		if (!this.isEmpty()) {
			for (int i = 0; i < this.table.length; i++) {
				for (int j = 0; j < this.table[0].length; j++) {
					table[i][j] = null;
				}
			}
			this.clear();
		}
	}

	/**Returns array of elements in table
	 * @return Object array containing all elements in table
	 */
	public Object[] toArray() {
		Object[] arr = new Object[this.size()];
		int index = 0;
		for (T[] row : table) {
			for (T item : row) {
				arr[index] = item;
				index++;
			}
		}
		return arr;
	}

	/**
	 * @param a
	 * @return Array a filled with elements in table (if large enough)
	 */
	@SuppressWarnings("unchecked")
	public Object[] toArray(Object[] a) {
		int index = 0;
		if (a.length < this.size()) {
			T[] tArr = (T[]) new Object[this.size()];
			for (int i = 0; i < this.table.length; i++) {
				for (int j = 0; j < this.table[0].length; j++) {
					tArr[index] = (T) this.table[j][i];
					index++;
				}
			}
			return tArr;
		} else {
			for (int i = 0; i < this.table.length; i++) {
				for (int j = 0; j < this.table[0].length; j++) {
					a[index] = (T) this.table[j][i];
					index++;
				}
			}
			return a;
		}
	}

	/**
	 * @return
	 * @see java.lang.Class#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder().append("[");
		if (this.isEmpty()) {
			return sb.append("]").toString();
		}
		else {
			for (int i = 0; i < table.length; i++) {
				for (int j = 0; j < table[0].length; j++) {
					sb.append(table[i][j].toString());
					if (j < this.getxSize() - 1) {
						sb.append(", ");
					}
				}
				if (i < this.getySize() - 1) {
					sb.append("\n ");
				}
			}
			return sb.append("]").toString();
		}
	}

	@Override
	public Table<T> clone() {
		Table<T> clone = new Table<T>(this.getxSize(), this.getySize(), this.getCls());
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				clone.set(j, i, this.table[i][j]);
			}
		}
		return clone;
	}
	
	/**Converts list to table
	 * @param xDimension
	 * @param yDimension
	 * @param list
	 * @return new Table object from list
	 */
	public Table<T> toTable(int xDimension, int yDimension, List<T> list) {
		Table<T> returnTable = new Table<T>(xDimension, yDimension, this.cls);
		for (int i = 0; i < returnTable.getySize(); i++) {
			for (int j = 0; j < returnTable.getxSize(); j++) {
				for (int a = 0; a < list.size(); a++) {
					returnTable.set(j, i, list.get(a));
				}
			}
		}
		return returnTable;
	}
}