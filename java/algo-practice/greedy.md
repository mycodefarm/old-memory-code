## 1.[candy](https://leetcode.com/problems/candy/description/)
There are N children standing in a line. Each child is assigned a rating value.
You are giving candies to these children subjected to the following requirements:
Each child must have at least one candy.
Children with a higher rating get more candies than their neighbors.
What is the minimum candies you must give?

```java

public class Solution {
    public int candy(int[] r) {
        int len = r.length;
        if(len==0){
            return 0;
        }else if(len==1){
            return 1;
        }
        
        int[]dp = new int[len];
		for(int i=0;i<len;i++){
            dp[i] = 1;
        }  
        for(int i=1;i<len;i++){
            if(r[i-1]<r[i]){
                dp[i] = dp[i-1]+1;
            }
        }
        for(int i=len-2;i>=0;i--){
            if(r[i+1]<r[i]){
                dp[i] = Math.max(dp[i],dp[i+1]+1);
            }
        }
        int min = 0;
        for(int i=0;i<len;i++){
            min+=dp[i];
        }
        return min;
    }   
}
```
# 2.[gas-station](https://leetcode.com/problems/gas-station/description/)
There are N gas stations along a circular route, where the amount of gas at station i isgas[i].
You have a car with an unlimited gas tank and it costscost[i]of gas to travel from station i to its next station (i+1). You begin the journey with an empty tank at one of the gas stations.
Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.
Note: 
The solution is guaranteed to be unique.

```java
public class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int step = 0;//记录走的路径上的花销，如果小于0说明有个站不够通过
        int sum = 0;//记录总的花销，有剩余说明可以走完全程
        int result = -1;
        for(int i=0;i<n;i++){
            sum += gas[i]-cost[i];
            step += gas[i] - cost[i];
            if(step<0){
                step = 0;
                result = i;//i到i+1不可达
            }
        }
        return sum>=0?result+1:-1;
    }
}
```