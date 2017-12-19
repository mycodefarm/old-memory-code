# 1.[two-sum-ii-input-array-is-sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/)
Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.

The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.

You may assume that each input would have exactly one solution and you may not use the same element twice.

Input: numbers={2, 7, 11, 15}, target=9
Output: index1=1, index2=2

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int len = numbers.length;
        int low = 0,high = len -1;
        int[] res = new int[2];
        while(low<high){
            int v = numbers[low]+numbers[high];
            if(v==target){
                res[0] = low+1;
                res[1] = high+1;
                break;
            }else if(v<target){
                low++;
            }else{
                high--;
            }
        }
        return res;
    }
}
```
# 2.[和为S的连续正数序列]()
题目描述
小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。但是他并不满足于此,他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck!
输出描述:
输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
```java
import java.util.ArrayList;
public class Solution {
    public ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer> > res = new ArrayList<ArrayList<Integer> >();
        //求和公式和双指针思想
        int left=1,right=2;
        while(left<right){
            int tmpSum = (left + right)*(right - left + 1)/2;
            if(tmpSum==sum){
                ArrayList<Integer> list = new ArrayList<>();
                for(int i=left;i<=right;i++){
                    list.add(i);
                }
                res.add(list);
                left++;
                right++;
            }else if(tmpSum<sum){
                right++;
            }else{
                left++;
            }
        }
        return res;
    }
}
```
# 3.[数字在排序数组中出现的次数]()
统计一个数字在排序数组中出现的次数。
```java
public class Solution {
    public int GetNumberOfK(int [] array , int k) {
       int fk = getFirstK(array,k);
       int lk = getLastK(array,k);
       if(fk!=-1&&lk!=-1){
           return lk-fk+1;
       }
       return 0;
    }
    
    public int getFirstK(int[] a,int k){
        int left = 0,right = a.length-1;
        int mid = (left+right)>>1;
        while(left<=right){
            if(a[mid]>k){
                right = mid-1;
            }else if(a[mid]<k){
                left = mid+1;
            }else if(mid>0&&a[mid-1]==k){
                right = mid-1;
            }else{
                return mid;
            }
            mid = (left+right)>>1;
        }
        return -1;
    }
    
    public int getLastK(int[]a,int k){
        int left = 0,right = a.length-1;
        int mid = (left+right)>>1;
        while(left<=right){
            if(a[mid]>k){
                right = mid-1;
            }else if(a[mid]<k){
                left = mid+1;
            }else if(mid+1<a.length&&a[mid+1]==k){
                left = mid + 1;
            }else{
                return mid;
            }
            mid = (left+right)>>1;
        }
        return -1;
    }
}
```
# 4.[调整数组顺序使奇数位于偶数前面]()
输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。

code1:
```java
/**
 * 1.要想保证原有次序，则只能顺次移动或相邻交换。
 * 2.i从左向右遍历，找到第一个偶数。
 * 3.j从i+1开始向后找，直到找到第一个奇数。
 * 4.将[i,...,j-1]的元素整体后移一位，最后将找到的奇数放入i位置，然后i++。
 * 5.終止條件：j向後遍歷查找失敗。
 */
public void reOrderArray2(int [] a) {
    if(a==null||a.length==0)
        return;
    int i = 0,j;
    while(i<a.length){
        while(i<a.length&&!isEven(a[i]))
            i++;
        j = i+1;
        while(j<a.length&&isEven(a[j]))
            j++;
        if(j<a.length){
            int tmp = a[j];
            for (int j2 = j-1; j2 >=i; j2--) {
                a[j2+1] = a[j2];
            }
            a[i++] = tmp;
        }else{// 查找失敗
            break;
        }
    }
}
boolean isEven(int n){
    if(n%2==0)
        return true;
    return false;
}
```
code2:
```java
/*新建一个数组先把原数组中的奇数push进去再把偶数push进去，然后用新数组数据覆盖原数组即可
复杂度O(n)
*/
class Solution {
public:
    void reOrderArray(vector<int> &array) {
        vector<int> res;
        for(int i = 0; i < array.size(); i++)
        {
            if(array[i] % 2 == 1)
                res.push_back(array[i]);
        }
        for(int i = 0; i < array.size(); i++)
        {
            if(array[i] % 2 == 0)
                res.push_back(array[i]);
        }
        //array.swap(res);
        array = res;
        
    }
}
```
# 5.[二进制中1的个数]()
输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
```java
    //思想：用1（1自身左移运算，其实后来就不是1了）和n的每位进行位与，来判断1的个数
    private static int NumberOf1_low(int n) {
        int count = 0;
        int flag = 1;
        while (flag != 0) {
            if ((n & flag) != 0) {
                count++;
            }
            flag = flag << 1;
        }
        return count;
    }
    //--------------------最优解----------------------------
    public static int NumberOf1(int n) {
        int count = 0;
        while (n != 0) {
            ++count;
            n = (n - 1) & n;
        }
        return count;
    }
```
# 6.[median-of-two-sorted-arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/description/)
There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

Example 1:
```
nums1 = [1, 3]
nums2 = [2]
```
The median is 2.0

Example 2:
```
nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5
```
code:
```java
class Solution {
    public double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) { // to ensure m<=n
            int[] temp = A; A = B; B = temp;
            int tmp = m; m = n; n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j-1] > A[i]){
                iMin = iMin + 1; // i is too small
            }
            else if (i > iMin && A[i-1] > B[j]) {
                iMax = iMax - 1; // i is too big
            }
            else { // i is perfect
                int maxLeft = 0;
                if (i == 0) { maxLeft = B[j-1]; }
                else if (j == 0) { maxLeft = A[i-1]; }
                else { maxLeft = Math.max(A[i-1], B[j-1]); }
                if ( (m + n) % 2 == 1 ) { return maxLeft; }

                int minRight = 0;
                if (i == m) { minRight = B[j]; }
                else if (j == n) { minRight = A[i]; }
                else { minRight = Math.min(B[j], A[i]); }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }
}
```
# 7.[数组中只出现一次的数字]()

一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
```java
//num1,num2分别为长度为1的数组。传出参数
//将num1[0],num2[0]设置为返回结果
public class Solution {
    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
		if(array==null ||array.length<2)
            return ;
       int temp = 0;
       for(int i=0;i<array.length;i++)
           temp ^= array[i];
        
       int indexOf1 = findFirstBitIs(temp);
       for(int i=0;i<array.length;i++){
           if(isBit(array[i], indexOf1))
               num1[0]^=array[i];
           else
               num2[0]^=array[i];
       }
    }

	public int findFirstBitIs(int num){
       int indexBit = 0;
       while(((num & 1)==0) && (indexBit)<8*4){
           num = num >> 1;
           ++indexBit;
       }
       return indexBit;
   }
   public boolean isBit(int num,int indexBit){
       num = num >> indexBit;
       return (num & 1) == 1;
   }
}
```