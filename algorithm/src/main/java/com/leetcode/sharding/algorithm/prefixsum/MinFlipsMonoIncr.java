package com.leetcode.sharding.algorithm.prefixsum;

/**
 * 将字符串翻转到单调递增
 * 如果一个二进制字符串，是以一些 0（可能没有 0）后面跟着一些 1（也可能没有 1）的形式组成的，那么该字符串是 单调递增 的。
 * 给你一个二进制字符串 s，你可以将任何 0 翻转为 1 或者将 1 翻转为 0 。
 * 返回使 s 单调递增的最小翻转次数。
 * 示例 1：
 *
 * 输入：s = "00110"
 * 输出：1
 * 解释：翻转最后一位得到 00111.
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/flip-string-to-monotone-increasing
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MinFlipsMonoIncr {
    /**
     * 方法一：动态规划
     * 单调递增的字符串满足以下性质：
     *
     * 首个字符是 00 或 11；
     *
     * 其余的每个字符，字符 00 前面的相邻字符一定是 00，字符 11 前面的相邻字符可以是 00 或 11。
     *
     * 当 i > 0i>0 时，如果字符串 ss 的长度为 ii 的前缀即 s[0 .. i - 1]s[0..i−1] 单调递增，且 s[i]s[i] 和 s[i - 1]s[i−1] 也满足上述单调递增的顺序，则长度为 i + 1i+1 的前缀 s[0 .. i]s[0..i] 也单调递增。因此可以使用动态规划计算使字符串 ss 单调递增的最小翻转次数。
     *
     * 由于字符串 ss 的每个位置的字符可以是 00 或 11，因此对于每个位置需要分别计算该位置的字符是 00 和该位置的字符是 11 的情况下的最小翻转次数。
     *
     * 假设字符串 ss 的长度是 nn，对于 0≤i<n，用 dp[i][0] 和 dp[i][1] 分别表示下标 ii 处的字符为 00 和 11 的情况下使得 s[0 .. i]s[0..i] 单调递增的最小翻转次数。
     *
     * 当 i = 0i=0 时，对应长度为 11 的前缀，一定满足单调递增，因此 \textit{dp}[0][0]dp[0][0] 和 \textit{dp}[0][1]dp[0][1] 的值取决于字符 s[i]s[i]。具体而言，\textit{dp}[0][0] = \mathbb{I}(s[0] = \text{`1'})dp[0][0]=I(s[0]=‘1’)，\textit{dp}[0][1] = \mathbb{I}(s[0] = \text{`0'})dp[0][1]=I(s[0]=‘0’)，其中 \mathbb{I}I 为示性函数，当事件成立时示性函数值为 11，当事件不成立时示性函数值为 00。
     *
     * 当 1 \le i < n1≤i<n 时，考虑下标 ii 处的字符。如果下标 ii 处的字符是 00，则只有当下标 i - 1i−1 处的字符是 00 时才符合单调递增；如果下标 ii 处的字符是 11，则下标 i - 1i−1 处的字符是 00 或 11 都符合单调递增，此时为了将翻转次数最小化，应分别考虑下标 i - 1i−1 处的字符是 00 和 11 的情况下需要的翻转次数，取两者的最小值。
     *
     * 在计算 dp[i][0] 和 dp[i][1] 时，还需要根据 s[i] 的值决定下标 i 处的字符是否需要翻转，因此可以得到如下状态转移方程，其中 b{I}I 为示性函数：
     *
     * dp[i][0] = dp[i−1][0]+I(s[i]=‘1’)
     * dp[i][1] = min(dp[i−1][0],dp[i−1][1])+I(s[i]=‘0’)
     *
     * 遍历字符串 ss 计算每个下标处的状态值，遍历结束之后，dp[n−1][0] 和 dp[n−1][1] 中的最小值即为使字符串 ss 单调递增的最小翻转次数。
     * 实现方面有以下两点可以优化。
     *
     * 可以将边界情况定义为 dp[−1][0]=dp[−1][1]=0，则可以从下标 00 开始使用状态转移方程计算状态值。
     *
     * 由于 dp[i] 的值只和 dp[i−1] 有关，因此在计算状态值的过程中只需要维护前一个下标处的状态值，将空间复杂度降低到 O(1)O(1)。
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode.cn/problems/flip-string-to-monotone-increasing/solution/jiang-zi-fu-chuan-fan-zhuan-dao-dan-diao-stjd/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param s
     * @return
     */
    public int minFlipsMonoIncrDp(String s){
        int n = s.length();
        int dp0 = 0, dp1 = 0;
        for (int i=0; i<n; i++) {
            char c = s.charAt(i);
            int dp0New = dp0, dp1New = Math.min(dp0, dp1);
            if(c == '1'){
                dp0New ++;
            }else {
                dp1New ++;
            }
            dp0 = dp0New;
            dp1 = dp1New;
        }
        return Math.min(dp0, dp1);
    }

    /**
     * 更进一步，利用 s 只存在 00 和 11 两种数值，我们知道最后的目标序列形如 000...000、000...111 或 111...111 的形式。
     *
     * 因此我们可以枚举目标序列的 00 和 11 分割点位置 idxidx（分割点是 00 是 11 都可以，不消耗改变次数）。
     *
     * 于是问题转换为：分割点 idxidx 左边有多少个 11（目标序列中分割点左边均为 00，因此 11 的个数为左边的改变次数），分割点 idxidx 的右边有多少个 00（目标序列中分割点右边均为 11，因此 00 的个数为右边的改变次数），两者之和即是分割点为 idxidx 时的总变化次数，所有 idxidx 的总变化次数最小值即是答案。
     *
     * 而求解某个点左边或者右边有多少 11 和 00 可通过「前缀和」进行优化
     *
     * 作者：AC_OIer
     * 链接：https://leetcode.cn/problems/flip-string-to-monotone-increasing/solution/by-ac_oier-h0we/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param s
     * @return
     */
    public static int minFlipsMonoIncrSum(String s){
        char[] cs = s.toCharArray();
        int n = cs.length;
        int ans = n;
        int[] sum = new int[n+10];
        for (int i=1; i<=n; i++) {
            sum[i] = sum[i-1] + (cs[i-1] - '0');
        }
        for (int i=1; i<=n; i++){
            int l = sum[i-1];
            int r = (n - i) - (sum[n] - sum[i]);
            ans = Math.min(ans, l+r);
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "010110";
        minFlipsMonoIncrSum(s);
    }
}
