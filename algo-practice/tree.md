# 1.[minimum-depth-of-binary-tree](https://leetcode.com/problems/minimum-depth-of-binary-tree/description/)
Given a binary tree, find its minimum depth.The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.

code1:
```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public int run(TreeNode root) {
        if(root==null){
            return 0;
        }
        if(root.left==null){
            return run(root.right)+1;
        }
        if(root.right==null){
            return run(root.left)+1;
        }
        return Math.min(run(root.left),run(root.right))+1;
    }
}
```
code2:
```java
public class Solution {
    public int run(TreeNode root) {
        if(root==null){
            return 0;
        }
        int left = run(root.left);
        int right = run(root.right);
        if(left==0 || right==0){
            return left+right+1;
        }
        return Math.min(left,right)+1;
    }
}
```
code3:
```java
import java.util.*;
public class Solution {
    public int run(TreeNode root) {
        if(root==null){
            return 0;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level = 0;
        while(!q.isEmpty()){
            level++;
            int n = q.size();
            boolean isLeaf = false;
            while(n-->0){
                TreeNode node = q.poll();                
                if(node.left!=null){
                    q.offer(node.left);
                }
                if(node.right!=null){
                    q.offer(node.right);
                }
                if(node.left==null&&node.right==null){
                    isLeaf = true;
                    break;
                }
            }
            if(isLeaf){
                break;
            }
        }
        return level;
    }
}
```

