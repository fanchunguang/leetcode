package com.leetcode.sharding.algorithm.pointer;

/**
 * 复写零
 * 给你一个长度固定的整数数组arr，请你将该数组中出现的每个零都复写一遍，并将其余的元素向右平移。
 * 注意：请不要在超过该数组长度的位置写入元素。
 * 要求：请对输入的数组就地进行上述修改，不要从函数返回任何东西。
 *
 * 示例 1：
 *
 * 输入：[1,0,2,3,0,4,5,0]
 * 输出：null
 * 解释：调用函数后，输入的数组将被修改为：[1,0,0,2,3,0,0,4]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/duplicate-zeros
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class DuplicateZeros {
    /**
     * 而实际上我们可以不需要开辟栈空间来模拟放置元素，我们只需要用两个指针来进行标记栈顶位置和现在需要放置的元素位置即可。我们用 \textit{top}top 来标记栈顶位置，用 ii 来标记现在需要放置的元素位置，那么我们找到原数组中对应放置在最后位置的元素位置，然后在数组最后从该位置元素往前来进行模拟放置即可
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode.cn/problems/duplicate-zeros/solution/fu-xie-ling-by-leetcode-solution-7ael/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param arr
     */
    public static void duplicateZeros(int[] arr){
        int n = arr.length;
        int top = 0;
        int i = -1;
        while (top < n) {
            i++;
            if (arr[i] !=0) {
                top++;
            } else {
                top +=2;
            }
        }
        int j = n - 1;
        if (top == n + 1){
            arr[j] = 0;
            j--;
            i--;
        }
        while (j>=0) {
            arr[j] = arr[i];
            j--;
            if (arr[i] == 0) {
                arr[j] = arr[i];
                j--;
            }
            i--;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 0, 2, 3, 0, 4, 5, 0};
        duplicateZeros(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
