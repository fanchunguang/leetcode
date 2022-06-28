package com.leetcode.sharding.algorithm.lcs;

/**
 * 给定字符串列表strs ，返回其中 最长的特殊序列 。如果最长特殊序列不存在，返回 -1 。
 *
 * 特殊序列 定义如下：该序列为某字符串 独有的子序列（即不能是其他字符串的子序列）。
 *
 * s的子序列可以通过删去字符串s中的某些字符实现。
 *
 * 例如，"abc" 是 "aebdc" 的子序列，因为您可以删除"aebdc"中的下划线字符来得到 "abc" 。"aebdc"的子序列还包括"aebdc"、 "aeb" 和 "" (空字符串)。
 *
 * 示例 1：
 * 输入: strs = ["aba","cdc","eae"]
 * 输出: 3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/longest-uncommon-subsequence-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindLUSLength {

    public int findLUSlength(String[] strs) {
        int n = strs.length;
        int ans = -1;
        for (int i = 0; i < n; ++i) {
            boolean check = true;
            for (int j = 0; j < n; ++j) {
                if (i != j && isSubseq(strs[i], strs[j])) {
                    check = false;
                    break;
                }
            }
            if (check) {
                ans = Math.max(ans, strs[i].length());
            }
        }
        return ans;
    }

    public boolean isSubseq(String s, String t) {
        int ptS = 0, ptT = 0;
        while (ptS < s.length() && ptT < t.length()) {
            if (s.charAt(ptS) == t.charAt(ptT)) {
                ++ptS;
            }
            ++ptT;
        }
        return ptS == s.length();
    }

    public static void main(String[] args) {

    }
}
