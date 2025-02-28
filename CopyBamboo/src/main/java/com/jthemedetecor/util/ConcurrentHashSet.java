package com.jthemedetecor.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A thread-safe {@link Set} implementation backed by a
 * {@link ConcurrentHashMap}. This class provides a set-like data structure
 * where elements are unique and thread-safe. It allows concurrent read and
 * write operations, making it suitable for multithreaded environments.
 *
 * <p>
 * Internally, this implementation uses a {@link ConcurrentHashMap}, where the
 * keys are the set elements, and the values are constant objects (the
 * {@link ConcurrentHashSet#OBJ} constant) to ensure thread-safety.
 * </p>
 *
 * <p>
 * This implementation supports the usual {@link Set} operations, but it does
 * not support the {@code retainAll} and {@code removeAll} methods. These
 * methods will throw an {@link UnsupportedOperationException}.
 * </p>
 *
 * @param <E> the type of elements maintained by this set.
 */
public class ConcurrentHashSet<E> implements Set<E> {

    // The underlying map used for storing elements
    private final Map<E, Object> map;

    // A constant object used for values in the map
    private static final Object OBJ = new Object();

    /**
     * Constructs a new, empty set with an initial capacity of 16 and a default
     * load factor (0.75).
     */
    public ConcurrentHashSet() {
        map = new ConcurrentHashMap<>();
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the number of elements in the set.
     */
    @Override
    public int size() {
        return map.size();
    }

    /**
     * Returns {@code true} if the set contains no elements.
     *
     * @return {@code true} if the set contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Returns {@code true} if the set contains the specified element.
     *
     * @param o the element whose presence in the set is to be tested.
     * @return {@code true} if the set contains the specified element.
     */
    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    /**
     * Returns an iterator over the elements in the set.
     *
     * @return an iterator over the elements in the set.
     */
    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    /**
     * Returns an array containing all elements in the set.
     *
     * @return an array containing all elements in the set.
     */
    @Override
    public Object[] toArray() {
        return map.keySet().toArray();
    }

    /**
     * Returns an array containing all elements in the set; the runtime type of
     * the returned array is that of the specified array.
     *
     * @param a the array into which the elements of the set are to be stored.
     * @param <T> the type of the array to store elements in.
     * @return an array containing all elements in the set.
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return map.keySet().toArray(a);
    }

    /**
     * Adds the specified element to the set if it is not already present.
     *
     * @param e the element to be added to the set.
     * @return {@code true} if the set did not already contain the specified
     * element.
     */
    @Override
    public boolean add(E e) {
        return map.put(e, OBJ) == null;
    }

    /**
     * Removes the specified element from the set if it is present.
     *
     * @param o the element to be removed from the set.
     * @return {@code true} if the set contained the specified element.
     */
    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    /**
     * Returns {@code true} if the set contains all of the elements in the
     * specified collection.
     *
     * @param c the collection to be checked for containment in the set.
     * @return {@code true} if the set contains all of the elements in the
     * specified collection.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return map.keySet().containsAll(c);
    }

    /**
     * Adds all elements in the specified collection to the set.
     *
     * @param c the collection whose elements are to be added to the set.
     * @return {@code true} if the set was modified as a result of the call.
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E e : c) {
            if (map.put(e, OBJ) == null) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Throws {@link UnsupportedOperationException} since the {@code retainAll}
     * method is not supported.
     *
     * @param c the collection to retain.
     * @throws UnsupportedOperationException if this method is called.
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Throws {@link UnsupportedOperationException} since the {@code removeAll}
     * method is not supported.
     *
     * @param c the collection to remove.
     * @throws UnsupportedOperationException if this method is called.
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes all elements from the set.
     */
    @Override
    public void clear() {
        map.clear();
    }
}