# 2.[binary-tree-postorder-traversal](https://leetcode.com/problems/binary-tree-postorder-traversal/description/)
Given a binary tree, return the postorder traversal of its nodes' values.
For example:
Given binary tree{1,#,2,3},
   1
    \
     2
    /
   3

return[3,2,1].
Note: Recursive solution is trivial, could you do it iteratively?

```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
/* 这个解法可能是最佳实践，思路清晰，易于理解。
 * 核心思想是用栈做辅助空间，先从根往左一直入栈，直到为空，然后判断栈顶元素的右孩子，如果不为空且未被访问过，
 * 则从它开始重复左孩子入栈的过程；否则说明此时栈顶为要访问的节点（因为左右孩子都是要么为空要么已访问过了），
 * 出栈然后访问即可，接下来再判断栈顶元素的右孩子...直到栈空。
 */
import java.util.Stack;
import java.util.ArrayList;
public class Solution {
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        TreeNode p = root, r = null;        //P记录当前节点，r用来记录上一次访问的节点
        Stack<TreeNode> s = new Stack<TreeNode>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        while(p != null || !s.isEmpty()) {
            if(p != null) {     //左孩子一直入栈，直到左孩子为空
                s.push(p);
                p = p.left;
            } else {
                p = s.peek();
                p = p.right;
                if(p != null && p != r) {   //如果栈顶元素的右孩子不为空，且未被访问过
                    s.push(p);              //则右孩子进栈，然后重复左孩子一直进栈直到为空的过程
                    p = p.left;
                } else {
                    p = s.pop();            //否则出栈，访问，r记录刚刚访问的节点
                    list.add(p.val);
                    r = p;
                    p = null;
                }
            }
        }
        return list;
    }
}
```
# 3.[binary-tree-preorder-traversal](https://leetcode.com/problems/binary-tree-preorder-traversal/description/)
Given a binary tree, return the preorder traversal of its nodes' values.
For example:
Given binary tree{1,#,2,3},
   1
    \
     2
    /
   3

return[1,2,3].
Note: Recursive solution is trivial, could you do it iteratively?

```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
import java.util.*;
public class Solution {
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> st = new Stack<>();
        ArrayList<Integer> re = new ArrayList<>();
        if(root==null){
            return re;
        }
        st.push(root);
        while(!st.empty()){
            TreeNode t = st.pop();
            re.add(t.val);
            if(t.right!=null){
                st.push(t.right);
            }
            if(t.left!=null){
                st.push(t.left);
            }
        }
        return re;
    }
}
```
# 4.[binary-tree-maximum-path-sum](https://leetcode.com/problems/binary-tree-maximum-path-sum/description/)
Given a binary tree, find the maximum path sum.
The path may start and end at any node in the tree.
For example:
Given the below binary tree,
       1
      / \
     2   3

Return6.

```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    int max = 0;
    
    public int maxPathSum(TreeNode root) {
        if(root==null){
            return 0;
        }
        max = Integer.MIN_VALUE;
        maxPathRecursive(root);
        return max;
    }
    
    public int maxPathRecursive(TreeNode node){
        if(node==null){
            return 0;
        }
        int left = maxPathRecursive(node.left);
        int right = maxPathRecursive(node.right);
        max = Math.max(max,left+right+node.val);
        return Math.max(Math.max(left,right)+node.val,0);//只返回一条路径的值
    }
}
```
# 5.[same-tree](https://leetcode.com/problems/same-tree/description/)
Given two binary trees, write a function to check if they are equal or not.
Two binary trees are considered equal if they are structurally identical and the nodes have the same value.

```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null||q==null){
            return q==p;
        }
        if(p!=null&&q!=null&&p.val==q.val){
            boolean left = isSameTree(p.left,q.left);
            boolean right = isSameTree(p.right,q.right);
            return left && right;
        }
        return false;
    }
}
```
# 6.[recover-binary-search-tree](https://leetcode.com/problems/recover-binary-search-tree/description/)
Two elements of a binary search tree (BST) are swapped by mistake.
Recover the tree without changing its structure.
Note: 
A solution using O(n ) space is pretty straight forward. Could you devise a constant space solution?

confused what"{1,#,2,3}"means? > read more on how binary tree is serialized on OJ.

OJ's Binary Tree Serialization:
The serialization of a binary tree follows a level order traversal, where '#' signifies a path terminator where no node exists below.
Here's an example:
   1
  / \
 2   3
    /
   4
    \
     5
The above binary tree is serialized as"{1,2,3,#,#,4,#,#,5}".

```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    TreeNode pre,first,second;//first和second记录2个错误的节点
    public void recoverTree(TreeNode root) {
        pre = first = second = null;
        inorderTraverse(root);
        if(first!=null&&second!=null){
            int t = first.val;
            first.val = second.val;
            second.val = t;
        }
    }
    
    public void inorderTraverse(TreeNode root){
        if(root==null){
            return;
        }
        inorderTraverse(root.left);
        if(pre!=null&&pre.val>root.val){
            if(first==null){
                first = pre;
            }
            second = root;
        }
        pre = root;
        inorderTraverse(root.right);
    }
}
```
# 7.[validate-binary-search-tree](https://leetcode.com/problems/validate-binary-search-tree/description/)
Given a binary tree, determine if it is a valid binary search tree (BST).
Assume a BST is defined as follows:
The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.

confused what"{1,#,2,3}"means? > read more on how binary tree is serialized on OJ.

OJ's Binary Tree Serialization:
The serialization of a binary tree follows a level order traversal, where '#' signifies a path terminator where no node exists below.
Here's an example:
   1
  / \
 2   3
    /
   4
    \
     5
The above binary tree is serialized as"{1,2,3,#,#,4,#,#,5}".
```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    TreeNode pre;
    boolean ok;
    public boolean isValidBST(TreeNode root) {
        ok = true;
        inorderTraverse(root);
        return ok;
    }
    //利用中序遍历是有序的性质
    public void inorderTraverse(TreeNode root){
        if(root==null){
            return;
        }
        inorderTraverse(root.left);
        if(pre!=null&&pre.val>=root.val){
            ok = false;
        }
        pre = root;
        inorderTraverse(root.right);
    }
}
```
# 8.[unique-binary-search-trees](https://leetcode.com/problems/unique-binary-search-trees/description/)
Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
For example,
Given n = 3, there are a total of 5 unique BST's.
   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
```java
public class Solution {
    public int numTrees(int n) {
        //dp[i]表示共有i个节点时有几种情况
        int[]dp = new int[n+1];
        //空树和一个节点
        dp[0] = dp[1] = 1;
        //表示i作为根节点
        for(int i=2;i<=n;i++){
            //左子树有j个节点，右子树有i-j-1个节点
            for(int j=0;j<i;j++){
                dp[i] += dp[j]*dp[i-j-1];
            }
        }
        return dp[n];
    }
}
```
# 9.[maximum-depth-of-binary-tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/description/)
Given a binary tree, find its maximum depth.
The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public int maxDepth(TreeNode root) {
        if(root==null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }
}
```
# 10.[sum-root-to-leaf-numbers](https://leetcode.com/problems/sum-root-to-leaf-numbers/description/)
Given a binary tree containing digits from0-9only, each root-to-leaf path could represent a number.
An example is the root-to-leaf path1->2->3which represents the number123.
Find the total sum of all root-to-leaf numbers.
For example,
    1
   / \
  2   3

The root-to-leaf path1->2represents the number12.
The root-to-leaf path1->3represents the number13.
Return the sum = 12 + 13 =25.
```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {

    public int sumNumbers(TreeNode root) {
        int sum = 0;
        return getSum(root,sum);
    }
    //前序遍历是关键
    public int getSum(TreeNode root,int sum){
        if(root==null){
            return 0;
        }
        sum = sum * 10 + root.val;
        TreeNode left = root.left;
        TreeNode right = root.right;
        if(left==null&&right==null){
            return sum;
        }
        return getSum(left,sum)+getSum(right,sum);
    }
}
```
# 11.[populating-next-right-pointers-in-each-node](https://leetcode.com/problems/populating-next-right-pointers-in-each-node/description/)
Given a binary tree
    struct TreeLinkNode {
      TreeLinkNode *left;
      TreeLinkNode *right;
      TreeLinkNode *next;
    }

Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set toNULL.
Initially, all next pointers are set toNULL.
Note:
You may only use constant extra space.
You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two children).

For example,
Given the following perfect binary tree,
         1
       /  \
      2    3
     / \  / \
    4  5  6  7

After calling your function, the tree should look like:
         1 -> NULL
       /  \
      2 -> 3 -> NULL
     / \  / \
    4->5->6->7 -> NULL

code1:递归，虽然不满足常量空间，但思想很重要
```java
/**
 * Definition for binary tree with next pointer.
 * public class TreeLinkNode {
 *     int val;
 *     TreeLinkNode left, right, next;
 *     TreeLinkNode(int x) { val = x; }
 * }
 */
public class Solution {
    public void connect(TreeLinkNode root) {
        if(root==null){
            return;
        }
        //左右节点相连
        if(root.left!=null&&root.right!=null){
            root.left.next = root.right;
        }
        //左右子树相连
        if(root.next!=null&&root.right!=null){
            root.right.next = root.next.left;
        }
        connect(root.left);
        connect(root.right);
    }
}
```
code2:
```java
    public void connect(TreeLinkNode root) {
    	if(root==null){
            return;
        }
        TreeLinkNode left,tmp;//left指向每一层的最左边的节点
        //root.next = null;//default
        left = root;
        while(left.left!=null){
            tmp = left;
            while(tmp!=null){
                //左右节点
                tmp.left.next = tmp.right;
                //左右子树
                if(tmp.next!=null&&tmp.right!=null){
                    tmp.right.next = tmp.next.left;
                }
                tmp = tmp.next;//移到兄弟节点
            }
            left = left.left;
        }
    }
```
# 12.[construct-binary-tree-from-preorder-and-inorder-traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/)
Given preorder and inorder traversal of a tree, construct the binary tree.
Note: 
You may assume that duplicates do not exist in the tree.

/*
     * 假设树的先序遍历是12453687，中序遍历是42516837。
     * 这里最重要的一点就是先序遍历可以提供根的所在，
     * 而根据中序遍历的性质知道根的所在就可以将序列分为左右子树。
     * 比如上述例子，我们知道1是根，所以根据中序遍历的结果425是左子树，而6837就是右子树。
     * 接下来根据切出来的左右子树的长度又可以在先序便利中确定左右子树对应的子序列
     * （先序遍历也是先左子树后右子树）。
     * 根据这个流程，左子树的先序遍历和中序遍历分别是245和425，
     * 右子树的先序遍历和中序遍历则是3687和6837，我们重复以上方法，可以继续找到根和左右子树，
     * 直到剩下一个元素。
     */
```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(preorder,inorder,0,preorder.length-1,0,inorder.length-1);
    }
    
    public TreeNode build(int[]preorder,int[]inorder,int pl,int pr,int il,int ir){
        if(pl>pr){
            return null;
        }
        TreeNode root = new TreeNode(preorder[pl]);
        
        int rootIndex = il;
        for(int i=il;i<=ir;i++){
            if(preorder[pl]==inorder[i]){
                rootIndex = i;
                break;
            }
        }
        int leftLen = rootIndex - il;
        root.left = build(preorder,inorder,pl+1,pl+leftLen,il,il+leftLen-1);
        root.right = build(preorder,inorder,pl+leftLen+1,pr,rootIndex+1,ir);
        return root;
    }
}
```
# 13.[construct-binary-tree-from-inorder-and-postorder-traversal](https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/)
Given inorder and postorder traversal of a tree, construct the binary tree.
Note: 
You may assume that duplicates do not exist in the tree.

思想同上，只不过这次根节点是在最后
```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return build(postorder,inorder,0,postorder.length-1,0,inorder.length-1);
    }
    
    public TreeNode build(int[]postorder,int[]inorder,int pl,int pr,int il,int ir){
        if(pl>pr){
            return null;
        }
        TreeNode root = new TreeNode(postorder[pr]);
        int rootIndex = il;
        for(int i=il;i<=ir;i++){
            if(postorder[pr]==inorder[i]){
                rootIndex = i;
                break;
            }
        }
        int rightLen = ir - rootIndex;
        root.left = build(postorder,inorder,pl,pr-rightLen-1,il,rootIndex-1);
        root.right = build(postorder,inorder,pr-rightLen,pr-1,rootIndex+1,ir);
        return root;
    }
}
```
# 14.[symmetric-tree](https://leetcode.com/problems/symmetric-tree/description/)
Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
For example, this binary tree is symmetric:
    1
   / \
  2   2
 / \ / \
3  4 4  3

But the following is not:
    1
   / \
  2   2
   \   \
   3    3

Note: 
Bonus points if you could solve it both recursively and iteratively.

递归法：
```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public boolean isSymmetric(TreeNode root) {
        return doubleEqual(root,root);
    }
    
    public boolean doubleEqual(TreeNode left,TreeNode right){
        if(left==null&&right==null){
            return true;
        }
        if(left==null||right==null){
            return false;
        }
        if(left.val!=right.val){
            return false;
        }
        return doubleEqual(left.left,right.right)&&doubleEqual(left.right,right.left);
    }
}
```
# 15.[problems/path-sum](https://leetcode.com/problems/path-sum/description/)
Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
For example:
Given the below binary tree andsum = 22,
              5
             / \
            4   8
           /   / \
          11  13  4
         /  \      \
        7    2      1
return true, as there exist a root-to-leaf path5->4->11->2which sum is 22.

```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {

    public boolean hasPathSum(TreeNode root, int sum) {
        if(root==null){
            return false;
        }
        if(root.left==null&&root.right==null&&sum-root.val==0){
            return true;
        }
        return hasPathSum(root.left,sum-root.val)||hasPathSum(root.right,sum-root.val);
    }
}
```
# 16.[path-sum-ii](https://leetcode.com/problems/path-sum-ii/description/)
Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
For example:
Given the below binary tree andsum = 22,
              5
             / \
            4   8
           /   / \
          11  13  4
         /  \    / \
        7    2  5   1
return
[
   [5,4,11,2],
   [5,8,4,5]
]

code1:
```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
import java.util.*;
class Solution {
    public List<List<Integer>> re  = null;
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        re = new ArrayList<List<Integer>>();
        sum(root,sum,new ArrayList<Integer>());
        return re;
    }
    
    public void sum(TreeNode root,int sum,ArrayList<Integer> path){
        if(root==null){
            return;
        }
        path.add(root.val);
        if(root.left==null&&root.right==null&&sum-root.val==0){
            re.add(new ArrayList<Integer>(path));
        }
        sum(root.left,sum-root.val,path);
        sum(root.right,sum-root.val,path);
        path.remove(path.size()-1);
    }
}
```
code2:稍微改一下，不需要每次new一个对象
```java
        sum(root.left,sum-root.val,path);
        sum(root.right,sum-root.val,path);
        path.remove(path.size()-1);//在返回上上一步栈时要把这次的元素删除，否则会形成死循环
