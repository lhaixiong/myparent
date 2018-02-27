package com.lhxbase.sort;

/**
 * 快速排序
 * https://baike.baidu.com/item/%E5%BF%AB%E9%80%9F%E6%8E%92%E5%BA%8F%E7%AE%97%E6%B3%95/369842?fromtitle=%E5%BF%AB%E9%80%9F%E6%8E%92%E5%BA%8F&fromid=2084344
 */
public class QuickSort {
    public static void quickSort(int[] arr,int low,int high){
        int l=low;
        int h=high;
        int povit=arr[low];
        while (l<h){
            //一次循环做的工作
            //3）从j开始向前搜索，即由后开始向前搜索(j--)，找到第一个小于key的值A[j]，将A[j]和A[i]互换；
            //4）从i开始向后搜索，即由前开始向后搜索(i++)，找到第一个大于key的A[i]，将A[i]和A[j]互换；

            while (l<h&&arr[h]>=povit){//从右往左，比基准的大的数,不管，h--直到得到比povit小的第一个数，移动到povit的左面，
                h--;
            }
            if(l<h){
                int temp=arr[h];
                arr[h]=arr[l];
                arr[l]=temp;
                l++;
            }

            while (l<h&&arr[l]<povit){//从左往右，比基准的小的数,不管，l++,直到得到比povit大的第一个数，移动到povit的右面面，
                l++;
            }
            if(l<h){
                int temp=arr[h];
                arr[h]=arr[l];
                arr[l]=temp;
                h--;
            }
            print(arr);
            System.out.print("l="+(l+1)+"h="+(h+1)+"povit="+povit+"\n");
            if(l>low){
                quickSort(arr,low,l-1);
            }
            if(h<high){
                quickSort(arr,l+1,high);
            }

        }
    }
    /**
     * 快速排序
     * @param num
     * @param left
     * @param right
     */
    public void quickSort2(int[] num, int left, int right) {
        if (num == null)
            return;
        //如果左边大于右边，则return，这里是递归的终点，需要写在前面。
        if (left >= right) {
            return;
        }
        int i = left;
        int j = right;
        int temp = num[i];
        //此处开始进入遍历循环
        while (i < j) {
            //从右往左循环
            while (i < j && num[j] >= temp) {//如果num[j]大于temp值，则pass，比较下一个
                j--;
            }
            num[i] = num[j];
            while (i < j && num[i] <= temp) {
                i++;
            }
            num[j] = num[i];


            num[i] = temp; // 此处不可遗漏，将基准值插入到指定位置
        }
        quickSort2(num, left, i - 1);
        quickSort2(num, i + 1, right);
    }
    private static void print(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
    }
}
