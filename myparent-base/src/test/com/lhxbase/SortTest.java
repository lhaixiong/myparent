package lhxbase;

import com.lhxbase.sort.Sort;
import org.junit.Test;

public class SortTest {
    @Test
    public void testBinarySearch(){
        int[] a={12, 15, 24, 45, 51, 78, 84, 95};
        System.out.println("original array:");
        for (int i=0;i<a.length;i++){
            System.out.print(a[i]+"   ");
        }
        System.out.println("\n二分查找78在数组中的位置:"+Sort.binarySearch(a, 78, 0, a.length - 1));
        System.out.println("二分查找0在数组中的位置:"+Sort.binarySearch(a, 0, 0, a.length - 1));
    }
    @Test
    public void testBubbleSort(){
        int[] a={95, 45, 15, 78, 84, 51, 24, 12};
        System.out.println("before bubble sort:");
        for (int i=0;i<a.length;i++){
            System.out.print(a[i]+"   ");
        }

        Sort.bubbleSort(a);

        System.out.println("\nafter bubble sort:");
        for (int i=0;i<a.length;i++){
            System.out.print(a[i]+"   ");
        }
    }
}
