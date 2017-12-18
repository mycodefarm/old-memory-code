## 1.[word-break](https://leetcode.com/problems/word-break/description/)
Given a string s and a dictionary of words dict, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
For example, given
s ="leetcode",
dict =["leet", "code"].
Return true because"leetcode"can be segmented as"leet code".

```java
import java.util.*;

public class Solution {
    
    /**
    * 动归方程dp[i] = dp[j]&&dict.contains(s.substring(j,i))
    * 表示从开始到第j个字符可以找到匹配，再加入第i个字符看是否匹配
    * 直到最后一个匹配都可以找到匹配就能被分割
    **/
    public boolean wordBreak(String s, Set<String> dict) {
        int len = s.length();
        boolean[] dp = new boolean[len+1];
        dp[0] = true;
        for(int i=0;i<=len;i++){
            for(int j=0;j<i;j++){
                if(dp[j]&&dict.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[len];
    }
}
```

## 2
Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a valid dictionary word.
Return all such possible sentences.
For example, given
s ="catsanddog",
dict =["cat", "cats", "and", "sand", "dog"].
A solution is["cats and dog", "cat sand dog"].


code1：
```java
import java.util.*;

/**
思想还是子问题划分
将字符串分为前后两部分s1和s2
s1从头开始，表示能够匹配的单词，s2是剩下的部分，
s2进入递归返回结果，然后将s1和s2的结果拼接就构成了一种可能。
反复进行，直到s1达到串尾。
*/
public class Solution {
    public ArrayList<String> wordBreak(String s, Set<String> dict) {
        Map<String,List<String>> temp = new HashMap<>();
        return dp(s,dict,temp);
    }
    
    public ArrayList<String> dp(String s, Set<String> dict,Map<String,List<String>> temp){
        if(temp.containsKey(s)){
            return (ArrayList<String>)temp.get(s);
        }
        
        ArrayList<String> re = new ArrayList<>();
        int len = s.length();
        for(int i=0;i<=len;i++){
            String s1 = s.substring(0,i);
            if(dict.contains(s1)){
                String s2 = s.substring(i);
                if("".equals(s2)){
                    re.add(s1);
                    continue;
                }
                //递归
                List<String> list = dp(s2,dict,temp);
                //对结果进行拼接
                for(String ss:list){
                    StringBuffer sb = new StringBuffer();
                    sb.append(s1).append(" ").append(ss);
                    re.add(sb.toString().trim());
                }
            }
        }
        temp.put(s,re);
        return re;
    }
}
```
code2：


测试用例:
"catsanddog",["cat","cats","and","sand","dog"]

结果：["cat sand dog","cats and dog"]

## 3.[palindrome-partitioning-ii](https://leetcode.com/problems/palindrome-partitioning-ii/description/)
Given a string s, partition s such that every substring of the partition is a palindrome.
Return the minimum cuts needed for a palindrome partitioning of s.
For example, given s ="aab",
Return1since the palindrome partitioning["aa","b"]could be produced using 1 cut.

```java
public class Solution {
    public int minCut(String s) {
        int len = s.length();
        //dp[i]表示0..i的字符串的最小分割数
        //...i,i+1,...j,j+1,方程dp[j+1] = min(dp[j+1],dp[i]+1),其中i..j是回文
        //表示比较切不切这个i..j的回文的次数和原来
        int[] dp = new int[len+1];
        boolean[][]p = new boolean[len][len];//p[i][j]表示i到j的字符串是否是回文
        //初始化dp
        for(int i=0;i<=len;i++){
            dp[i] = i-1;//其实可以设为无限大，只要dp[0] = -1就行
        }
        for(int j=1;j<len;j++){
            for(int i=j;i>=0;i--){
                if(s.charAt(i)==s.charAt(j)&&(j-i<=1||p[i+1][j-1])){//i..j是回文，1个字符也是,j-i<=1写前面不会越界
                    p[i][j] = true;
                    dp[j+1] = Math.min(dp[j+1],dp[i]+1);
                }
            }
        }
        return dp[len];
    }
}
```
## 4.[triangle](https://leetcode.com/problems/triangle/description/)
Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.
For example, given the following triangle
[
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]

The minimum path sum from top to bottom is11(i.e., 2 + 3 + 5 + 1 = 11).
Note: 
Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.

code1:
```java
import java.util.ArrayList;

public class Solution {
    public int minimumTotal(ArrayList<ArrayList<Integer>> tri) {
        int n = tri.size();        
        int[] re = new int[n+1];
        //从后往前求解，re[i]代表第i行（含i）之后的最小和
        for(int i=n-1;i>=0;i--){
            ArrayList<Integer> t = tri.get(i);
            for(int j=0;j<t.size();j++){
                re[j] = t.get(j)+Math.min(re[j],re[j+1]);
            }
        }
        return re[0];
    }
}
```

code2:递归
```java
import java.util.ArrayList;

public class Solution {
    public int minimumTotal(ArrayList<ArrayList<Integer>> tri) {
        return minmin(tri,0,0);
    }
    //第i行第j列,sum的值等于当前值+下一行的k和k+1列的最小值
    public int minmin(ArrayList<ArrayList<Integer>> tri,int i,int j){
        int sum = tri.get(i).get(j);
        if(i<tri.size()-1){
            sum += Math.min(minmin(tri,i+1,j),minmin(tri,i+1,j+1));
        }
        return sum;
    }
}
```

