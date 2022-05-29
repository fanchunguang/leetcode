package com.leetcode.sharding.algorithm;

/**
 * 给定一个正整数数组 nums和一个整数 k，返回 num中 「好子数组」的数目。
 *
 * 如果 nums的某个子数组中不同整数的个数恰好为 k，则称 nums的这个连续、不一定不同的子数组为 「好子数组 」。
 *
 * 例如，[1,2,3,1,2] 中有3个不同的整数：1，2，以及3。
 * 子数组 是数组的 连续 部分。
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,1,2,3], k = 2
 * 输出：7
 * 解释：恰好由 2 个不同整数组成的子数组：[1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/subarrays-with-k-different-integers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SubArraysWithKDIstinct {

    public int subArraysWithDistinct(int[] A, int k) {
        return atMostDistinct(A, k) - atMostDistinct(A, k-1);
    }

    /**
     *
     * @param A
     * @param K
     * @return
     */
    private int atMostDistinct(int[] A, int K) {
        int length = A.length;
        int[] freq = new int[length + 1];

        int left = 0;
        int right = 0;
        // [left,right) 里不同整数的个数
        int count = 0;
        int res = 0;
        // [left, right) 包含不同整数的个数小于等于k
        while (right < length){
            if (freq[A[right]] == 0) {
                count ++;
            }
            freq[A[right]] ++;
            right++;

            while (count > K){
                freq[A[left]]--;
                if (freq[A[left]] ==0) {
                    count--;
                }
                left++;
            }
            res+=right - left;
        }
        return res;
    }
}