```
# 17.[binary-tree-level-order-traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/description/)
Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
For example:
Given binary tree{3,9,20,#,#,15,7},
    3
   / \
  9  20
    /  \
   15   7

return its level order traversal as:
[
  [3],
  [9,20],
  [15,7]
]

```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
import java.util.*;
public class Solution {
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> re = new ArrayList<ArrayList<Integer>>();
        if(root==null){
            return re;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()){
            int levelNum = q.size();
            ArrayList<Integer> level = new ArrayList<>();
            while(levelNum-->0){
                TreeNode node = q.poll();
                level.add(node.val);
                if(node.left!=null){
                    q.offer(node.left);
                }
                if(node.right!=null){
                    q.offer(node.right);
                }
            }
            re.add(level);
        }
        return re;
    }
}
```
# 18.[binary-tree-level-order-traversal-ii](https://leetcode.com/problems/binary-tree-level-order-traversal-ii/description/)
Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right, level by level from leaf to root).
For example:
Given binary tree{3,9,20,#,#,15,7},
    3
   / \
  9  20
    /  \
   15   7

return its bottom-up level order traversal as:
[
  [15,7]
  [9,20],
  [3],
]

```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
import java.util.*;
public class Solution {
    public ArrayList<ArrayList<Integer>> levelOrderBottom(TreeNode root) {
        ArrayList<ArrayList<Integer>> re = new ArrayList<ArrayList<Integer>>();
        if(root==null){
            return re;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()){
            int levelNum = q.size();
            ArrayList<Integer> level = new ArrayList<>();
            while(levelNum-->0){
                TreeNode node = q.poll();
                level.add(node.val);
                if(node.left!=null){
                    q.offer(node.left);
                }
                if(node.right!=null){
                    q.offer(node.right);
                }
            }
            re.add(0,level);//在首位添加
        }
        return re;
    }
}
```
# 19.[balanced-binary-tree](https://leetcode.com/problems/balanced-binary-tree/description/)
Given a binary tree, determine if it is height-balanced.
For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

code 1:
```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public boolean isBalanced(TreeNode root) {
        if(root==null){
            return true;
        }
        int leftDepth = depth(root.left);
        int rightDepth = depth(root.right);
        if(Math.abs(leftDepth - rightDepth)<=1){
            if(isBalanced(root.left)&&isBalanced(root.right)){
                return true;
            }
        }
        return false;
    }
    
    public int depth(TreeNode node){
        if(node==null){
            return 0;
        }
        int l = depth(node.left);
        int r = depth(node.right);
        return Math.max(l,r)+1;
    }
}
```
code 2:
```java
public class Solution {
    boolean isBalanced = true;
    public boolean IsBalanced_Solution(TreeNode root) {
        depth(root);
        return isBalanced;
    }
    
