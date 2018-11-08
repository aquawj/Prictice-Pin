package Companys.Intuit;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
第一小问：只有加，减，数字
第二小问：在之前的基础上有了括号.
第三小问：在此基础上有variable。有的可以map上数字，有的不行。return一个最简化后的string
*/
public class Calculator {
    public static void main(String[] args) {

        System.out.println(calculateIntuit3("a+b+e+w+1+2+a"));
    }
    //2+3-999
    public static int calculateIntuit(String s) {
        //basis idea是把减号变成负数，这样每个数字都可以相加了
        int sign = 1;//1="+", -1="-"
        int res = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            char c = chars[i];
            int num = 0;
            if (Character.isDigit(c)) {
                num = c - '0';
                while (i + 1 < s.length() && Character.isDigit(chars[i + 1])) {
                    num = num * 10 + chars[i + 1] - '0';
                    i++;
                }
                num *= sign;
                res += num;
            } else if (c == '+') {
                sign = 1;
            } else if (c == '-') {
                sign = -1;
            }
        }
        return res;
    }

    //2+((8+2)+(3-999))
    //basic calculator2
    public static int calculateIntuit2(String s) {

        // ): 先处理好括号里面的结果，再处理(外面的结果
        s = s.trim();
        int num = 0;
        int res = 0;
        int sign = 1;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if (c == '+') {
                res += sign * num;//means end of a num, add previous num
                num = 0;//reset num
                sign = 1;//set current sign
            } else if (c == '-') {
                res += sign * num;
                num = 0;
                sign = -1;
            } else if (c == '(') {
                stack.push(res);
                stack.push(sign);
                res = 0;
                sign = 1;//new sign would be 1
            } else if (c == ')') {
                res += sign * num;
                num = 0;
                res *= stack.pop();
                res += stack.pop();
            }
        }
        res += sign * num;
        return res;
    }

    //a+b+c+1+d=7 ----> 7+d
    private static final Map<Character, Integer> MAP = new HashMap<>();
    static {
        MAP.put('a', 1);
        MAP.put('b', 2);
        MAP.put('c', 3);
    }
    public static String calculateIntuit3(String s) {

        s = s.trim();
        int num = 0;
        StringBuilder tempS = new StringBuilder();
        int tempN = 0;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (MAP.containsKey(c) || Character.isDigit(c)) {
                res.append(tempS.toString());
                tempS = new StringBuilder();
                if (MAP.containsKey(c)) {
                    num = num * 10 + MAP.get(c);
                } else {
                    num = num * 10 + c - '0';
                }

            } else if (c == '+') {
                //'+'后面是数字
                if (i + 1 < s.length() && MAP.containsKey(s.charAt(i + 1))
                        || i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    //case1:string case2:num
                    if (tempS.length() != 0) {//+前面是string
                        tempS.append('+');
                    } else {//+前面是num
                        tempN += num;
                        num = 0;
                    }
                    //'+'后面是char
                } else if (i + 1 < s.length() && !MAP.containsKey(s.charAt(i + 1))) {
                    if (num != 0) {//单个1的情况, '+'前面有数字
                        tempN += num;
                        res.append(tempN);
                        tempN = 0;
                        num = 0;
                    }
                    //'+'前面没有数字
                    tempS.append('+');
                }
            } else {//unknow char
                if (tempN != 0) {
                    res.append(tempN);
                }
                tempS.append(c);
            }
        }
        //last position
        if (num != 0 ) {
            tempN += num;
            res.append(tempN);
        }
        if (tempS.length() != 0) {
            res.append(tempS);
        }
        return res.toString();
    }
}
