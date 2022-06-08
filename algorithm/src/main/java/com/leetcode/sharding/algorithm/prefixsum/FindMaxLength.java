package com.leetcode.sharding.algorithm.prefixsum;

import java.util.Arrays;

/**
 * 给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: nums = [0,1]
 * 输出: 2
 * 说明: [0, 1] 是具有相同数量 0 和 1 的最长连续子数组。
 * 示例 2:
 *
 * 输入: nums = [0,1,0]
 * 输出: 2
 * 说明: [0, 1] (或 [1, 0]) 是具有相同数量0和1的最长连续子数组。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/contiguous-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindMaxLength {

    public int findMaxLength(int[] nums) {
        int n = nums.length;
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) sum[i] = sum[i - 1] + (nums[i - 1] == 1 ? 1 : -1);
        int ans = 0;
        int[] hash = new int[2 * n + 1];
        Arrays.fill(hash, -1);
        for (int i = 2; i <= n; i++) {
            if (hash[sum[i - 2] + n] == -1) hash[sum[i - 2] + n] = i - 2;
            if (hash[sum[i] + n] != -1) ans = Math.max(ans, i - hash[sum[i] + n]);
        }
        return ans;
    }
}
