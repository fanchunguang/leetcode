package com.leetcode.sharding.algorithm.dbfs;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个仅包含数字 0-9 的字符串 num 和一个目标值整数 target ，在 num 的数字之间添加 二元 运算符（不是一元）+、- 或 * ，返回 所有 能够得到 target 的表达式。
 *
 * 注意，返回表达式中的操作数 不应该 包含前导零。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: num = "123", target = 6
 * 输出: ["1+2+3", "1*2*3"]
 * 解释: “1*2*3” 和 “1+2+3” 的值都是6。
 * 示例 2:
 *
 * 输入: num = "232", target = 8
 * 输出: ["2*3+2", "2+3*2"]
 * 解释: “2*3+2” 和 “2+3*2” 的值都是8。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/expression-add-operators
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class AddOperators {
    /**
     * 最开始的想法是先使用 DFS 搜出来所有的表达式，然后套用 （题解）227. 基本计算器 II 方案，计算所有表达式的结果，并将计算结果为
     * target 的表达式加到结果集。
     *
     * 假设原字符串
     * num 的长度为
     * n，由于每个位置之间存在四种插入决策（不插入符号、+、- 和 *），共有
     * n−1 个位置需要决策，因此搜索所有表达式的复杂度为
     * O(4n−1)；同时需要对所有的表达式执行计算，复杂度为
     * O(n)，整体复杂度为
     * O(n∗4n−1)。
     *
     * 添加运算符后的表达式长度不会超过20
     * 20，因此总的计算量应该是在
     * 107
     *   以内，但可能是因为常数问题超时了（各种优化双栈操作也还是 TLE，在这上面浪费了好多时间 QWQ）。
     * 因此，我们需要考虑在搜索过程中进行计算，以避免使用 （题解）227. 基本计算器 II 这种常数较大的计算方式。
     *
     * 我们考虑如果只有 + 和 - 的话，可以很容易将运算和回溯搜索所有表达进行结合。但当存在 * 时，由于存在运算优先级的问题，我们需要记录形如 a + b * c 中的乘法部分。
     *
     * 实现上，除了记录当前决策到原串
     * num 的哪一位 u，以及当前的运算结果
     * cur 以外，还需要额外记录最后一次的计算结果
     * prev，然后在决策表达式中的第 k 个部分时，对本次添加的运算符合做分情况讨论：
     *
     * 如果本次添加的 + 操作，且第 k 项的值是 next：那么直接使用 cur+next 来更新 cur，同时 next 作为下一次的 prev；
     * 如果本次添加的 - 操作，且第 k 项的值是 next：同理，那么直接使用 cur−next 来更新 cur，同时 −next 作为下一次的 prev；
     * 如果本次添加的 * 操作，且第 k 项的值是 next：此时需要考虑运算符的优先级问题，由于本次的 next 是与上一次的操作数 prev 执行乘法，而 cur 已经累加了
     * prev 的影响，因此需要先减去 prev，再加上 prev∗next，以此来更新 cur，同时prev∗next 也作为下一次的prev。
     * 一些细节：需要注意前导零（
     * 0 单独作为一位是被允许的，但是多位且首部为
     * 0 是不允许的）以及 + 和 - 不作为一元运算符（运算符不能出现在表达式的首部）的情况。
     *
     * 作者：AC_OIer
     * 链接：https://leetcode.cn/problems/expression-add-operators/solution/gong-shui-san-xie-hui-su-suan-fa-yun-yon-nl9z/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param num
     * @param target
     * @return
     */
    static List<String> ans = new ArrayList<>();
    static String s;
    static int n, t;
    public static List<String> addOperators(String num, int target) {
        s = num;
        n = s.length();
        t = target;
        dfs(0, 0, 0, "");
        return ans;
    }

    private static void dfs(int u, long prev, long cur, String ss){
        if (u == n){
            if (cur == t){
                ans.add(ss);
            }
            return;
        }
        for (int i=u; i<n; i++){
            if(i!=u && s.charAt(u) == '0') break;
            long next = Long.parseLong(s.substring(u,i+1));
            if(u==0){
                dfs(i+1, next, next, next + "");
            }else{
                dfs(i+1, next, cur+next, ss + "+" + next);
                dfs(i+1, -next, cur -  next, ss + "-" + next);
                long x = prev * next;
                dfs(i+1, x, cur - prev + x, ss + "*" + next);
            }
        }
    }

    public static void main(String[] args) {
        addOperators("123", 6);
    }
}
