package com.lhxbase.sort;

public class SortTest {
    public static void main(String[] args) {
        int[] arr={3,8,9,5,6,11,2};
        System.out.println("原数组:3,8,9,5,6,11,2");
        //CocktailSort.cocktailSort(arr);
        QuickSort.quickSort(arr,0,arr.length-1);
    }
}
