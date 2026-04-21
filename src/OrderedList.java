package edu.commonwealthu.mrodriguez.cmsc230.listlab;

// Implement this class as a working linked list.
// Your list must keep itself sorted using the contained type's natural ordering.
// This invariant is maintained by ensuring the add() method puts them into the correct position.
//
// Change this class into a generic class that takes a type parameter representing the objects it contains.
// Declare an upper bound on this type parameter so that you only accept objects that have a natural ordering.
// Do this properly, so that no casting is necessary.
//
// Below, anywhere you see an Object that represents an element of this list,
// change its type to the type parameter you defined.
// Make sure any reference to any generic class (including OrderedList itself) is properly parameterized.
//
// You must define a class to represent nodes in this list; this too should be parameterized.
// If the autograding fails due to not finding this node class, explicitly import it.
// (Do this even though normally you would not need to if it's in the same package.)
public class OrderedList<T extends Comparable<? super T>> {

    //node
    private static class Node<U> {
        U data;
        Node<U> next;
        Node(U d) { data = d; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int length;


    public OrderedList() {
        // Construct an empty list.
        head = null;
        tail = null;
        length = 0;
    }

    public void insert(T o) {
        // Insert o into the list in the correct place to keep it sorted.
        if (o == null) throw new NullPointerException("element is null");
        Node<T> n = new Node<>(o);

        if (head == null) { // empty
            head = tail = n;
            length++;
            return;
        }

        if (o.compareTo(head.data) <= 0) {
            n.next = head;
            head = n;
            length++;
            return;
        }

        Node<T> prev = head;
        Node<T> cur = head.next;
        while (cur != null && o.compareTo(cur.data) > 0) {
            prev = cur;
            cur = cur.next;
        }
        prev.next = n;
        n.next = cur;
        if (cur == null) tail = n; // appended
        length++;
    }

    public void add(T o) {
        insert(o); }


    public T get(int k) {
        // Get the element at index k.
        if (k < 0 || k >= length) throw new IndexOutOfBoundsException("k=" + k);
        Node<T> cur = head;
        for (int i = 0; i < k; i++) cur = cur.next;
        return cur.data;    }


    public boolean remove(T o) {
        // Find and remove the first item equal to o.
        // Return true if succeeded; false if no such element.
        if (o == null || head == null) return false;

        if (head.data.equals(o)) {
            head = head.next;
            if (head == null) tail = null;
            length--;
            return true;
        }

        Node<T> prev = head;
        Node<T> cur = head.next;
        while (cur != null) {
            if (cur.data.compareTo(o) > 0) return false; // already passed it in sorted order
            if (cur.data.equals(o)) {
                prev.next = cur.next;
                if (cur == tail) tail = prev;
                length--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }


    public OrderedList<T> kLargest(int k) {
        // Create a new list and copy the k largest elements from this list into it.
        OrderedList<T> out = new OrderedList<>();
        if (k <= 0 || length == 0) return out;
        if (k > length) k = length;

        int skip = length - k; // start at (length-k)th node
        Node<T> cur = head;
        for (int i = 0; i < skip; i++) cur = cur.next;

        while (cur != null) {
            out.appendTail(cur.data);
            cur = cur.next;
        }
        return out;
    }

    public OrderedList<T> merge(OrderedList<T> that) {
        // Create a new list that represents the merger of this list and that.
        // This method must be implemented reasonably efficiently.
        OrderedList<T> out = new OrderedList<>();
        Node<T> a = this.head;
        Node<T> b = (that == null) ? null : that.head;

        while (a != null && b != null) {
            if (a.data.compareTo(b.data) <= 0) {
                out.appendTail(a.data);
                a = a.next;
            } else {
                out.appendTail(b.data);
                b = b.next;
            }
        }
        while (a != null) { out.appendTail(a.data); a = a.next; }
        while (b != null) { out.appendTail(b.data); b = b.next; }
        return out;
    }

    // helper: O(1) tail append (does not sort; only for internal use)
    private void appendTail(T x) {
        Node<T> n = new Node<>(x);
        if (head == null) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        length++;
    }


    @Override
    public String toString() {
        // Print the list as in the following example format:
        // [1, 2, 3]
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node<T> cur = head;
        while (cur != null) {
            sb.append(cur.data);
            cur = cur.next;
            if (cur != null) sb.append(", ");
        }
        sb.append(']');
        return sb.toString();
    }

    public int getLength() {
        // Return the number of elements in the list.
        return length;
    }

    @Override
    public boolean equals(Object o) {
        // Determine if o is an OrderedList of the same length,
        // where each element of it .equals() the element at the same index in this list.
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof OrderedList<?>)) return false;

        OrderedList<?> other = (OrderedList<?>) o;
        if (this.length != other.getLength()) return false;

        // allowed to access private fields of the same class
        Node<T> a = this.head;
        Node<?> b = other.head;
        while (a != null && b != null) {
            if (a.data == null ? b.data != null : !a.data.equals(b.data)) return false;
            a = a.next;
            b = ((Node<?>) b).next;
        }
        return a == null && b == null;
    }
}
