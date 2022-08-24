/**
 * Created by Oscar on 16-11-19.
 */
public class BubbleSort {

    LinkedList sort(LinkedList a) {
        int R = a.getLength() - 2;
        int count = 0;
        boolean swapped = true;
        while(R >= 0 && swapped) {
            LinkedList.Node temp = a.getFirst();
            swapped = false;
            for (int i = 0; i <= R; i++) {
                if (temp.getValue() > temp.getNext().getValue()) {
                    a.swap(temp, temp.getNext());
                    swapped = true;
                    count++;
                }
                temp = temp.getNext();
            }
            R = R - 1;
        }
        System.out.println("Bubble count swapped " + count + " times.");
        return a;
    }
}
