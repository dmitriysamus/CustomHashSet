/**
 * Custom hash set.
 */
public interface CustomHashSet<T> {

    int size();

    boolean isEmpty();

    /**
     * Add single item.
     */
    boolean add(T item);

    /**
     * Remove item by value
     *
     * @param item - item
     * @return true if item was removed
     */
    boolean remove(T item);

    /**
     * Checks if item exists.
     *
     * @param item - item
     * @return true or false
     */
    boolean contains(T item);

    /**
     * Get content in format '[ element1 element2 ... elementN] or [ ] if empty.
     */
    String toString();

    /**
     * Get copy of current set.
     */
    Object[] toArray();
}
