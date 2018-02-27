package com.lhxbase.sort;

import java.util.Arrays;

/**
 * 鸡尾酒排序
 *
 * 数组中的数字本是无规律的排放，先找到最小的数字，把他放到第一位，然后找到最大的数字放到最后一位。
 * 然后再找到第二小的数字放到第二位，再找到第二大的数字放到倒数第二位。以此类推，直到完成排序。
 */
public class CocktailSort {
    public static int[] cocktailSort(int[] src)
    {
        for(int i = 0 ; i < src.length/2 ; i++)
        {
            //将最小值排到队尾
            for(int j = i ; j < src.length-i-1 ; j++)
            {
                if(src[j] < src[j+1])
                {
                    int temp = src[j];
                    src[j] = src[j+1];
                    src[j+1] = temp;
                }
                System.out.println("交换小"+ Arrays.toString(src));
            }
            //将最大值排到队头
            for(int j = src.length-1-(i+1); j > i ; j--)
            {
                if(src[j] > src[j-1])
                {
                    int temp = src[j];
                    src[j] = src[j-1];
                    src[j-1] = temp;
                }
                System.out.println("交换大"+Arrays.toString(src));
            }
            System.out.println("第"+i+"次排序结果："+Arrays.toString(src));
        }
        return src;
    }
}
