package com.leetcode.sharding.algorithm.prefixsum;

/**
 * 给你一个二维整数数组ranges和两个整数left和right。每个ranges[i] = [starti, endi]表示一个从starti到endi的闭区间。
 *
 * 如果闭区间[left, right]内每个整数都被ranges中至少一个区间覆盖，那么请你返回true，否则返回false。
 *
 * 已知区间 ranges[i] = [starti, endi] ，如果整数 x 满足 starti <= x <= endi，那么我们称整数x被覆盖了。
 *
 *
 * 示例 1：
 *
 * 输入：ranges = [[1,2],[3,4],[5,6]], left = 2, right = 5
 * 输出：true
 * 解释：2 到 5 的每个整数都被覆盖了：
 * - 2 被第一个区间覆盖。
 * - 3 和 4 被第二个区间覆盖。
 * - 5 被第三个区间覆盖。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/check-if-all-the-integers-in-a-range-are-covered
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Covered {

    public boolean isCovered(int[][] rs, int l, int r) {
        for (int i = l; i <= r; i++) {
            boolean ok = false;
            for (int[] cur : rs) {
                int a = cur[0], b = cur[1];
                if (a <= i && i <= b) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                return false;
            }
        }
        return true;
    }

    /**
     * 树状数组
     * 针对此题，可以有一个很有意思的拓展，将本题难度提升到【中等】甚至是【困难】。
     *
     * 将查询 [left, right][left,right] 修改为「四元查询数组」querysquerys，每个 querys[i]querys[i] 包含四个指标 (a,b,l,r)(a,b,l,r)：代表询问 [l, r][l,r] 中的每个数是否在 rangerange 中 [a, b][a,b] 的闭区间所覆盖过。
     *
     * 如果进行这样的拓展的话，那么我们需要使用「持久化树状数组」或者「主席树」来配合「容斥原理」来做。
     *
     * 基本思想都是使用 range[0,b]range[0,b] 的计数情况减去 range[0, a-1]range[0,a−1] 的计数情况来得出 [a, b][a,b] 的计数情况。
     *
     * 回到本题，由于数据范围很小，只有 5050，我们可以使用「树状数组」进行求解：
     *
     * void add(int x, int u)：对于数值 xx 出现次数进行 +u+u 操作；
     * int query(int x)：查询某个满足 <= x<=x 的数值的个数。
     * 那么显然，如果我们需要查询一个数值 xx 是否出现过，可以通过查询 cnt = query(x) - query(x - 1)cnt=query(x)−query(x−1) 来得知。
     *
     * 作者：AC_OIer
     * 链接：https://leetcode.cn/problems/check-if-all-the-integers-in-a-range-are-covered/solution/gong-shui-san-xie-yi-ti-shuang-jie-mo-ni-j83x/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param rs
     * @param l
     * @param r
     * @return
     */
    int n = 55;
    int[] tr = new int[n];
    int lowbit(int x){
        return x & -x;
    }

    /**
     * 对数值x出现次数进行+u操作
     * @param x
     * @param u
     */
    void add(int x, int u){
        for(int i=x; i< n; i+= lowbit(i)){
            tr[i] +=u;
        }
    }

    /**
     * 查询某个满足 <=x
     * @param x
     * @return
     */
    int query(int x) {
        int ans = 0;
        for(int i=x;i>0; i-=lowbit(i)){
            ans += tr[i];
        }
        return ans;
    }
    public  boolean isCovered1(int[][] rs, int l, int r) {
        for(int[] cur: rs){
            int a = cur[0], b = cur[1];
            for(int i=a;i<b;i++){
                add(i,1);
            }
        }
        for(int i=l;i<r;i++){
            int cnt = query(i) - query(i-1);
            if (cnt == 0) {
                return true;
            }
        }
        return true;
    }
    // 代表 [l, r] 区间有 cnt 个数被覆盖
    class Node {
        int l, r, cnt;
        Node (int _l, int _r, int _cnt) {
            l = _l; r = _r; cnt = _cnt;
        }
    }
    int N = 55;
    Node[] tr1 = new Node[N * 4];
    void pushup(int u) {
        tr1[u].cnt = tr1[u << 1].cnt + tr1[u << 1 | 1].cnt;
    }
    void build(int u, int l, int r) {
        if (l == r) {
            tr1[u] = new Node(l, r, 0);
        } else {
            tr1[u] = new Node(l, r, 0);
            int mid = l + r >> 1;
            build(u << 1, l, mid);
            build(u << 1 | 1, mid + 1, r);
            pushup(u);
        }
    }
    // 从 tr 数组的下标 u 开始，在数值 x 的位置进行标记
    void update(int u, int x) {
        if (tr1[u].l == x && tr1[u].r == x) {
            tr1[u].cnt = 1;
        } else {
            int mid = tr1[u].l + tr1[u].r >> 1;
            if (x <= mid) update(u << 1, x);
            else update(u << 1 | 1, x);
            pushup(u);
        }
    }
    // 从 tr 数组的下标 u 开始，查询 [l,r] 范围内有多少个数值被标记
    int query(int u, int l, int r) {
        if (l <= tr1[u].l && tr1[u].r <= r) return tr1[u].cnt;
        int mid = tr1[u].l + tr1[u].r >> 1;
        int ans = 0;
        if (l <= mid) ans += query(u << 1, l, r);
        if (r > mid) ans += query(u << 1 | 1, l, r);
        return ans;
    }
    /**
     * 线段树（不含“懒标记”）
     * 更加进阶的做法是使用「线段树」来做，与「树状数组（优化）」解法一样，线段树配合持久化也可以用于求解「在线」问题。
     *
     * 与主要解决「单点修改 & 区间查询」的树状数组不同，线段树能够解决绝大多数「区间修改（区间修改/单点修改）& 区间查询」问题。
     *
     * 对于本题，由于数据范围只有 5555，因此我们可以使用与「树状数组（优化）」解法相同的思路，实现一个不包含“懒标记”的线段树来做（仅支持单点修改 & 区间查询）
     *
     * 作者：AC_OIer
     * 链接：https://leetcode.cn/problems/check-if-all-the-integers-in-a-range-are-covered/solution/gong-shui-san-xie-yi-ti-shuang-jie-mo-ni-j83x/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param rs
     * @param l
     * @param r
     * @return
     */
    public boolean isCovered2(int[][] rs, int l, int r) {
        build(1, 1, N);
        for (int[] cur : rs) {
            int a = cur[0], b = cur[1];
            for (int i = a; i <= b; i++) {
                update(1, i);
            }
        }
        int tot = r - l + 1 , cnt = query(1, l, r);
        return tot == cnt;
    }
}
