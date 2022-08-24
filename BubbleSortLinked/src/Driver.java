import java.util.Random;

/**
 * Created by Oscar on 16-11-19.
 */
public class Driver {
    public static void main(String[] args) {
        BubbleSort bubbleSort = new BubbleSort();
        LinkedList test = new LinkedList();
        LinkedList test2 = new LinkedList();
        Inversions inversions = new Inversions();
        Random rndm = new Random();
        for (int i = 0; i < 10; i++) {
            test.addNode(rndm.nextInt(10));
        }
        System.out.println("Random list:\n" + test.toString()
                + "\nNumber of inversions: " + inversions.count(test) + ".");
        System.out.println(bubbleSort.sort(test).toString());
        test2.addNode(1);
        test2.addNode(2);
        test2.addNode(4);
        test2.addNode(3);
        test2.addNode(5);
        test2.addNode(0);
        System.out.println("Lab list:\n" + test2.toString()
                + "\nNumber of inversions: " + inversions.count(test2) + ".");
        System.out.println(bubbleSort.sort(test2));
    }
}
