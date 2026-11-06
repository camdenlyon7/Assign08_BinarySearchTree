package assign08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class BinarySearchTree<Type extends Comparable<? super Type>> implements SortedSet<Type> {

  private class Node {
    Type data;
    Node left;
    Node right;

    Node(Type data) {
      this.data = data;
      this.left = null;
      this.right = null;
    }
}

private Node root;
private int size;
private Comparator<? super Type> comparator;

/**
  *Constructor using natural ordering.
  */
public BinarySearchTree() {
  this.root = null;
  this.size = 0;
  this.comparator = null;
}

/**
  *Constructor using custom comparator.
  */

public BinarySearchTree(Comparator<? super Type> comparator) {
  this.root = null;
  this.size = 0;
  this.comparator = comparator;
}

/**
  * Compares two elements.
  */
private int compare(Type first, Type second) {
  if (comparator != null) {
    return comparator.compare(first, second);
  }

  return first.compareTo(second);
}

/**
  * Returns smallest element
  */
@Override
public Type min() {
  if (root == null) {
    throw new NoSuchElementException("Tree Empty!");
  }

  Node current = root;

  while (current.left != null) {
    current = current.left;
  }

  return current.data;
}

/**
  *Returns largest element.
  */
@Override
public Type max() {
  if (root == null) {
    throw new NoSuchElementException("Tree Empty!");
  }

  Node current = root;

  while (current.right != null) {
      current = current.right;
  }

  return current.data;
}

/**
  *Return number of elements
  */
@Override
public int size() {
  return size;
}

/**
  *Checks whether tree is empty.
  */

@Override
public boolean isEmpty() {
  return size == 0;
}


/**
  * Removes every element from tree.
  */

@Override 
public void clear() {
  root = null;
  size = 0;
}

/**
  *Adds element to tree.
  */
@Override
public boolean add(Type element) {
  if (element == null) {
    throw new NullPointerException("Cannot add null elements");
  }

  if (root == null) {
    root = new Node(element);
    size++;
    return true;
  }

  Node current = root;

  while (true) {
    int comparison = compare(element, current.data);

    if (comparison == 0) {
      return false;
    }

    if (comparison < 0) {
      if (current.left == null) {
        current.left = new Node(element);
        size++;
        return true;
      }

      current = current.left;

    } else {
      if (current.right == null) {
        current.right = new Node(element);
        size++;
        return true;
      }

      current = current.right;
    }
  }
}

/**
  *Adds all elements.
  */
@Override
public boolean addAll(Collection<? extends Type> items) {
  if (items == null) {
    throw new NullPointerException("Collection can't be null");
  }

  boolean changed = false;

  for (Type item : items) {
    if (add(item)) {
      changed = true;
    }
  }

  return changed;

}

/**
  * Checks if element exists.
  */
@Override
public boolean contains(Type element) {
  if (element == null) {
    return false;
  }

  Node current = root;
  while (current != null) {
    int comparison = compare(element, current.data);

    if (comparison == 0) {
      return true;
    } else if (comparison < 0) {
      current = current.left;
    } else {
      current = current.right;
    }
  }

  return false;

}

/**
  *Checks if all elements exist.
  */
@Override
public boolean containsAll(Collection<? extends Type> items) {
  if (items == null) {
    throw new NullPointerException("collection can't be null!");
  }

  for (Type item : items) {
    if (!contains(item)) {
      return false;
    }
  }

  return true;
}


/**
  *Removes element from tree.
  */
@Override
public boolean remove(Type element) {
  if (element == null) {
    return false;
  }

  boolean[] removed = {false};

  root = removeHelper(root, element, removed);

  if (removed[0]) {
    size--;
  }

  return removed[0];
}

/**
  * Recursive helper for removing node.
  */
private Node removeHelper(Node current, Type element, boolean[] removed) {
  if (current == null) {
    return null;
  }

  int comparison = compare(element, current.data);

  if (comparison < 0) {
    current.left = removeHelper(current.left, element, removed);
  } else if (comparison > 0) {
    current.right = removeHelper(current.right, element, removed);
  } else {
    removed[0] = true;

  // case 1 node has no children
  if (current.left == null && current.right == null) {
    return null;
  }

  // case 2 node has only a right child
  if (current.left == null) {
    return current.right;
  }

  // case 3 node has only a left child.
  if (current.right == null) {
    return current.left;
  }

  // case 4 node has two children.
  Node successor = findMinimum(current.right);

  current.data = successor.data;

  boolean[] ignored = {false};

  current.right = removeHelper(current.right, successor.data, ignored);
  }

return current;
}

/**
  * Find smallest node in subtree.
  */
private Node findMinimum(Node current) {
  while (current.left != null) {
    current = current.left;
  }

  return current;
}

/**
  *Removes all elements contained in collection.
  */
@Override
public boolean removeAll(Collection<? extends Type> items) {
  if (items == null) {
    throw new NullPointerException("Collection cannot be null!");
  }

  boolean changed = false;

  for (Type item : items) {
    if (remove(item)) {
      changed = true;

    }
  }
  return changed;
}

/**
  * Return elements sorted order.
  */
@Override
public ArrayList<Type> toArrayList() {
  ArrayList<Type> list = new ArrayList<>();

  inOrderTraversal(root, list);

  return list;

}

/**
  * Add elements to list using in order traversal.
  */
private void inOrderTraversal(Node current, ArrayList<Type> list) {
  if (current == null) {
    return;
  }

  inOrderTraversal(current.left, list);

  list.add(current.data);

  inOrderTraversal(current.right, list);
}
}
