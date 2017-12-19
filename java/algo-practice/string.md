# 1.
题目描述
将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0
输入描述:
输入一个字符串,包括数字字母符号,可以为空
输出描述:
如果是合法的数值表达则返回该数字，否则返回0
示例1
输入
```java
+2147483647
    1a33
```
输出
```java
2147483647
    0
```
```java
public class Solution {
    public int StrToInt(String str) {
        if(str==null||"".equals(str)){
            return 0;
        }
        char[] c = str.toCharArray();
        int symbol = 1;//-1表示负号
        if(c[0]=='-'){
            symbol = -1;
            c[0] = '0';
        }else if(c[0]=='+'){
            symbol = 1;
            c[0] = '0';
        }
        int sum = 0;
        for(int i=0;i<c.length;i++){
            if(c[i]<'0'||c[i]>'9'){
                return 0;
            }
            sum = sum*10 + (c[i] - '0');
        }
        return symbol*sum;
    }
}
```
