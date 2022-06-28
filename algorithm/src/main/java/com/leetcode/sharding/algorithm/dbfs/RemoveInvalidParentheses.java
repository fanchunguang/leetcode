package com.leetcode.sharding.algorithm.dbfs;

import java.util.*;

/**
 * 给你一个由若干括号和字母组成的字符串 s ，删除最小数量的无效括号，使得输入的字符串有效。
 *
 * 返回所有可能的结果。答案可以按 任意顺序 返回。
 * 示例 1：
 *
 * 输入：s = "()())()"
 * 输出：["(())()","()()()"]
 * 示例 2：
 *
 * 输入：s = "(a)())()"
 * 输出：["(a())()","(a)()()"]
 * 示例 3：
 *
 * 输入：s = ")("
 * 输出：[""]
 *
 * 提示：
 * 1 <= s.length <= 25
 * s 由小写英文字母以及括号 '(' 和 ')' 组成
 * s 中至多含 20 个括号
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/remove-invalid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class RemoveInvalidParentheses {
    static Set<String> set = new HashSet<>();
    static int n, max, len;
    static String s;
    /**
     * 由于题目要求我们将所有（最长）合法方案输出，因此不可能有别的优化，只能进行「爆搜」。
     * 我们可以使用 DFS 实现回溯搜索。
     *
     * 基本思路：
     * 我们知道所有的合法方案，必然有左括号的数量与右括号数量相等。
     *
     * 首先我们令左括号的得分为 1；右括号的得分为 -1。那么对于合法的方案而言，必定满足最终得分为 0。
     * 同时我们可以预处理出「爆搜」过程的最大得分： max = min(左括号的数量, 右括号的数量)
     * PS.「爆搜」过程的最大得分必然是：合法左括号先全部出现在左边，之后使用最多的合法右括号进行匹配。
     * 枚举过程中出现字符分三种情况：
     *
     * 普通字符：无须删除，直接添加
     * 左括号：如果当前得分不超过 max - 1 时，我们可以选择添加该左括号，也能选择不添加
     * 右括号：如果当前得分大于 0（说明有一个左括号可以与之匹配），我们可以选择添加该右括号，也能选择不添加
     * 使用 Set 进行方案去重，len 记录「爆搜」过程中的最大子串，然后将所有结果集中长度为 len 的子串加入答案
     * 作者：AC_OIer
     * 链接：https://leetcode.cn/problems/remove-invalid-parentheses/solution/yi-fen-zhong-nei-kan-dong-jiang-gua-hao-aya6k/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param _s
     * @return
     */
    public static List<String> removeInvalidParentheses(String _s){
        s = _s;
        n = s.length();
        int l = 0, r = 0;
        for(char c: s.toCharArray()){
            if(c == '('){
                l++;
            }else if(c == ')') r++;
        }
        max = Math.min(l, r);
        dfs(0, "", 0);
        return new ArrayList<>(set);
    }

    private static void dfs(int u, String cur, int score) {
        if(score <0 || score>max) return;
        if(u == n){
            if (score == 0 && cur.length() >= len){
                if (cur.length() > len) set.clear();
                len = cur.length();
                set.add(cur);
            }
            return;
        }
        char c = s.charAt(u);
        if (c == '('){
            dfs(u+1, cur + String.valueOf(c), score + 1);
            dfs(u + 1, cur, score);
        } else if (c == ')'){
            dfs(u+1, cur + String.valueOf(c), score - 1);
            dfs(u+1, cur , score);
        } else {
            dfs(u+1, cur + String.valueOf(c), score);
        }
    }

    public static void main(String[] args) {
        String s = "(a)())()";
        List<String> list = removeInvalidParentheses(s);
        list.forEach(t -> {
            System.out.println("args = " + t);
        });
    }
}