    public int depth(TreeNode node){
        if(node==null){
            return 0;
        }
        int left = depth(node.left);
        int right = depth(node.right);
        if(Math.abs(left - right)>1){
            isBalanced = false;
        }
        return Math.max(left,right) + 1;
    }
}
```
# 20.[二叉搜索树与双向链表]()
输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。
```java
方法一：非递归版
解题思路：
1.核心是中序遍历的非递归算法。
2.修改当前遍历节点与前一遍历节点的指针指向。
    import java.util.Stack;
    public TreeNode ConvertBSTToBiList(TreeNode root) {
        if(root==null)
            return null;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode p = root;
        TreeNode pre = null;// 用于保存中序遍历序列的上一节点
        boolean isFirst = true;
        while(p!=null||!stack.isEmpty()){
            while(p!=null){
                stack.push(p);
                p = p.left;
            }
            p = stack.pop();
            if(isFirst){
                root = p;// 将中序遍历序列中的第一个节点记为root
                pre = root;
                isFirst = false;
            }else{
                pre.right = p;
                p.left = pre;
                pre = p;
            }      
            p = p.right;
        }
        return root;
    }
方法二：递归版
解题思路：
1.将左子树构造成双链表，并返回链表头节点。
2.定位至左子树双链表最后一个节点。
3.如果左子树链表不为空的话，将当前root追加到左子树链表。
4.将右子树构造成双链表，并返回链表头节点。
5.如果右子树链表不为空的话，将该链表追加到root节点之后。
6.根据左子树链表是否为空确定返回的节点。
    public TreeNode Convert(TreeNode root) {
        if(root==null)
            return null;
        if(root.left==null&&root.right==null)
            return root;
        // 1.将左子树构造成双链表，并返回链表头节点
        TreeNode left = Convert(root.left);
        TreeNode p = left;
        // 2.定位至左子树双链表最后一个节点
        while(p!=null&&p.right!=null){
            p = p.right;
        }
        // 3.如果左子树链表不为空的话，将当前root追加到左子树链表
        if(left!=null){
            p.right = root;
            root.left = p;
        }
        // 4.将右子树构造成双链表，并返回链表头节点
        TreeNode right = Convert(root.right);
        // 5.如果右子树链表不为空的话，将该链表追加到root节点之后
        if(right!=null){
            right.left = root;
            root.right = right;
        }
        return left!=null?left:root;       
    }
