package Companys.Karat;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
第一小问：只有加，减，数字
第二小问：在之前的基础上有了括号.
第三小问：在此基础上有variable。有的可以map上数字，有的不行。return一个最简化后的string
*/
public class Calculator {

    //1. 只有加减和数字：2+3-999
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

    //2.加减数字+括号：2+((8+2)+(3-999))
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
            if (c == ' ') { //跳过空格
                continue;
            }
            if (Character.isDigit(c)) { //数字
                num = num * 10 + c - '0';
            } else if (c == '+') {
                res += sign * num;//数字已经结束了，把之前结果加上
                num = 0;//reset 数字num和符号
                sign = 1;
            } else if (c == '-') {
                res += sign * num;//数字已经结束了，把之前结果加上
                num = 0; //reset 数字num和符号
                sign = -1;
            } else if (c == '(') { //只操作res和sign
                stack.push(res); // res和sign 入栈 （暂存）
                stack.push(sign);
                res = 0;
                sign = 1;//reset 结果res和符号
            } else if (c == ')') { //加结果（之前），num归零，出栈res和sign，计算当前结果
                res += sign * num;
                num = 0;
                //sign=1;
                res *= stack.pop();  //符号sign 先出来
                res += stack.pop(); //数字num 再出来
            }
        }
        res += sign * num; //最后一步：如果是数字，需要再加结果；如果是），加的结果中num=0，不影响最后结果
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

    public static void main(String[] args) {

        System.out.println(calculateIntuit3("a+b+e+w+1+2+a"));
        System.out.println(calculateIntuit2("10-(5-(3-2))-2"));
        System.out.println(calculateIntuit2("3+(5-3)"));


    }
}
