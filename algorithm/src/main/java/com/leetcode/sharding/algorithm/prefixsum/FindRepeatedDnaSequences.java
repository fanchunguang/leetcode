package com.leetcode.sharding.algorithm.prefixsum;

import java.util.*;

/**
 * 所有 DNA 都由一系列缩写为 'A'，'C'，'G' 和 'T' 的核苷酸组成，例如："ACGAATTCCG"。在研究 DNA 时，识别 DNA 中的重复序列有时会对研究非常有帮助。
 *
 * 编写一个函数来找出所有目标子串，目标子串的长度为 ，且在 DNA 字符串 s 中出现次数超过一次。
 * 示例 1：
 *
 * 输入：s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 *
 * 输出：["AAAAACCCCC","CCCCCAAAAA"]
 */
public class FindRepeatedDnaSequences {

    /**
     * 我们使用一个与字符串  等长的哈希数组 ，以及次方数组 。
     *
     * 由字符串预处理得到这样的哈希数组和次方数组复杂度为 。当我们需要计算子串 的哈希值，只需要利用前缀和思想  即可在  时间内得出哈希值（与子串长度无关）。
     *
     * 到这里，还有一个小小的细节需要注意：如果我们期望做到严格 ，进行计数的「哈希表」就不能是以 String 作为 key，只能使用 Integer（也就是 hash 结果本身）作为 key。因为 Java 中的 String 的 hashCode 实现是会对字符串进行遍历的，这样哈希计数过程仍与长度有关，而 Integer 的 hashCode 就是该值本身，这是与长度无关的。
     * @param s
     * @return
     */
    static int N = (int)1e5 + 10, P = 131313;
    static int[] h = new int[N], p = new int[N];
    public static List<String> findRepeatedDnaSequences(String s) {
        int n = s.length();
        List<String> ans = new ArrayList<>();
        p[0] = 1;
        for (int i=1; i<=n; i++){
            h[i] = h[i-1] * P + s.charAt(i-1);
            p[i] = p[i-1] * P;
        }
        Map<Integer,Integer> map = new HashMap<>();
        for (int i=1; i+10-1 <=n; i++){
            int j = i+10-1;
            int hash = h[j] - h[i-1] * p[j-i+1];
            int cnt = map.getOrDefault(hash, 0);
            if (cnt==1)
                ans.add(s.substring(i-1, i+10-1));
            map.put(hash, cnt+1);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"));
    }
}
