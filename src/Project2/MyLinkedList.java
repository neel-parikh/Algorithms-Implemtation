package Project2;

/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType> {
	/**
	 * Construct an empty LinkedList.
	 */
	public MyLinkedList() {
		doClear();
	}

	private void clear() {
		doClear();
	}

	/**
	 * Change the size of this collection to zero.
	 */
	public void doClear() {
		beginMarker = new Node<>(null, null, null);
		endMarker = new Node<>(null, beginMarker, null);
		beginMarker.next = endMarker;

		theSize = 0;
		modCount++;
	}

	/**
	 * Returns the number of items in this collection.
	 * 
	 * @return the number of items in this collection.
	 */
	public int size() {
		return theSize;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Adds an item to this collection, at the end.
	 * 
	 * @param x
	 *            any object.
	 * @return true.
	 */
	public boolean add(AnyType x) {
		add(size(), x);
		return true;
	}

	/**
	 * Adds an item to this collection, at specified position. Items at or after
	 * that position are slid one position higher.
	 * 
	 * @param x
	 *            any object.
	 * @param idx
	 *            position to add at.
	 * @throws IndexOutOfBoundsException
	 *             if idx is not between 0 and size(), inclusive.
	 */
	public void add(int idx, AnyType x) {
		addBefore(getNode(idx, 0, size()), x);
	}

	/**
	 * Adds an item to this collection, at specified position p. Items at or
	 * after that position are slid one position higher.
	 * 
	 * @param p
	 *            Node to add before.
	 * @param x
	 *            any object.
	 * @throws IndexOutOfBoundsException
	 *             if idx is not between 0 and size(), inclusive.
	 */
	private void addBefore(Node<AnyType> p, AnyType x) {
		Node<AnyType> newNode = new Node<>(x, p.prev, p);
		newNode.prev.next = newNode;
		p.prev = newNode;
		theSize++;
		modCount++;
	}

	/**
	 * Returns the item at position idx.
	 * 
	 * @param idx
	 *            the index to search in.
	 * @throws IndexOutOfBoundsException
	 *             if index is out of range.
	 */
	public AnyType get(int idx) {
		return getNode(idx).data;
	}

	/**
	 * Changes the item at position idx.
	 * 
	 * @param idx
	 *            the index to change.
	 * @param newVal
	 *            the new value.
	 * @return the old value.
	 * @throws IndexOutOfBoundsException
	 *             if index is out of range.
	 */
	public AnyType set(int idx, AnyType newVal) {
		Node<AnyType> p = getNode(idx);
		AnyType oldVal = p.data;

		p.data = newVal;
		return oldVal;
	}

	/**
	 * Gets the Node at position idx, which must range from 0 to size( ) - 1.
	 * 
	 * @param idx
	 *            index to search at.
	 * @return internal node corresponding to idx.
	 * @throws IndexOutOfBoundsException
	 *             if idx is not between 0 and size( ) - 1, inclusive.
	 */
	private Node<AnyType> getNode(int idx) {
		return getNode(idx, 0, size() - 1);
	}

	/**
	 * Gets the Node at position idx, which must range from lower to upper.
	 * 
	 * @param idx
	 *            index to search at.
	 * @param lower
	 *            lowest valid index.
	 * @param upper
	 *            highest valid index.
	 * @return internal node corresponding to idx.
	 * @throws IndexOutOfBoundsException
	 *             if idx is not between lower and upper, inclusive.
	 */
	private Node<AnyType> getNode(int idx, int lower, int upper) {
		Node<AnyType> p;

		if (idx < lower || idx > upper)
			throw new IndexOutOfBoundsException("getNode index: " + idx + "; size: " + size());

		if (idx < size() / 2) {
			p = beginMarker.next;
			for (int i = 0; i < idx; i++)
				p = p.next;
		} else {
			p = endMarker;
			for (int i = size(); i > idx; i--)
				p = p.prev;
		}
		return p;
	}

	/**
	 * Removes an item from this collection.
	 * 
	 * @param idx
	 *            the index of the object.
	 * @return the item was removed from the collection.
	 */
	public AnyType remove(int idx) {
		return remove(getNode(idx));
	}

	/**
	 * Removes the object contained in Node p.
	 * 
	 * @param p
	 *            the Node containing the object.
	 * @return the item was removed from the collection.
	 */
	private AnyType remove(Node<AnyType> p) {
		p.next.prev = p.prev;
		p.prev.next = p.next;
		theSize--;
		modCount++;

		return p.data;
	}

	/**
	 * Returns a String representation of this collection.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("[ ");

		for (AnyType x : this)
			sb.append(x + " ");
		sb.append("]");

		return new String(sb);
	}

	/**
	 * Obtains an Iterator object used to traverse the collection.
	 * 
	 * @return an iterator positioned prior to the first element.
	 */
	public java.util.Iterator<AnyType> iterator() {
		return new LinkedListIterator();
	}

	/**
	 * This is the implementation of the LinkedListIterator. It maintains a
	 * notion of a current position and of course the implicit reference to the
	 * MyLinkedList.
	 */
	private class LinkedListIterator implements java.util.Iterator<AnyType> {
		private Node<AnyType> current = beginMarker.next;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;

		public boolean hasNext() {
			return current != endMarker;
		}

		public AnyType next() {
			if (modCount != expectedModCount)
				throw new java.util.ConcurrentModificationException();
			if (!hasNext())
				throw new java.util.NoSuchElementException();

			AnyType nextItem = current.data;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}

		public void remove() {
			if (modCount != expectedModCount)
				throw new java.util.ConcurrentModificationException();
			if (!okToRemove)
				throw new IllegalStateException();

			MyLinkedList.this.remove(current.prev);
			expectedModCount++;
			okToRemove = false;
		}
	}

	/**
	 * This is the doubly-linked list node.
	 */
	private static class Node<AnyType> {
		public Node(AnyType d, Node<AnyType> p, Node<AnyType> n) {
			data = d;
			prev = p;
			next = n;
		}

		public AnyType data;
		public Node<AnyType> prev;
		public Node<AnyType> next;
	}

	/* Swap two nodes given index number for both the nodes */
	public void swap(int idx1, int idx2) {

		// idx1 and idx2 should be in current size limit
		if (!((idx1 >= 0 && idx1 < size()) && (idx2 >= 0 && idx2 < size()))) {
			System.out.println("Invalid Index specified");
			return;
		}
		// If two index are same, nothing to swap
		if (idx1 == idx2)
			return;

		Node<AnyType> n1 = getNode(idx1);
		Node<AnyType> n2 = getNode(idx2);

		// Swap two nodes away from each other
		if (Math.abs(idx1 - idx2) != 1) {
			Node<AnyType> tmp = new Node<AnyType>(n2.data, n2.prev, n2.next);
			n2.next = n1.next;
			n2.next.prev = n2;
			n2.prev = n1.prev;
			n2.prev.next = n2;

			n1.next = tmp.next;
			n1.next.prev = n1;
			n1.prev = tmp.prev;
			n1.prev.next = n1;
		}
		// Swap 2 nodes side by side
		else {
			n2.next.prev = n1;
			n1.next = n2.next;
			n1.prev.next = n2;
			n2.prev = n1.prev;
			n2.next = n1;
			n1.prev = n2;
		}
	}

	/* Shift nodes forward or backward */
	public void shift(int pos) {
		// Backward Shift
		if (pos < 0) {
			for (int i = 0; i < Math.abs(pos); i++) {
				Node<AnyType> curr = beginMarker.next;
				beginMarker.next = endMarker.prev;
				endMarker.prev.prev.next = endMarker;
				endMarker.prev = endMarker.prev.prev;
				beginMarker.next.prev = beginMarker;
				curr.prev = beginMarker.next;
				beginMarker.next.next = curr;
			}
		}
		// Forward Shift
		else if (pos > 0) {
			for (int i = 0; i < Math.abs(pos); i++) {
				Node<AnyType> curr = beginMarker.next;
				beginMarker.next = curr.next;
				beginMarker.next.prev = beginMarker;
				endMarker.prev.next = curr;
				curr.prev = endMarker.prev;
				curr.next = endMarker;
				endMarker.prev = curr;
			}
		}
	}

	/* Erase specified number of elements from idx position */
	public void erase(int idx, int n) {

		// idx and 'n' should be not more than size of list
		if (!(idx >= 0 && idx < size() && n <= size())) {
			System.out.println("Index out of bound");
			return;
		}
		// Erase entire list
		if (idx == 0 && n == size()) {
			doClear();
			return;
		}

		if (idx >= 0 && n > (size() - idx)) {
			System.out.println("Not many number of elements present after index " + idx);
			return;
		}

		while (n-- > 0) {
			remove(idx);
		}
	}

	public void insertList(MyLinkedList<AnyType> addLst, int idx) {

		if (!(idx >= 0 && idx < size())) {
			System.out.println("Index out of bound");
			return;
		}
		Node<AnyType> prevOldLst = getNode(idx);

		Node<AnyType> newFirst = addLst.getNode(0);
		Node<AnyType> newLast = addLst.getNode(addLst.size() - 1);
		if (idx == 0) {
			beginMarker.next = newFirst;
			beginMarker.next.prev = beginMarker;
		} else {
			prevOldLst.prev.next = newFirst;
			newFirst.prev = prevOldLst.prev;
		}
		prevOldLst.prev = newLast;
		prevOldLst.prev.next = prevOldLst;
		theSize += addLst.size();
	}

	private int theSize;
	private int modCount = 0;
	private Node<AnyType> beginMarker;
	private Node<AnyType> endMarker;

	public static void main(String[] args) {
		MyLinkedList<Integer> lst = new MyLinkedList<>();

		for (int i = 3; i < 9; i++)
			lst.add(i);

		System.out.println("Original List: " + lst);

		// Test Swap index 2 and 3
		lst.swap(2, 3);
		System.out.println("Swap index 2 and 3: " + lst);

		// Test Forward Shift 2 places
		lst.shift(2);
		System.out.println("Forward Shift 2 places: " + lst);

		// Test Backward Shift 2 place
		lst.shift(-2);
		System.out.println("Backward Shift 2 place: " + lst);

		// Test Erase 3 elements starting index 1
		lst.erase(1, 3);
		System.out.println("Erase 3 elements starting index 1: " + lst);

		MyLinkedList<Integer> addLst = new MyLinkedList<>();
		addLst.add(1);
		addLst.add(2);

		// Test copying list with two elements [1 2]
		lst.insertList(addLst, 2);
		System.out.println("Insert New list [1,2] at index 2: " + lst);
	}
}