方法三：改进递归版
解题思路：
思路与方法二中的递归版一致，仅对第2点中的定位作了修改，新增一个全局变量记录左子树的最后一个节点。
    // 记录子树链表的最后一个节点，终结点只可能为只含左子树的非叶节点与叶节点
    protected TreeNode leftLast = null;
    public TreeNode Convert(TreeNode root) {
        if(root==null)
            return null;
        if(root.left==null&&root.right==null){
            leftLast = root;// 最后的一个节点可能为最右侧的叶节点
            return root;
        }
        // 1.将左子树构造成双链表，并返回链表头节点
        TreeNode left = Convert(root.left);
        // 3.如果左子树链表不为空的话，将当前root追加到左子树链表
        if(left!=null){
            leftLast.right = root;
            root.left = leftLast;
        }
        leftLast = root;// 当根节点只含左子树时，则该根节点为最后一个节点
        // 4.将右子树构造成双链表，并返回链表头节点
        TreeNode right = Convert(root.right);
        // 5.如果右子树链表不为空的话，将该链表追加到root节点之后
        if(right!=null){
            right.left = root;
            root.right = right;
        }
        return left!=null?left:root;       
    }
```
# 21.[二叉搜索树的第k个结点]()
给定一颗二叉搜索树，请找出其中的第k大的结点。例如， 5 / \ 3 7 /\ /\ 2 4 6 8 中，按结点数值大小顺序第三个结点的值为4。

```java
import java.util.*;
public class Solution {
    TreeNode KthNode(TreeNode pRoot, int k)
    {
        if(pRoot==null){
            return null;
        }
        Stack<TreeNode> s = new Stack<>();
        TreeNode p = pRoot;
        TreeNode res = null;
        int cnt = 0;
        while(p!=null||!s.isEmpty()){
            while(p!=null){
                s.push(p);
                p = p.left;
            }
            TreeNode t = s.pop();
            if(k==++cnt){
                res = t;
            }
            p = t.right;
        }
        return res;
    }
}
```
