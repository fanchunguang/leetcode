package com.leetcode.sharding.algorithm.prefixsum;

import java.util.Arrays;

/**
 * 这是 LeetCode 上的 1109. 航班预订统计 ，难度为中等。
 * Tag : 「区间求和问题」、「差分」、「线段树」
 * 这里有 n 个航班，它们分别从 1 到 n 进行编号。
 * 有一份航班预订表 bookings ，表中第 i 条预订记录 bookings[i] = [firsti, lasti, seatsi]
 * 意味着在从 firsti 到 lasti （包含 firsti 和 lasti ）的 每个航班 上预订了 seatsi 个座位。
 * 请你返回一个长度为 n 的数组 answer ，其中 answer[i] 是航班 i 上预订的座位总数。
 * 示例 1：
 * 输入：bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
 * 输出：[10,55,45,25,25]
 * 解释：
 * 航班编号 1 2 3 4 5
 * 预订记录 1 ： 10 10
 * 预订记录 2 ： 20 20
 * 预订记录 3 ： 25 25 25 25
 * 总座位数： 10 55 45 25 25
 * 因此，answer = [10,55,45,25,25]
 * 示例 2：
 * 输入：bookings = [[1,2,10],[2,2,15]], n = 2
 * 输出：[10,25]
 * 解释：
 * 航班编号 1 2
 * 预订记录 1 ： 10 10
 * 预订记录 2 ： 15
 * 总座位数： 10 25
 * 因此，answer = [10,25]
 * 提示：
 * • 1 <= n <= 2 ∗ 104 • 1 <= bookings.length <= 2 ∗ 104 • bookings[i].length == 3
 * • 1 <= firsti <= lasti <= n
 * • 1 <= seatsi <= 10
 */
public class CorpFlightBookings {
    /**
     * 各类区间求和问题（加粗字体为最佳方案）：
     *      数组不变，区间查询：前缀和,树状数组,线段树
     *      数组单点修改，区间查询：=树状数组,线段树
     *      数组区间修改，单点查询：差分，线段树
     *      数组区间修改，区间查询：线段树
     *  注意：上述总结是对于一般性而言的（能直接解决的），对标的是模板问题
     *  但存在经过一些"额外"操作对问题进行转化，从而使用别的方案求解的情况
     *  例如某些问题，我们可以先对原数组进行差分，然后使用树状数组，也能解决
     *  区间修改问题。
     *
     */

    /**
     * 差分 差分可以看做是求前缀和的逆向过程
     * 对于一个将区间[l,r]整体增加一个值v操作，我们可以对差分数组c的影响看成两部分：
     *  对c[l]+=v:由于差分是前缀和的逆向过程，这个操作对于将来的查询而言，带来的影
     *      响是对于所有的下标大于等于l的位置都增加了值v;
     *  对c[r+1]-=v:由于我们期望只对[l,r]产生影响，因此需要对下标大于r的位置进行
     *      减值操作，从而抵消“影响”
     *  对于最后的构造答案，可看做是对每个下标做“单点查询”操作，只需要对差分数组求
     *  前缀和即可
     *  时间复杂度O(m+n)
     * @param bookings
     * @param n
     * @return
     */
    public static int[] corpFlightBookings(int[][] bookings, int n){
        int[] c = new int[n+1];
        for (int[] bo:bookings) {
            int l = bo[0] -1 ,r = bo[1]-1, v=bo[2];
            c[l] +=v;
            c[r+1] -=v;
        }
        int[] ans = new int[n];
        ans[0] = c[0];
        for (int i=1;i<n;i++) {
            ans[i] = ans[i-1] + c[i];
        }
        return ans;
    }

    /**
     * 线段树
     * 几乎所有的区间求和问题都可以用线段树解决，
     * @param bookings
     * @param n
     * @return
     */
    static int N = 20009;
    static Node[] tr = new Node[N * 4];

    public static int[] corpFlightBookingsTree(int[][] bookings, int n){
        build(1, 1, n);
        for (int[] bo:bookings) {
            update(1, bo[0], bo[1], bo[2]);
        }
        int[] ans = new int[n];
        for (int i=0; i<n; i++) {
            ans[i] = query(1, i+1, i+1);
        }
        return ans;
    }

    static class Node{
        int l, r, v, add;
        Node(int _l, int _r){
            this.l = _l;
            this.r = _r;
        }
    }

    static void pushdown(int u){
        int add = tr[u].add;
        tr[u << 1].v += add;
        tr[u << 1].add += add;
        tr[u << 1 | 1].v += add;
        tr[u << 1 | 1].add += add;
        tr[u].add = 0;
    }

    static void pushup(int u){
        tr[u].v = tr[u<<1].v + tr[u << 1 | 1].v;
    }

    static void update(int u, int l, int r, int v){
        if (l <= tr[u].l && tr[u].r <= r) {
            tr[u].v +=v ;
            tr[u].add +=v;
        }else {
            pushdown(u);
            int mid = tr[u].l + tr[u].r >>1;
            if (l<=mid) update(u<<1, l, r, v);
            if (r > mid) update(u<<1 | 1, l, r, v);
            pushup(u);
        }
    }

    static int query(int u, int l, int r){
        if (l <= tr[u].l && tr[u].r <=r) {
            return tr[u].v;
        }else {
            pushdown(u);
            int mid = tr[u].l + tr[u].v >> 1;
            int ans = 0;
            if (l <= mid)
                ans+=query(u<<1, l, r);
            if (r > mid)
                ans +=query(u<<1 |1, l, r);
            return ans;
        }
    }

    /**
     * 构建线段树
     * @param u
     * @param l
     * @param r
     */
    static void build(int u, int l, int r){
        tr[u] = new Node(l,r);
        if (l != r){
            int mid = l+r >> 1;
            build(u << 1, l, mid);
            build(u << 1 | 1, mid+1, r);
        }
    }

    public static void main(String[] args) {
        int[][] bookings = new int[][]{{1,2,10},{2,3,20},{2,5,25}};
        corpFlightBookings(bookings, 5);
        int n = 2;
        System.out.println(n << 1);
        System.out.println(n << 1 | 1);
    }
}
