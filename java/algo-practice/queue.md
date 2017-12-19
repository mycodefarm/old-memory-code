# 1.[]()
从上往下打印出二叉树的每个节点，同层节点从左至右打印。
```java
import java.util.*;
/**
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        if(root==null){
            return res;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()){
            TreeNode t = q.poll();
            if(t.left!=null){
                q.offer(t.left);
            }
            if(t.right!=null){
                q.offer(t.right);
            }
            res.add(t.val);
        }
        return res;
    }
}
```
