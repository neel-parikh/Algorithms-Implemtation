package project4;

/**
* Implements an AVL tree.
* Note that all "matching" is based on the compareTo method.
* @author Mark Allen Weiss
*/
public class AvlTree<AnyType extends Comparable<? super AnyType>>
{
 /**
  * Construct the tree.
  */
 public AvlTree( )
 {
     root = null;
 }

 /**
  * Insert into the tree; duplicates are ignored.
  * @param x the item to insert.
  */
 public void insert( AnyType x )
 {
     root = insert( x, root );
 }
 public boolean contains( AnyType x )
 {
     return contains( x, root );
 }

 /**
  * Make the tree logically empty.
  */
 public void makeEmpty( )
 {
     root = null;
 }

 /**
  * Test if the tree is logically empty.
  * @return true if empty, false otherwise.
  */
 public boolean isEmpty( )
 {
     return root == null;
 }

 /**
  * Print the tree contents in sorted order.
  */
 public void printTree( )
 {
     if( isEmpty( ) )
         System.out.println( "Empty tree" );
     else
         printTree( root );
 }

 private static final int ALLOWED_IMBALANCE = 1;
 
 // Assume t is either balanced or within one of being balanced
 private AvlNode<AnyType> balance( AvlNode<AnyType> t )
 {
     if( t == null )
         return t;
     
     if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
         if( height( t.left.left ) >= height( t.left.right ) )
             t = rotateWithLeftChild( t );
         else
             t = doubleWithLeftChild( t );
     else
     if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
         if( height( t.right.right ) >= height( t.right.left ) )
             t = rotateWithRightChild( t );
         else
             t = doubleWithRightChild( t );

     t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
     return t;
 } 
 
 /**
  * Internal method to insert into a subtree.
  * @param x the item to insert.
  * @param t the node that roots the subtree.
  * @return the new root of the subtree.
  */
 private AvlNode<AnyType> insert( AnyType x, AvlNode<AnyType> t )
 {
     if( t == null )
         return new AvlNode<>( x, null, null );
     
     int compareResult = x.compareTo( t.element );
     
     if( compareResult < 0 )
         t.left = insert( x, t.left );
     else if( compareResult > 0 )
         t.right = insert( x, t.right );
     else
         ;  // Duplicate; do nothing
     return balance( t );
 }
 private boolean contains( AnyType x, AvlNode<AnyType> t )
 {
     while( t != null )
     {
         int compareResult = x.compareTo( t.element );
         
         if( compareResult < 0 )
             t = t.left;
         else if( compareResult > 0 )
             t = t.right;
         else
             return true;    // Match
     }

     return false;   // No match
 }

 /**
  * Internal method to print a subtree in sorted order.
  * @param t the node that roots the tree.
  */
 private void printTree( AvlNode<AnyType> t )
 {
     if( t != null )
     {
         printTree( t.left );
         System.out.println( t.element );
         printTree( t.right );
     }
 }

 /**
  * Return the height of node t, or -1, if null.
  */
 private int height( AvlNode<AnyType> t )
 {
     return t == null ? -1 : t.height;
 }

 /**
  * Rotate binary tree node with left child.
  * For AVL trees, this is a single rotation for case 1.
  * Update heights, then return new root.
  */
 private AvlNode<AnyType> rotateWithLeftChild( AvlNode<AnyType> k2 )
 {
     AvlNode<AnyType> k1 = k2.left;
     k2.left = k1.right;
     k1.right = k2;
     k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
     k1.height = Math.max( height( k1.left ), k2.height ) + 1;
     return k1;
 }

 /**
  * Rotate binary tree node with right child.
  * For AVL trees, this is a single rotation for case 4.
  * Update heights, then return new root.
  */
 private AvlNode<AnyType> rotateWithRightChild( AvlNode<AnyType> k1 )
 {
     AvlNode<AnyType> k2 = k1.right;
     k1.right = k2.left;
     k2.left = k1;
     k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
     k2.height = Math.max( height( k2.right ), k1.height ) + 1;
     return k2;
 }

 /**
  * Double rotate binary tree node: first left child
  * with its right child; then node k3 with new left child.
  * For AVL trees, this is a double rotation for case 2.
  * Update heights, then return new root.
  */
 private AvlNode<AnyType> doubleWithLeftChild( AvlNode<AnyType> k3 )
 {
     k3.left = rotateWithRightChild( k3.left );
     return rotateWithLeftChild( k3 );
 }

 /**
  * Double rotate binary tree node: first right child
  * with its left child; then node k1 with new right child.
  * For AVL trees, this is a double rotation for case 3.
  * Update heights, then return new root.
  */
 private AvlNode<AnyType> doubleWithRightChild( AvlNode<AnyType> k1 )
 {
     k1.right = rotateWithLeftChild( k1.right );
     return rotateWithRightChild( k1 );
 }

 private static class AvlNode<AnyType>
 {
         // Constructors
     AvlNode( AnyType theElement )
     {
         this( theElement, null, null );
     }

     AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt )
     {
         element  = theElement;
         left     = lt;
         right    = rt;
         height   = 0;
     }

     AnyType           element;      // The data in the node
     AvlNode<AnyType>  left;         // Left child
     AvlNode<AnyType>  right;        // Right child
     int               height;       // Height
 }

   /** The tree root. */
 private AvlNode<AnyType> root;
}