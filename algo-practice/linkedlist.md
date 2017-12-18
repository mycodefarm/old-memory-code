# 1.[sort-list](https://leetcode.com/problems/sort-list/description/)
Sort a linked list in O(n log n) time using constant space complexity.

经典的将归并排序与链表结合
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode sortList(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode mid = getMiddle(head);
        //从中间断开
        ListNode midNext = mid.next;
        mid.next = null;
        return mergeList(sortList(head),sortList(midNext));
    }
    
    public ListNode getMiddle(ListNode head){
        if(head==null||head.next==null){
            return head;
        }
        ListNode slow = head,fast = head.next;//使用快慢指针的思想
        //fast的速度是slow的两倍，所以fast到尾部时slow到中部
        while(fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    //合并有序单链表
    public ListNode mergeList(ListNode h1,ListNode h2){
        if(h1==null){
            return h2;
        }
        if(h2==null){
            return h1;
        }
        ListNode a = h1.next,b = h2.next,c = null;
        ListNode res = null;//保留头结点结果
        if(h1.val>h2.val){
            res = h2;
            c = h2;
            a = h1;
        }else{
            res = h1;
            c = h1;
            b = h2;
        }
        while(a!=null&&b!=null){
            if(a.val<b.val){
                c.next = a;
                a = a.next;
            }else{
                c.next = b;
                b = b.next;
            }
            c = c.next;
        }
        if(a!=null){
            c.next = a;
        }
        if(b!=null){
            c.next = b;
        }
        return res;
    }
}
```
# 2.[reorder-list](https://leetcode.com/problems/reorder-list/description/)
Given a singly linked list L: L 0→L 1→…→L n-1→L n,
reorder it to: L 0→L n →L 1→L n-1→L 2→L n-2→…
You must do this in-place without altering the nodes' values.
For example,
Given{1,2,3,4}, reorder it to{1,4,2,3}.

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public void reorderList(ListNode head) {
        if(head==null){
            return;
        }
        //找到中间节点，快慢指针
        ListNode slow = head,fast = head.next;
        while(fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
        }        
        //翻转后半部分节点
        ListNode center = slow.next; 
        slow.next = null;//断开
        ListNode t = center,p=null,n=center;       
        while(t!=null){
            n = t;
            t = t.next;
            n.next = p;
            p = n;
        }
        //连接2部分
        t = head;
        p = n;
        while(t!=null&&p!=null){
            n = t;
            t = t.next;
            n.next = p;
            n = p;
            p = p.next;
            n.next = t;
        }
    }
}
```
# 3.[linked-list-cycle](https://leetcode.com/problems/linked-list-cycle/description/)
Given a linked list, determine if it has a cycle in it.
Follow up:
Can you solve it without using extra space?

code1:使用Set：
```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
import java.util.*;
public class Solution {
    public boolean hasCycle(ListNode head) {
        if(head==null){
            return false;
        }
        Set<ListNode> s = new HashSet<>();
        ListNode t = head;
        while(t!=null){
            s.add(t);
            ListNode n = t.next;
            if(s.contains(n)){
               return true; 
            }
            t = t.next;
        }
        return false;
    }
}
```
code2:使用快慢指针
```java
    public boolean hasCycle(ListNode head) {
        if(head==null){
            return false;
        }
    	ListNode slow = head,fast = head.next;
        while(fast!=null&&fast.next!=null){
			if(slow==fast){
                return true;
            }
            fast = fast.next.next;
            slow = slow.next;            
        }
        return false;
    }
```
# 4.[linked-list-cycle-ii](https://leetcode.com/problems/linked-list-cycle-ii/description/)
Given a linked list, return the node where the cycle begins. If there is no cycle, returnnull.
Follow up:
Can you solve it without using extra space?

这个理解需要一点技巧
```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if(head==null){
            return null;
        }
    	ListNode slow = head,fast = head;
        while(fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(slow==fast){
                break;
            }
        }
        if(fast==null||fast.next==null){
            return null;
        }
        slow = head;
        while(slow!=fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
```
# 5.[reverse-linked-list](https://leetcode.com/problems/reverse-linked-list/description/)
Reverse a singly linked list.

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode pre = head,n = head.next,t ;
        pre.next = null;
        while(n!=null){
            t = n.next;
            n.next = pre;
            pre = n;
            n = t;
        }
        return pre;
    }
}
```
# 6.[reverse-linked-list-ii](https://leetcode.com/problems/reverse-linked-list-ii/description/)
Reverse a linked list from position m to n. Do it in-place and in one-pass.
For example:
Given1->2->3->4->5->NULL, m = 2 and n = 4,
return1->4->3->2->5->NULL.
Note: 
Given m, n satisfy the following condition:
1 ≤ m ≤ n ≤ length of list.

```java
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head==null){
            return null;
        }
        ListNode headPre = new ListNode(0);//有可能第一个是head
		int len=n-m;
        headPre.next=head;
        ListNode slow=head;
        ListNode slowPre=headPre;
        while(m-->1){
            slowPre=slow;
            slow=slow.next;
        }
        while(len-->0){
            ListNode temp=slow.next;
            slow.next=temp.next;
            temp.next=slowPre.next;
            slowPre.next=temp;
        } 
        return headPre.next;
    }
