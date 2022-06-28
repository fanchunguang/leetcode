package com.leetcode.sharding.algorithm.other;

import java.util.HashSet;
import java.util.Set;

/**
 * 给你一个字符串 s ，考虑其所有 重复子串 ：即 s 的（连续）子串，在 s 中出现 2 次或更多次。这些出现之间可能存在重叠。
 *
 * 返回 任意一个 可能具有最长长度的重复子串。如果 s 不含重复子串，那么答案为 "" 。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：s = "banana"
 * 输出："ana"
 * 示例 2：
 *
 * 输入：s = "abcd"
 * 输出：""
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/longest-duplicate-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LongestDupSubstring {

    /**
     * 字符串哈希 + 二分
     *
     * 题目要求得「能取得最大长度的任一方案」，首先以「最大长度」为分割点的数轴具有「二段性」：
     *
     * 小于等于最大长度方案均存在（考虑在最大长度方案上做删减）；
     * 大于最大长度的方案不存在。
     * 二分范围为
     * [0,n]，关键在于如何 check 函数，即实现「检查某个长度
     * len 作为最大长度，是否存在合法方案」。
     *
     * 对于常规做法而言，可枚举每个位置作为起点，得到长度为
     * len 的子串，同时使用 Set<String> 容器记录已被处理过子串有哪些，当容器中出现过当前子串，说明存在合法方案。
     *
     * 但是该做法实现的 check 并非线性，子串的生成和存入容器的时执行的哈希函数执行均和子串长度相关，复杂度是
     * O(n∗len)。
     *
     * 我们可以通过「字符串哈希」进行优化，对「字符串哈希」不熟悉的同学可以看前置 🧀 字符串哈希入门。
     *
     * 具体的，在二分前先通过
     * O(n) 的复杂度预处理出哈希数组，从而确保能够在 check 时能够
     * O(1) 得到某个子串的哈希值，最终将 check 的复杂度降为
     * O(n)。
     *
     * 作者：AC_OIer
     * 链接：https://leetcode.cn/problems/longest-duplicate-substring/solution/gong-shui-san-xie-zi-fu-chuan-ha-xi-ying-hae9/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */

    long[] h, p;
    public String longestDupSubstring(String s) {
        int P = 1313131, n = s.length();
        h = new long[n + 10]; p = new long[n + 10];
        p[0] = 1;
        for (int i = 0; i < n; i++) {
            p[i + 1] = p[i] * P;
            h[i + 1] = h[i] * P + s.charAt(i);
        }
        String ans = "";
        int l = 0, r = n;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            String t = check(s, mid);
            if (t.length() != 0) l = mid;
            else r = mid - 1;
            ans = t.length() > ans.length() ? t : ans;
        }
        return ans;
    }
    String check(String s, int len) {
        int n = s.length();
        Set<Long> set = new HashSet<>();
        for (int i = 1; i + len - 1 <= n; i++) {
            int j = i + len - 1;
            long cur = h[j] - h[i - 1] * p[j - i + 1];
            if (set.contains(cur)) return s.substring(i - 1, j);
            set.add(cur);
        }
        return "";
    }
}
