package com.lhxbase.sort;

/**
 * 查找排序相关算法
 * 查看百度百科
 * 相关blog
 * http://blog.csdn.net/derrantcm/article/details/46591085
 */
public class Sort {
	/**
	 * 二分查找 要求待查表为有序表(利用递归)
	 * 
	 * @param srcArray
	 * @param target
	 * @param low
	 * @param high
	 * @return 查找对象在数组的下标位置，返回-1表示数组中没有该元素
	 */
	public static int binarySort(int[] srcArray, int target, int low,
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
			return binarySort(srcArray, target, 0, middle - 1);
		}
		if (target > srcArray[middle]) {// 如果target在右边
			return binarySort(srcArray, target, middle + 1, high);
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
	public static int binarySort(int[] srcArray, int target) {
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

	/**
	 * 冒泡排序
	 * 
	 * @param a
	 *            整型数组
	 */
	public static void bubbleSort(int[] a) {
		int temp = 0;
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[i] > a[j]) {
					temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
	}
}
