package Project3;

import java.util.LinkedList;
import java.util.Queue;

//BinarySearchTree class
//
//CONSTRUCTION: with no initializer
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//void remove( x )       --> Remove x
//boolean contains( x )  --> Return true if x is present
//Comparable findMin( )  --> Return smallest item
//Comparable findMax( )  --> Return largest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//void printTree( )      --> Print tree in sorted order
//******************ERRORS********************************
//Throws Exception as appropriate

/**
 * Implements an unbalanced binary search tree. Note that all "matching" is
 * based on the compareTo method.
 * 
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
	/**
	 * Construct the tree.
	 */
	public BinarySearchTree() {
		root = null;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * 
	 * @param x
	 *            the item to insert.
	 */
	public void insert(AnyType x) {
		root = insert(x, root);
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * 
	 * @param x
	 *            the item to remove.
	 */
	public void remove(AnyType x) {
		root = remove(x, root);
	}

	/**
	 * Find the smallest item in the tree.
	 * 
	 * @return smallest item or null if empty.
	 * @throws Exception
	 */
	public AnyType findMin() throws Exception {
		if (isEmpty())
			throw new Exception();
		return findMin(root).element;
	}

	/**
	 * Find the largest item in the tree.
	 * 
	 * @return the largest item of null if empty.
	 * @throws Exception
	 */
	public AnyType findMax() throws Exception {
		if (isEmpty())
			throw new Exception();
		return findMax(root).element;
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x
	 *            the item to search for.
	 * @return true if not found.
	 */
	public boolean contains(AnyType x) {
		return contains(x, root);
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			printTree(root);
		System.out.println();
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param x
	 *            the item to insert.
	 * @param t
	 *            the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return new BinaryNode<>(x, null, null);

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = insert(x, t.left);
		else if (compareResult > 0)
			t.right = insert(x, t.right);
		else
			; // Duplicate; do nothing
		return t;
	}

	/**
	 * Internal method to remove from a subtree.
	 * 
	 * @param x
	 *            the item to remove.
	 * @param t
	 *            the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return t; // Item not found; do nothing

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = remove(x, t.left);
		else if (compareResult > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) // Two children
		{
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else
			t = (t.left != null) ? t.left : t.right;
		return t;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
		if (t == null)
			return null;
		else if (t.left == null)
			return t;
		return findMin(t.left);
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the largest item.
	 */
	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
		if (t != null)
			while (t.right != null)
				t = t.right;

		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * 
	 * @param x
	 *            is item to search for.
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the matched item.
	 */
	private boolean contains(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return false;

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			return contains(x, t.left);
		else if (compareResult > 0)
			return contains(x, t.right);
		else
			return true; // Match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 */
	private void printTree(BinaryNode<AnyType> t) {
		if (t != null) {
			printTree(t.left);
			System.out.print(t.element+" ");
			printTree(t.right);
		}
	}

	/**
	 * Internal method to compute height of a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 */
	private int height(BinaryNode<AnyType> t) {
		if (t == null)
			return -1;
		else
			return 1 + Math.max(height(t.left), height(t.right));
	}

	// Basic node stored in unbalanced binary search trees
	private static class BinaryNode<AnyType> {
		// Constructors
		BinaryNode(AnyType theElement) {
			this(theElement, null, null);
		}

		BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
			element = theElement;
			left = lt;
			right = rt;
		}

		AnyType element; // The data in the node
		BinaryNode<AnyType> left; // Left child
		BinaryNode<AnyType> right; // Right child
	}

	//////////////////////////// NODE COUNT ////////////////////////////////////
	public int nodeCount(BinaryNode<AnyType> root) {
		if (root == null)
			return 0;
		return 1 + nodeCount(root.left) + nodeCount(root.right);
	}

	//////////////////////// isFULL /////////////////////////////////////
	public boolean isFull(BinaryNode<AnyType> root) {
		if (root == null)
			return true;
		if (root.left == null && root.right == null)
			return true;
		if (root.left != null && root.right != null)
			return isFull(root.left) && isFull(root.right);
		return false;
	}

	//////////////////// COMPARE STRUCTURE ////////////////////////////////
	public boolean compareStructure(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2) {
		if (t1 == null && t2 == null)
			return true;
		if (t1 != null && t2 != null)
			return compareStructure(t1.left, t2.left) && compareStructure(t1.right, t2.right);
		return false;
	}

	////////////////////////// EQUALS //////////////////////////////////////////
	public boolean equals(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2) {
		if (t1 == null && t2 == null)
			return true;
		if (t1 != null && t2 != null)
			return t1.element.equals(t2.element) && equals(t1.left, t2.left) && equals(t1.right, t2.right);
		return false;
	}

	//////////////////// IS MIRROR /////////////////////////////////////////////
	public boolean isMirror(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2) {
		if (t1 == null && t2 == null)
			return true;
		if (t1 == null || t2 == null)
			return false;
		return t1.element.equals(t2.element) && isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
	}

	//////////////////////// MIRROR TREE ////////////////////////////////
	public BinarySearchTree<AnyType> mirror() {
		if (root == null)
			return null;
		BinarySearchTree<AnyType> mirrTree = new BinarySearchTree<>();
		mirrTree.root = BSTMirror(root, new BinaryNode<>(null));
		return mirrTree;
	}

	private BinaryNode<AnyType> BSTMirror(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2) {
		t2.element = t1.element;
		if (t1.left != null)
			t2.right = BSTMirror(t1.left, new BinaryNode<>(null));
		if (t1.right != null)
			t2.left = BSTMirror(t1.right, new BinaryNode<>(null));
		return t2;
	}

	/////////////////////////// COPY TREE /////////////////////////////////////
	public BinarySearchTree<AnyType> copy() {
		BinarySearchTree<AnyType> n = new BinarySearchTree<>();
		return traverse(root, n);
	}

	private BinarySearchTree<AnyType> traverse(BinaryNode<AnyType> root, BinarySearchTree<AnyType> n) {
		if (root == null)
			return null;
		n.insert(root.element);
		traverse(root.left, n);
		traverse(root.right, n);
		return n;
	}

	////////////////////// ROTATE LEFT /////////////////////////////////////
	public void rotateLeft(AnyType val) {
		BinaryNode<AnyType> bn = findNode(val); // Node to rotate
		BinaryNode<AnyType> bnp = findParent(val); // Parent node
		if (bn == null)
			return;
		if (bn.right == null) // Left rotate requires right child not null
			return;
		BinaryNode<AnyType> nr = bn.right;
		bn.right = nr.left;
		if (bnp == null) {
			root = nr;
		} else if (bnp.left == bn) {
			bnp.left = nr;
		} else {
			bnp.right = nr;
		}
		nr.left = bn;
	}

	////////////////// ROTATE RIGHT //////////////////////////////////
	public void rotateRight(AnyType val) {
		BinaryNode<AnyType> bn = findNode(val); // Node to rotate
		BinaryNode<AnyType> bnp = findParent(val); // Parent node
		if (bn == null)
			return;
		if (bn.left == null) // Right rotate requires left child not null
			return;
		BinaryNode<AnyType> nl = bn.left;
		bn.left = nl.right;
		if (bnp == null) {
			root = nl;
		} else if (bnp.left == bn) {
			bnp.left = nl;
		} else {
			bnp.right = nl;
		}
		nl.right = bn;
	}

	private BinaryNode<AnyType> findParent(AnyType val) {
		BinaryNode<AnyType> p = null;
		BinaryNode<AnyType> n = root;
		while (!n.element.equals(val)) {
			if (val.compareTo(n.element) > 1) { // Right sub tree
				if (n.right == null)
					return null;
				p = n;
				n = n.right;
			} else { // Left sub tree
				if (n.left == null)
					return null;
				p = n;
				n = n.left;
			}
		}
		return p;
	}

	private BinaryNode<AnyType> findNode(AnyType val) {
		BinaryNode<AnyType> n = root;
		if (n == null)
			return null;
		while (!n.element.equals(val)) {
			if (val.compareTo(n.element) > 1) { // Right sub tree
				if (n.right == null)
					return null;
				n = n.right;
			} else { // Left sub tree
				if (n.left == null)
					return null;
				n = n.left;
			}
		}
		return n;
	}
	
	///////////////////// PRINT LEVELS ////////////////////////////////
	public void printLevels() {
		Queue<BinaryNode<AnyType>> q = new LinkedList<>();
		q.add(root);
		while (!q.isEmpty()) {
			BinaryNode<AnyType> n = q.poll();
			System.out.print(n.element + " ");
			if (n.left != null)
				q.add(n.left);
			if (n.right != null)
				q.add(n.right);
		}
		System.out.println();
	}

	/** The tree root. */
	private BinaryNode<AnyType> root;

	// Test program
	public static void main(String[] args) throws Exception {

		System.out.println("------ All below prints are inorder traversal ---------- ");

		BinarySearchTree<Integer> tree1 = new BinarySearchTree<>();
		tree1.insert(8);
		tree1.insert(3);
		tree1.insert(10);
		tree1.insert(14);
		tree1.insert(9);
		System.out.print("Tree 1                     :  ");
		tree1.printTree();

		BinarySearchTree<Integer> tree2 = new BinarySearchTree<>();
		tree2.insert(8);
		tree2.insert(3);
		tree2.insert(10);
		tree2.insert(14);

		System.out.print("Tree 2                     :  ");
		tree2.printTree();

		System.out.println("Count of nodes(tree1)      :  " + tree1.nodeCount(tree1.root));
		System.out.println("IsFull(tree1)              :  " + tree1.isFull(tree1.root));

		System.out.println("CompareStruct(tree1, tree2):  " + tree2.compareStructure(tree2.root, tree1.root));
		System.out.println("Equals(tree1,tree2)        :  " + tree2.equals(tree2.root, tree1.root));

		System.out.print("Copy tree1 to cpy1         :  ");
		BinarySearchTree<Integer> cpy1 = tree1.copy();
		cpy1.printTree();

		System.out.print("Mirror(tree1 --> mirr)     :  ");
		BinarySearchTree<Integer> mirr = new BinarySearchTree<>();
		mirr = tree1.mirror();
		mirr.printTree();

		System.out.println("Is Mirror (tree1, mirr)    :  " + mirr.isMirror(mirr.root, tree1.root));
		
		System.out.print("Rotate Left  (tree1) (8)   :  ");
		tree1.rotateLeft(8);
		tree1.printTree();

		System.out.print("Rotate Right (tree1) (3)   :  ");
		tree1.rotateRight(3);
		tree1.printTree();
		
		System.out.print("Print Level wise (tree1)   :  ");
		tree1.printLevels();
	}
}