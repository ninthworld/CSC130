import java.util.Arrays;
import java.util.Random;

/**
 * Created by NinthWorld on 4/25/2016.
 */
public class Assignment4 {

    public static void main(String[] args) {
        Random rand = new Random(12345);
        int[] numbers = new int[1000000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rand.nextInt();
        }

        int nthLargest, nth = 12;
        String suffix = (nth == 1 ? "st" : (nth%10 == 2 && nth != 12 ? "nd" : (nth%10 == 3 && nth != 13 ? "rd" : "th" )));

        Heap heap = new Heap(numbers);
        nthLargest = heap.nthLargest(nth);
        System.out.printf("HeapSort:    The %d%s largest number is %d.\n", nth, suffix, nthLargest);

        QuickSelect quickSort = new QuickSelect(numbers);
        nthLargest = quickSort.nthLargest(nth);
        System.out.printf("QuickSelect: The %d%s largest number is %d.\n", nth, suffix, nthLargest);
    }
}

class QuickSelect {
    private int[] array;

    public QuickSelect(int[] array){
        this.array = Arrays.copyOf(array, array.length);
    }

    public int nthLargest(int nth){
        return nthLargest(0, array.length-1, nth);
    }

    public int nthLargest(int left, int right, int nth){
        int pos = randomPartition(left, right);
        if (pos-left == nth-1) {
            return array[pos];
        }
        if (pos-left > nth-1) {
            return nthLargest(left, pos-1, nth);
        }

        return nthLargest(pos+1, right, nth-pos+left-1);
    }

    public void swap(int a, int b){
        int c = array[a];
        array[a] = array[b];
        array[b] = c;
    }

    public int partition(int left, int right){
        int i = left;
        for (int j=left; j <= right-1; j++){
            if (array[j] > array[right]){
                swap(i, j);
                i++;
            }
        }
        swap(i, right);
        return i;
    }

    public int randomPartition(int left, int right){
        int pivot = (int)(Math.random()*(right-left+1));
        swap(left + pivot, right);
        return partition(left, right);
    }
}

class Heap {
    private int[] array;
    private int heapSize;

    public Heap(int[] array){
        this.array = Arrays.copyOf(array, array.length);
        this.heapSize = this.array.length;
    }

    public int size(){
        return array.length;
    }

    public void swap(int a, int b){
        int c = array[a];
        array[a] = array[b];
        array[b] = c;
    }

    public int nthLargest(int nth){
        buildHeap();
        heapSize = size() - 1;
        for(int i=heapSize; i >= size()-nth; i--){
            swap(0, heapSize);
            heapSize--;
            heapify(0);
        }
        return array[array.length - nth];
    }

    public void buildHeap(){
        heapSize = size() - 1;
        for(int i=(size()/2); i >= 0; i--){
            heapify(i);
        }
    }

    public void heapify(int i){
        int largest = i;
        int left = i*2;
        int right = i*2 + 1;
        if(left <= heapSize && array[left] > array[i]){
            largest = left;
        }
        if(right <= heapSize && array[right] > array[largest]){
            largest = right;
        }
        if(largest != i){
            swap(i, largest);
            heapify(largest);
        }
    }
}