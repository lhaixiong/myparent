package com.lhxbase.sort;

/**
 * 二分查找
 */
public class BinarySearch {
    /**
     * 二分查找 要求待查表为有序表(利用递归)
     *
     * @param srcArray
     * @param target
     * @param low
     * @param high
     * @return 查找对象在数组的下标位置，返回-1表示数组中没有该元素
     */
    public static int binarySearch(int[] srcArray, int target, int low,
                                 int high) {
        int middle = (low + high) / 2;
        if (low > high) {// 查找完毕没有找到，返回-1
            System.out.println("数组中没有" + target + "这个元素");
            return -1;
        }
        if (target == srcArray[middle]) {// 若果找到到，直接返回中间位置
            return middle;
        }
        if (target < srcArray[middle]) {// 如果target在左边
            return binarySearch(srcArray, target, 0, middle - 1);
        }
        if (target > srcArray[middle]) {// 如果target在右边
            return binarySearch(srcArray, target, middle + 1, high);
        }
        return -1;
    }

    /**
     * 二分查找 要求待查表为有序表(利用while循环)
     *
     * @param srcArray
     * @param target
     * @return 查找对象在数组的下标位置，返回-1表示数组中没有该元素
     */
    public static int binarySearch(int[] srcArray, int target) {
        int low = 0;
        int high = srcArray.length - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (target == srcArray[middle]) {// 查找成功则返回所在位置
                return middle;
            } else if (target < srcArray[middle]) {// 如果target在左边
                high = middle - 1;
            } else {// 如果target在右边
                low = middle + 1;
            }
        }
        return -1;
    }
}
