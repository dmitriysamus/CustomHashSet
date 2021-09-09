import java.util.Objects;

public class CustomHashSetImpl<T> implements CustomHashSet<T> {

    private static class Node<T> {
        T item;
        int hash;
        Node<T> next;

        /**
         * Контейнер для объекта.
         */
        public Node(T item, int hash) {
            this.item = item;
            this.hash = hash;
        }
    }

    private final int DEFAULT_CAPACITY = 10;
    private final Node<T>[] table = new Node[DEFAULT_CAPACITY];
    private int size;
    private int capacity;

    /**
     * Пустой конструктор объекта с capacity по умолчания.
     */
    CustomHashSetImpl() {
        this.capacity = DEFAULT_CAPACITY;
    }

    /**
     * Конструктор объекта, принимающий на вход capacity.
     */
    CustomHashSetImpl(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Возвращает размер множества.
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет множество на наличие объектов.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Добавляет объект в множество.
     */
    public boolean add(T newItem) {
        int bucketId= getBucket(newItem);
        int newItemHash = getItemHash(newItem);

        Node<T> tmp = table[bucketId];
        Node<T> prevTmp = null;
        while (tmp != null) {

            if (newItemHash == tmp.hash && Objects.equals(newItem, tmp.item)) {
                return false;
            }

            prevTmp = tmp;
            tmp = tmp.next;
        }

        if (prevTmp == null) {
            table[bucketId] = new Node<>(newItem, newItemHash);
        } else {
            prevTmp.next = new Node<>(newItem, newItemHash);
        }
        size++;
        return true;
    }

    /**
     * Возвращает hashCode переданного объекта.
     */
    private int getItemHash(T item) {
        if(item == null) {
            return 0;
        }
        return item.hashCode();
    }

    /**
     * Возвращает bucket переданного объекта.
     */
    private int getBucket(T item) {
        if (item == null) {
            return 0;
        }
        return item.hashCode() % (table.length - 1) + 1;
    }

    /**
     * Удаляет объект из множества.
     */
    public boolean remove(T item) {
        if (size == 0) {
            return false;
        }

        int hash = getItemHash(item);
        int bucketId = getBucket(item);
        Node<T> tmp = table[bucketId];
        Node<T> prevTmp = null;

        while (tmp != null) {
            if (tmp.hash == hash && tmp.item.equals(item)) {
                break;
            }
            prevTmp = tmp;
            tmp = tmp.next;
        }

        if (prevTmp == null) {
            if (tmp.next == null) {
                table[bucketId] = null;
            } else {
                table[bucketId] = tmp.next;
            }
        } else {
            prevTmp.next = tmp.next;
        }
        size--;
        return true;
    }

    /**
     * Проверяет наличие объекта в множестве.
     */
    public boolean contains(T item) {
        int hash = getItemHash(item);
        if (item == null && table[0] != null) {
            return true;
        }

        int bucketId = getBucket(item);
        Node<T> tmp = table[bucketId];
        while (tmp != null) {
            if (tmp.hash == hash && tmp.item.equals(item)) {
                return true;
            }
            tmp = tmp.next;
        }
        return false;
    }

    /**
     * Возвращает объекты множества в виде массива.
     */
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }

        Node<T> tmp;
        Object[] newData = new Object[size];
        int j = 0;
        for (Node<T> node : table) {
            tmp = node;
            while (tmp != null) {
                newData[j++] = tmp.item;
                tmp = tmp.next;
            }
        }
        return newData;
    }

    /**
     * Возвращает строковое представление дерева.
     */
    @Override
    public String toString() {

        StringBuilder cb = new StringBuilder();

        cb.append("[");

        for (int i = 0; i < table.length; ++i) {
            Node<T> tmp = table[i];
            while (tmp != null) {
                cb.append(" " + tmp.item);
                tmp = tmp.next;
            }
        }
        cb.append(" ]");

        return cb.toString();
    }
}
