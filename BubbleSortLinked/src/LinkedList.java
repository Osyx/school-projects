/**
 * Created by Oscar on 16-11-19.
 */
public class LinkedList {
    private Node first;
    private int length = 0;

    private void addFirst(int value) {
        this.first = new Node(value);
        length = 1;
    }

    // Get first node in list.
    public Node getFirst() {
        return first;
    }

    // Add a node to the list, if there are none create the first.
    public void addNode(int value) {
        if (first == null)
            addFirst(value);
        else {
            Node temp = first;
            while (temp.getNext() != null)
                temp = temp.getNext();

            temp.next = new Node(value);
            length++;
        }
    }

    // Not needed but in an actual implementation, it is good to have.
    // Get the value of a node.
    public int getValue(Node node) {
        return node.getValue();
    }

    // Not needed but in an actual implementation, it is good to have.
    // Get the value of the node at the given position.
    public int getValueAt(int position) {
        if (position > length)
            return -1;
        Node temp = first;
        for (int i = 0; i < position; i++) {
            temp = temp.getNext();
        }
        return getValue(temp);
    }

    // Swap the value of two nodes.
    public void swap(Node node1, Node node2) {
        int temp = node1.getValue();
        node1.setValue(node2.getValue());
        node2.setValue(temp);
    }

    // Get the length of the list.
    public int getLength() {
        return length;
    }

    // toString implementation.
    @Override
    public String toString() {
        String result = "";
        Node current = first;
        while(current.getNext() != null){
            result += current.getValue() + " -> ";
            current = current.getNext();
        }
        result += current.getValue();

        return result;
    }

    // The node class
    public class Node {
        private int value = 0;
        private Node next;

        private Node(int value) {
            this.value = value;
        }

        // Get the value of the node.
        public int getValue() {
            return value;
        }

        private void setValue(int value) {
            this.value = value;
        }

        // Get the next node.
        public Node getNext() {
            return next;
        }
    }
}
