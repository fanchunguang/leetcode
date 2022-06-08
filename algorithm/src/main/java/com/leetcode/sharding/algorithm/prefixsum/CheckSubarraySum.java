package com.leetcode.sharding.algorithm.prefixsum;

import java.util.HashSet;
import java.util.Set;

/**
 * 给你一个整数数组 nums 和一个整数 k ，编写一个函数来判断该数组是否含有同时满足下述条件的连续子数组：
 *
 * 子数组大小 至少为 2 ，且
 * 子数组元素总和为 k 的倍数。
 * 如果存在，返回 true ；否则，返回 false 。
 *
 * 如果存在一个整数 n ，令整数 x 符合 x = n * k ，则称 x 是 k 的一个倍数。0 始终视为 k 的一个倍数。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [23,2,4,6,7], k = 6
 * 输出：true
 * 解释：[2,4] 是一个大小为 2 的子数组，并且和为 6 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/continuous-subarray-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class CheckSubarraySum {
    /**
     * 要使得两者除 kk 相减为整数，需要满足 sum[j]sum[j] 和 sum[i - 1]sum[i−1] 对 kk 取余相同。
     *
     * 也就是说，我们只需要枚举右端点 jj，然后在枚举右端点 jj 的时候检查之前是否出现过左端点 ii，使得 sum[j]sum[j] 和 sum[i - 1]sum[i−1] 对 kk 取余相同。
     *
     * 前缀和 + HashSet
     * 具体的，使用 HashSet 来保存出现过的值。
     *
     * 让循环从 22 开始枚举右端点（根据题目要求，子数组长度至少为 22），每次将符合长度要求的位置的取余结果存入 HashSet。
     *
     * 如果枚举某个右端点 jj 时发现存在某个左端点 ii 符合要求，则返回 True。
     *
     * 作者：AC_OIer
     * 链接：https://leetcode.cn/problems/continuous-subarray-sum/solution/gong-shui-san-xie-tuo-zhan-wei-qiu-fang-1juse/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param nums
     * @param k
     * @return
     */
    public boolean checkSubarraySum(int[] nums, int k){
        int n = nums.length;
        int[] sum = new int[n+1];
        for(int i=1;i<nums.length;i++){
            sum[i] = sum[i-1] + nums[i-1];
        }
        Set<Integer> set = new HashSet<>();
        for(int i =2; i<=n;i++){
            set.add(sum[i-2] % k);
            if(set.contains(sum[i] % k)) return true;
        }
        return false;
    }
}
