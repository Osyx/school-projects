/**
 * Created by Oscar on 16-11-19.
 */
class Inversions {
    private int[] temp;

    public int count(LinkedList list) {
        int[] arr = new int[list.getLength()];
        LinkedList.Node temp = list.getFirst();

        for (int i = 0; i < list.getLength(); i++) {
            arr[i] = temp.getValue();
            temp = temp.getNext();
        }
        this.temp = new int[arr.length];
        return mergeSort(arr, 0, arr.length);
    }

    private int mergeSort (int[] a, int low, int high) {
        if (low == high - 1) return 0;
        int mid = (low + high)/2;
        return mergeSort (a, low, mid) + mergeSort (a, mid, high) + merge (a, low, mid, high);
    }

    private int merge (int[] a, int low, int mid, int high) {
        int count = 0;

        for (int i = low, lo = low, hi = mid; i < high; i++) {

            if (hi >= high || lo < mid && a[lo] <= a[hi]) {
                temp[i]  = a[lo++];
            } else {
                count = count + (mid - lo);
                temp[i]  = a[hi++];
            }
        }
        System.arraycopy(temp, low, a, low, high - low);

        return count;
    }
}