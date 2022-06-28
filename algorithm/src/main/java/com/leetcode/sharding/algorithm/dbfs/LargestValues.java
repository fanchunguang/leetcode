package com.leetcode.sharding.algorithm.dbfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值。
 * 示例：
 * 输入: root = [1,3,2,5,3,null,9]
 * 输出: [1,3,9]
 */
public class LargestValues {
    public class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) return ans;
        Deque<TreeNode> d = new ArrayDeque<>();
        d.addLast(root);
        while (!d.isEmpty()) {
            int sz = d.size(), max = d.peek().val;
            while (sz-- > 0) {
                TreeNode node = d.pollFirst();
                max = Math.max(max, node.val);
                if (node.left != null) d.addLast(node.left);
                if (node.right != null) d.addLast(node.right);
            }
            ans.add(max);
        }
        return ans;
    }

    public static void main(String[] args) {

    }
}
