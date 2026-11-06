package assign08;

/**
  *Represents single node in a binary search tree
*/

class BinarySearchTreeNode<Type> {
  Type data;
  BinarySearchTreeNode<Type> left;
  BinarySearchTreeNode<Type> right;

/**
  *Constructor for new node
  */

BinarySearchTreeNode(Type data) {
    this.data = data;
    this.left = null;
    this.right = null;
  }
}
  
