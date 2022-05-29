package com.leetcode.sharding.algorithm.knapsack;

/**
 * 这里有 n 个一样的骰子，每个骰子上都有 k 个面，分别标号为 1 到 k 。
 *
 * 给定三个整数 n ,  k 和 target ，返回可能的方式(从总共kn种方式中)滚动骰子的数量，使正面朝上的数字之和等于target。
 *
 * 答案可能很大，你需要对109+ 7 取模。
 * 示例 1：
 *
 * 输入：n = 1, k = 6, target = 3
 * 输出：1
 * 解释：你扔一个有6张脸的骰子。
 * 得到3的和只有一种方法。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/number-of-dice-rolls-with-target-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class NumRollsToTarget {

    int mod = (int)1e9 + 7;
    public int numRollsToTarget(int n, int m, int t){
        int[][] f = new int[n+1][t+1];
        f[0][0] = 1;
        for(int i=0;i<n;i++){
            for(int j=0;j<t;j++){
                for(int k=0;k<m;k++){
                    if(j> k){
                        f[i][j] = (f[i][j] + f[i-1][j-k]) % mod;
                    }
                }
            }
        }
        return f[n][t];
    }
}
