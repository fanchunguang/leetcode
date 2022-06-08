package com.leetcode.sharding.algorithm.prefixsum;

/**
 * 给你一个数组 nums ，请你完成两类查询。
 *
 * 其中一类查询要求 更新 数组nums下标对应的值
 * 另一类查询要求返回数组nums中索引left和索引right之间（包含）的nums元素的 和，其中left <= right
 * 实现 NumArray 类：
 *
 * NumArray(int[] nums) 用整数数组 nums 初始化对象
 * void update(int index, int val) 将 nums[index] 的值 更新 为 val
 * int sumRange(int left, int right) 返回数组nums中索引left和索引right之间（包含）的nums元素的 和（即，nums[left] + nums[left + 1], ..., nums[right]）
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/range-sum-query-mutable
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 这是一道很经典的题目，通常还能拓展出一大类问题。
 *
 * 针对不同的题目，我们有不同的方案可以选择（假设我们有一个数组）：
 *
 * 数组不变，求区间和：「前缀和」、「树状数组」、「线段树」
 * 多次修改某个数（单点），求区间和：「树状数组」、「线段树」
 * 多次修改某个区间，输出最终结果：「差分」
 * 多次修改某个区间，求区间和：「线段树」、「树状数组」（看修改区间范围大小）
 * 多次将某个区间变成同一个数，求区间和：「线段树」、「树状数组」（看修改区间范围大小）
 * 这样看来，「线段树」能解决的问题是最多的，那我们是不是无论什么情况都写「线段树」呢？
 *
 * 答案并不是，而且恰好相反，只有在我们遇到第 4 类问题，不得不写「线段树」的时候，我们才考虑线段树。
 * 因为「线段树」代码很长，而且常数很大，实际表现不算很好。我们只有在不得不用的时候才考虑「线段树」。
 *
 * 总结一下，我们应该按这样的优先级进行考虑：
 *
 * 简单求区间和，用「前缀和」
 * 多次将某个区间变成同一个数，用「线段树」
 * 其他情况，用「树状数组」
 *
 * 作者：AC_OIer
 * 链接：https://leetcode.cn/problems/range-sum-query-mutable/solution/guan-yu-ge-lei-qu-jian-he-wen-ti-ru-he-x-41hv/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class NumArray {

    /**
     * 树状数组模板
     */
    int[] tree;
    int lowbit(int x) {
        return x & -x;
    }

    /**
     * 查询前缀和的方法
     * @param x
     * @return
     */
    int query(int x){
        int ans = 0;
        for(int i=x; i>0; i-=lowbit(x)){
            ans += tree[i];
        }
        return ans;
    }

    /**
     * 在树状数组 x 位置中增加u
     * @param x
     * @param u
     */
    void add(int x, int u){
        for(int i=x;i<=n; i+=lowbit(i)){
            tree[i] += u;
        }
    }

    int n;
    int[] nums;
    public NumArray(int[] nums){
        this.nums = nums;
        n = nums.length;
        tree = new int[n+1];
        //初始化「树状数组」，要默认数组是从 1 开始
        for(int i=0; i<n; i++){
            add(i+1, nums[i]);
        }
    }
    //使用树状数组
    public void update(int index, int val){
        // 原来的值是nums[index],要使得修改为val，需要增加 val - nums[index]
        add(index +1, val - nums[index]);
        nums[index] = val;
    }
    
    public int sumRange(int left, int right){
        return query(right+1) - query(left);
    }

    public static void main(String[] args) {

    }
}
