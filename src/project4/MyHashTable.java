package project4;

// MyHashTable implementation
public class MyHashTable<AnyType> {
	private static final int DEFAULT_SIZE = 5000;

	private HashEntry<AnyType>[] list;
	private int occupied;
	private int currentSize;

	public MyHashTable() {
		this(DEFAULT_SIZE);
	}

	public MyHashTable(int tableSize) {
		list = new HashEntry[tableSize];
		occupied = 0;
		for (int i = 0; i < list.length; i++)
			list[i] = null;
	}

	public boolean insert(AnyType x) {
		int position = getLocation(x);
		if (isUsed(position))
			return false;

		if (list[position] == null)
			++occupied;
		list[position] = new HashEntry<>(x, true);
		currentSize++;

		if (occupied > list.length / 2)
			rehash();
		return true;
	}
	
	private void rehash() {
		HashEntry<AnyType>[] oldList = list;

		list = new HashEntry[oldList.length * 2];
		occupied = 0;
		currentSize = 0;

		for (HashEntry<AnyType> entry : oldList)
			if (entry != null && entry.isActive)
				insert(entry.element);
	}

	private int getLocation(AnyType x) {
		int offset = 1;
		int position = myhash(x);

		while (list[position] != null 
				&& !list[position].element.equals(x)) {
			position += offset;
			offset += 2;
			if (position >= list.length)
				position -= list.length;
		}

		return position;
	}

	public boolean contains(AnyType x) {
		int position = getLocation(x);
		return isUsed(position);
	}

	private boolean isUsed(int currentPos) {
		return list[currentPos] != null 
				&& list[currentPos].isActive;
	}

	private int myhash(AnyType x) {
		int hashVal = x.hashCode();
		String v = x.toString();
		for (int i = 0; i < v.length(); i++) {
			hashVal += (v.charAt(i) + 61);
		}
		hashVal = hashVal % list.length;
		return Math.abs(hashVal);
	}

	private static class HashEntry<AnyType> {
		public AnyType element;
		public boolean isActive;

		public HashEntry(AnyType e) {
			this(e, true);
		}

		public HashEntry(AnyType e, boolean i) {
			element = e;
			isActive = i;
		}
	}
}