package com.leetcode.sharding.algorithm.prefixsum;

import java.util.Arrays;
import java.util.Random;

/**
 * 给定一个由非重叠的轴对齐矩形的数组 rects ，其中 rects[i] = [ai, bi, xi, yi] 表示 (ai, bi) 是第 i 个矩形的左下角点，(xi, yi) 是第 i 个矩形的右上角点。
 * 设计一个算法来随机挑选一个被某一矩形覆盖的整数点。矩形周长上的点也算做是被矩形覆盖。所有满足要求的点必须等概率被返回。
 * 在给定的矩形覆盖的空间内的任何整数点都有可能被返回。
 * 请注意 ，整数点是具有整数坐标的点。
 * 实现 Solution 类:
 *
 * Solution(int[][] rects) 用给定的矩形数组 rects 初始化对象。
 * int[] pick() 返回一个随机的整数点 [u, v] 在给定的矩形所覆盖的空间内。
 *
 * 示例 1：
 * 输入:
 * ["Solution", "pick", "pick", "pick", "pick", "pick"]
 * [[[[-2, -2, 1, 1], [2, 2, 4, 6]]], [], [], [], [], []]
 * 输出:
 * [null, [1, -2], [1, -1], [-1, -2], [-2, -2], [0, 0]]
 *
 * 解释：
 * Solution solution = new Solution([[-2, -2, 1, 1], [2, 2, 4, 6]]);
 * solution.pick(); // 返回 [1, -2]
 * solution.pick(); // 返回 [1, -1]
 * solution.pick(); // 返回 [-1, -2]
 * solution.pick(); // 返回 [-2, -2]
 * solution.pick(); // 返回 [0, 0]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/random-point-in-non-overlapping-rectangles
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    static int[][] rs;
    static int[] sum;
    static int n;
    static Random random = new Random();
    public Solution(int[][] rects){
        rs = rects;
        n = rs.length;
        sum = new int[n+1];
        for(int i=1;i<n;i++){
            sum[i] = sum[i-1] + (rs[i-1][2] - rs[i-1][0] + 1) * (rs[i-1][3] - rs[i-1][1] + 1);
        }
    }

    public int[] pick(){
        int val = random.nextInt(sum[n]) + 1;
        int l = 0,r = n;
        while(l < r){
            int mid = l + r >>1;
            if(sum[mid] > val){
                r = mid;
            }else{
                l = mid + 1;
            }
        }
        int[] cur = rs[r-1];
        int x = random.nextInt(cur[2] - cur[0] + 1);
        int y = random.nextInt(cur[3] - cur[1] + 1);
        return new int[]{x,y};
    }

    public static void main(String[] args) {
        int[][] arr = new int[][]{{-2,-2,1,1},{2,2,4,6}};
        Solution solution = new Solution(arr);
        System.out.println("args = " + solution.pick());;
    }
}