## 5.[distinct-subsequences](https://leetcode.com/problems/distinct-subsequences/)
Given a string S and a string T, count the number of distinct subsequences of T in S.
A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie,"ACE"is a subsequence of"ABCDE"while"AEC"is not).
Here is an example:
S ="rabbbit", T ="rabbit"
Return3.

code1：
```java
public class Solution {
    public int numDistinct(String S, String T) {
        int slen = S.length();
        int tlen = T.length();
        //dp[i+1][j+1]表示：串T[0..i]在S[0..j]中被包含多少次
        //dp[i+1][j+1] = dp[i][j] + dp[i+1][j];当S[j]被考虑进来
        //dp[i+1][j+1] = dp[i+1][j];当S[j]没有考虑进来
        int[][]dp = new int[tlen+1][slen+1];
        
        //第一行初始化为1,表示空串T总是被S包含
        for(int i=0;i<=slen;i++){
            dp[0][i] = 1;
        }
        //第一列初始化为0，已经默认了
        
        for(int i=0;i<tlen;i++){
            for(int j=0;j<slen;j++){
                if(S.charAt(j)==T.charAt(i)){
                    dp[i+1][j+1] = dp[i][j] + dp[i+1][j];
                }else{
                    dp[i+1][j+1] = dp[i+1][j];
                }
            }
        }
        return dp[tlen][slen];
    }
}
```
code2：减少维数后
```java
public class Solution {
    
    public int numDistinct(String S, String T) {
        int slen = S.length();
        int tlen = T.length();
        //dp[j]代表：在T[0..j]的串能在S中找到多少次(指去除一些字符相等)
        //dp[tlen]就代表整个T串可以在S中找到多少次
        int[]dp = new int[tlen+1];
        dp[0] = 1;
        for(int i=1;i<=slen;i++){
            for(int j=Math.min(tlen,i);j>0;j--){
                if(S.charAt(i-1)==T.charAt(j-1)){
                    dp[j] = dp[j]+dp[j-1];
                }
            }
        }
        return dp[tlen];
    }
}
```
## 6.[minimum-path-sum](https://leetcode.com/problems/minimum-path-sum/description/)
Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.
Note: You can only move either down or right at any point in time.

```java
import java.util.Arrays;
public class Solution {
    public int minPathSum(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        //int[][]dp = new int[row+1][col+1];
        //dp[i][j]表示到grid[i][j]的最小路径和
        //只能从左边或上面到这里
        //dp[i][j] = min(d[i][j-1],dp[i-1][j])+grid[i][j]
        //可以发现只需要一维数组就行了，所以变为dp
        int[]dp = new int[col+1];
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[1] = 0;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                dp[j+1] = Math.min(dp[j+1],dp[j])+grid[i][j];
            }
        }
        return dp[col];
    }
} 
```
## 7.[unique-paths](https://leetcode.com/problems/unique-paths/description/)
A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
How many possible unique paths are there?
Above is a 3 x 7 grid. How many possible unique paths are there?
Note: m and n will be at most 100.
```java
public class Solution {
    public int uniquePaths(int m, int n) {
        int[][]dp = new int[m][n];
        //第一列和第一行只有一种情况
        for(int i=0;i<m;i++){
            dp[i][0] = 1;
        }
        for(int j=0;j<n;j++){
            dp[0][j] = 1;
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                dp[i][j] = dp[i][j-1]+dp[i-1][j];
            }
        }
        return dp[m-1][n-1];
    }
}
```

## 8.[unique-paths-ii](https://leetcode.com/problems/unique-paths-ii/description/)
Follow up for "Unique Paths":

Now consider if some obstacles are added to the grids. How many unique paths would there be?

An obstacle and empty space is marked as 1 and 0 respectively in the grid.

For example,
There is one obstacle in the middle of a 3x3 grid as illustrated below.

[
  [0,0,0],
  [0,1,0],
  [0,0,0]
]
The total number of unique paths is 2.

Note: m and n will be at most 100.

```java
public class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int row = obstacleGrid.length;
        int col = obstacleGrid[0].length;
        //特殊情况
        if(obstacleGrid[row-1][col-1]==1){
            return 0;
        }
        int[][]dp = new int[row][col];
        //第一列或则行遇到一个1后面都不可达
        for(int i=0;i<row;i++){
            if(obstacleGrid[i][0]==1){
                break;
            }
            dp[i][0] = 1;
        }
        for(int j=0;j<col;j++){
            if(obstacleGrid[0][j]==1){
                break;
            }
            dp[0][j] = 1;
        }
        for(int i=1;i<row;i++){
            for(int j=1;j<col;j++){
                int up = dp[i-1][j];
                int left = dp[i][j-1];
                if(obstacleGrid[i-1][j]==1){
                    up = 0;
                }
                if(obstacleGrid[i][j-1]==1){
                    left = 0;
                }
                dp[i][j] = left+up;
            }
        }
        return dp[row-1][col-1];
    }
}
```