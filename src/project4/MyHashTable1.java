package project4;

public class MyHashTable1<AnyType> {
	private static final int DEFAULT_TABLE_SIZE = 1000;
	private HashEntry<AnyType>[] list;
	private int currSize = 0;

	public MyHashTable1() {
		this(DEFAULT_TABLE_SIZE);
	}

	public MyHashTable1(int tableSize) {
		list = new HashEntry[tableSize];
	}

	public void insert(AnyType x) {
    	int hash = myHash(x);
    	if (list[hash] == null) {
    		list[hash] = new HashEntry<>(x);
    	}
    	else {
    		while(list[++hash] != null) {
    			if(hash == list.length-1)
    				hash = -1;
    		}
    		list[hash] = new HashEntry<>(x);
    	}
    	System.out.println(x + " stored at : "+hash);
    	if(++currSize > list.length/2) {
    		currSize = 0;
    		rehash();
    	}
    }

	private void rehash() {
		HashEntry<AnyType>[] oldList = list;
		list = new HashEntry[2 * oldList.length];
		//System.out.println("rehashed "+list.length);
		for (HashEntry<AnyType> val : oldList) {
			if(val != null) {
				//System.out.println("Val "+val.element);
				insert(val.element);
			}
		}
	}

	private int myHash(AnyType x) {
		int hval = x.hashCode();
		// For string hash value
		String v = x.toString();
		for (int i = 0; i < v.length(); i++) {
			hval += (v.charAt(i) + 61);
		}
		//System.out.println(hval);
		hval = (hval % (list.length-1));
		return Math.abs(hval);
	}
	
	public boolean contains(AnyType val) {
		int hash = myHash(val);
		System.out.println(val + "    " + hash);
		if (list[hash]!=null && list[hash].element.equals(val))
			return true;
		else
			return false;
	}

	private static class HashEntry<AnyType> {
		public AnyType element; // the element

		public HashEntry(AnyType e) {
			element = e;
		}
	}

	public static void main(String[] args) {
		MyHashTable1<String> my = new MyHashTable1<>(2);
		my.insert("abcd");
		my.insert("ab");
		my.contains("abcde");
	}
}