package assign08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

import assign03.SortedList;

public class ArraySortedSet<Type extends Comparable<? super Type>> implements SortedSet<Type>{
	private Object[] elements;
    private int size;
    private Comparator<? super Type> comparator;
    
    /**
     * Constructor for natural ordering
     */
    public ArraySortedSet() {
        this.elements = new Object[16];
        this.size = 0;
        this.comparator = null;
    }
    
    /**
     * Constructor for custom ordering
     * 
     * @param comparator, a functor to sort entries by
     */
    public ArraySortedSet(Comparator<? super Type> comparator) {
        this.elements = new Object[16];
        this.size = 0;
        this.comparator = comparator;
    }
    
    /**
     * private helper to retrieve and cast elements from the backing array
     * 
     * @param index, the index of the element to retrieve
     * @return the element cast to Type
     */
    @SuppressWarnings("unchecked")
    private Type get(int index) {
        return (Type) this.elements[index];
    }
    
    /**
     * private helper method to compare elements
     * 
     * @param t1, first element
     * @param t2, second element
     * @return negative if t1 < t2, zero if t1 == t2, positive if t1 > t2
     */
    private int compare(Type t1, Type t2) {
        if (this.comparator != null) {
            return this.comparator.compare(t1, t2);
        } else {
            return t1.compareTo(t2);
        }
    }
    
    /**
     * private helper method using binary search to find target index
     * 
     * @param target, the element to find
     * @return index of element if found or the negative index to insert it at shifted by one
     */
    private int binarySearch(Type target) {
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = compare(get(mid), target);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -(low + 1);
    }
    
    /**
     * Doubles the capacity of the backing array
     */
    private void resize() {
        Object[] newArray = new Object[this.elements.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = this.elements[i];
        }
        this.elements = newArray;
    }
    
    @Override
    public Type min() throws NoSuchElementException {
        if (size == 0) {
            throw new NoSuchElementException("Set is empty");
        }
        return get(0);
    }
    
    @Override
    public Type max() throws NoSuchElementException {
        if (size == 0) {
            throw new NoSuchElementException("Set is empty");
        }
        return get(size - 1);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            this.elements[i] = null; 
        }
        this.size = 0;
    }
    
    @Override
    public boolean add(Type element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null elements");
        }
        int index = binarySearch(element);
        if (index >= 0) {
            return false;
        }
        if (size == this.elements.length) {
            resize();
        }
        int insertIndex = -(index + 1);
        for (int i = size; i > insertIndex; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[insertIndex] = element;
        size++;
        return true;
    }
    
    @Override
    public boolean addAll(Collection<? extends Type> items) {
        if (items == null) {
            throw new NullPointerException("Collection cannot be null");
        }
        boolean changed = false;
        for (Type item : items) {
            if (add(item)) {
                changed = true;
            }
        }
        return changed;
    }
    
    @Override
    public boolean contains(Type element) {
        if (element == null || size == 0) {
            return false;
        }
        return binarySearch(element) >= 0;
    }
    
    @Override
    public boolean containsAll(Collection<? extends Type> items) {
        if (items == null) {
            throw new NullPointerException("Collection cannot be null");
        }
        for (Type item : items) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public ArrayList<Type> toArrayList() {
    	ArrayList<Type> list = new ArrayList<>(this.size);
        for (int i = 0; i < this.size; i++) {
            list.add(get(i));
        }
        return list;
    }
}