```
# 7.[convert-sorted-list-to-binary-search-tree](https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/description/)
Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; next = null; }
 * }
 */
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
    public TreeNode sortedListToBST(ListNode head) {
        if(head==null){
            return null;
        }
        if(head.next==null){
            return new TreeNode(head.val);
        }
        ListNode mid = head,fast = head,preMid=null;
        while(fast!=null&&fast.next!=null){
            preMid = mid;
            mid = mid.next;
            fast = fast.next.next;
        }
        TreeNode root = new TreeNode(mid.val);
        preMid.next = null;//分开
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(mid.next);
        return root;
    }
}
```
# 8.[partition-list](https://leetcode.com/problems/partition-list/description/)
Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
You should preserve the original relative order of the nodes in each of the two partitions.
For example,
Given1->4->3->2->5->2and x = 3,
return1->2->2->4->3->5.

code1:
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode partition(ListNode head, int x) {
        if(head==null){
            return null;
        }
        //找到第一个>=x的节点firstHigh，以后<x的节点就顺序插入firstHigh前
        //考虑到有可能是head，所有准备个preHead节点
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        ListNode firstHigh = head,preHigh = preHead,node=head,preNode=preHead;
        while(node!=null){
            if(node.val>=x){
                firstHigh = node;
                preNode = node;
                node = node.next;
                break;
            }
            preNode = preHigh = node;
            node = node.next;
        }
        while(node!=null){
            if(node.val<x){
                preNode.next = node.next;
                node.next = firstHigh;
                preHigh.next = node;
                preHigh = node;
                node = preNode;
            }
            preNode = node;
            node = node.next;
        }
        return preHead.next;
    }
}
```
code2:思想是分成2个链表，一个小于x，一个大于等于x的然后拼接

# 9.[remove-duplicates-from-sorted-list](https://leetcode.com/problems/remove-duplicates-from-sorted-list/description/)
Given a sorted linked list, delete all duplicates such that each element appear only once.
For example,
Given1->1->2, return1->2.
Given1->1->2->3->3, return1->2->3.

```java
public class Solution {
    //有序的好办
    //注意释放引用，防止内存泄露
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode p=head,q;
        while(p!=null&&p.next!=null){
            q = p.next;
            if(p.val==q.val){
                p.next = q.next;
                q.next = null;
            }else{
                p = p.next;
            }
        }
        return head;
    }
}
```
# 10.[remove-duplicates-from-sorted-list-ii](https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/description/)
Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
For example,
Given1->2->3->3->4->4->5, return1->2->5.
Given1->1->1->2->3, return2->3.

```java
public class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null){
            return head;
        }
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        ListNode node = head,pre = preHead,q = null;
        while(node!=null&&node.next!=null){
            q = node.next;
            if(node.val==q.val){
                while(q!=null&&node.val==q.val){
                    node.next = q.next;
                    q.next = null;
                    q = node.next;
                }
                pre.next = node.next;
                node.next = null;
                node = pre.next;
            }else{
                pre = node;
                node = node.next;
            }
        }
        return preHead.next;
    }
}
```
# 11.[merge-two-sorted-lists](https://leetcode.com/problems/merge-two-sorted-lists/description/)
Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.

```java
public class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode p = head;
        while(l1!=null&&l2!=null){
            if(l1.val<l2.val){
                p.next = l1;
                l1 = l1.next;
            }else{
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }
        if(l1!=null){
            p.next = l1;
        }
        if(l2!=null){
            p.next = l2;
        }
        return head.next;
    }
}
```
# 12.[rotate-list](https://leetcode.com/problems/rotate-list/description/)
Given a list, rotate the list to the right by k places, where k is non-negative.
For example:
Given1->2->3->4->5->NULLand k =2,
return4->5->1->2->3->NULL.
```java
public class Solution {
    public ListNode rotateRight(ListNode head, int n) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        int len = 0;
        ListNode left = preHead, right = preHead;
        while(right.next!=null){
            len++;
            right = right.next;
        }
        int i = len - n%len;
        while(i-->0){
            left = left.next;
        }
        right.next = preHead.next;
        preHead.next = left.next;
        left.next = null;
        return preHead.next;
    }
}
```
# 13.[swap-nodes-in-pairs](https://leetcode.com/problems/swap-nodes-in-pairs/description/)
Given a linked list, swap every two adjacent nodes and return its head.
For example,
Given1->2->3->4, you should return the list as2->1->4->3.
Your algorithm should use only constant space. You may not modify the values in the list, only nodes itself can be changed.

```java
public class Solution {
    public ListNode swapPairs(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        ListNode p=head,n=null,pre = preHead;
        while(p!=null&&p.next!=null){
            n = p.next.next;
            pre.next = p.next;
            p.next.next = p;
            p.next = n;
            pre = p;
            p = n;
        }
        return preHead.next;
    }
}
```