import java.util.List;

public class BubbleSort {

    static void sort(List<WordNode.AboutWord> list, String property, Boolean ascending) {
        int R = list.size() - 2;
        boolean shouldSwap = false;
        boolean swapped = true;
        while(R >= 0 && swapped) {
            swapped = false;
            for (int i = 0; i <= R; i++) {
                if (property.matches("count"))
                    shouldSwap = list.get(i).getCount() > list.get(i + 1).getCount();
                if (property.matches("popularity"))
                    shouldSwap = list.get(i).attributes.document.popularity > list.get(i + 1).attributes.document.popularity;
                if (property.matches("occurrence"))
                    shouldSwap = list.get(i).attributes.occurrence > list.get(i + 1).attributes.occurrence;

                if (shouldSwap) {
                    swap(list, i, i + 1);
                    swapped = true;
                    shouldSwap = false;
                }
            }
            R = R - 1;
        }
        if(!ascending) {
            for (int i = 0; i < list.size()/2; i++)
                swap(list, i, list.size() - 1 - i);
        }
    }

    public static void swap(List<WordNode.AboutWord> list, int elem1, int elem2) {
        WordNode.AboutWord temp = list.get(elem1);
        list.set(elem1, list.get(elem2));
        list.set(elem2, temp);
    }
}