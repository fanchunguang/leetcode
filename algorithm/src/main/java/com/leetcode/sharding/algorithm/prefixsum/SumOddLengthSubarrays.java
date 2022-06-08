package com.leetcode.sharding.algorithm.prefixsum;

/**
 * 给你一个正整数数组 arr ，请你计算所有可能的奇数长度子数组的和。
 *
 * 子数组 定义为原数组中的一个连续子序列。
 *
 * 请你返回 arr 中 所有奇数长度子数组的和 。
 *
 * 示例 1：
 *
 * 输入：arr = [1,4,2,5,3]
 * 输出：58
 * 解释：所有奇数长度子数组和它们的和为：
 * [1] = 1
 * [4] = 4
 * [2] = 2
 * [5] = 5
 * [3] = 3
 * [1,4,2] = 7
 * [4,2,5] = 11
 * [2,5,3] = 10
 * [1,4,2,5,3] = 15
 * 我们将所有值求和得到 1 + 4 + 2 + 5 + 3 + 7 + 11 + 10 + 15 = 58
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/sum-of-all-odd-length-subarrays
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SumOddLengthSubarrays {

    public static int sumOddLengthSubarrays(int[] arr) {
        int n = arr.length;
        int[] sum = new int[n+1];
        for(int j=1;j<n;j++){
            sum[j] = sum[j-1] + arr[j-1];
        }
        int ans = 0;
        for (int len = 1; len < n; len+=2) {
            for(int i=0;i+len -1 < n;i++){
                int r = i + len -1;
                ans += sum[r+1] - sum[i];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,4,2,5,3};
        sumOddLengthSubarrays(arr);
    }
}
