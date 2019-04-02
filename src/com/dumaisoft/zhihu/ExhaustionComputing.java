package com.dumaisoft.zhihu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @Description
 * 1 2 3 4 5 6 7 8 9 10=2000 可以在两个数之间使用加减乘除和括号 ，使等式成立
 *
 * 解体思路：
 * 1.9个运算符，每个运算符都可能为加减乘除，因此运算符数组为4的9次方=262144
 * 2.10个数字，需要运算9次，运算的先后顺序是一个全排列，即9的阶乘=362880
 * 整体遍历复杂度是95126814720，951亿
* @Author alex
* @Date 2019/3/17 0017 上午 10:38
*/
public class ExhaustionComputing {
    private static int WANTED_NUMBER = 2000;
    private static List<String> computingSerail;
    private static int resolve_number = 0;
    private static List<Integer> numbers= new ArrayList<>();

    public static void main(String[] args) {
        //构造数组
        for (int i = 1; i <=10 ; i++) {
            numbers.add(i);
        }

        long start = System.currentTimeMillis();
        //1.构造运算符号数组，数量为262144
        computingSerail = getAllComputing(numbers.size());
        System.out.println("运算符号数组长度："+computingSerail.size());

        //2.构造运算先后顺序排列，此为一个全排列,同时求解
        //计算顺序为 1 2 3 4 5 6 7 8 9，表明依次计算
        //计算顺序为 9 8 7 6 5 4 3 2 1，表明先计算9和10，然后是8和9 10的结果，依次倒序计算
        Integer[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> s = Arrays.asList(a);
        List<Integer> rs = new ArrayList<Integer>();
        pl(s, rs);

        long end = System.currentTimeMillis();
        System.out.println("解的个数为："+resolve_number+",所用的时间为："+(end -start)+"毫秒");
    }

    /**
     * 生成长度为n的数组的运算序列
     * @param n 数组长度
     * @return
     */
    private static List<String> getAllComputing(int n) {
        int m = n - 1;
        List<String> strings = new ArrayList<>();
        strings.add("");
        while (m > 0) {
            //取出所有原来的元素
            List<String> tmp = new ArrayList<>(strings);
            strings.clear();
            for (String s : tmp) {
                strings.add(s + "+");
            }
            for (String s : tmp) {
                strings.add(s + "-");
            }
            for (String s : tmp) {
                strings.add(s + "*");
            }
            for (String s : tmp) {
                strings.add(s + "/");
            }
            m--;
        }
        return strings;
    }

    /**
     * 全排列生成,计算复杂度n!
     * @param s
     * @param rs
     */
    public static void pl(List<Integer> s, List<Integer> rs) {
        if (s.size() == 1) {
            rs.add(s.get(0));
            //这里标志生成了一个全排列
//            System.out.println(rs.toString());
            //进行四则运算测试
            testComputing(rs);
            rs.remove(rs.size() - 1);
        } else {
            for (int i = 0; i < s.size(); i++) {
                rs.add(s.get(i));
                //深拷贝
                List<Integer> tmp = new ArrayList<Integer>(s);
                tmp.remove(i);
                pl(tmp, rs);
                rs.remove(rs.size() - 1);
            }
        }
    }

    /**
     * 用所有的运算符号数组来测试这个排列是否能成功
     * @param rs
     */
    private static void testComputing(List<Integer> rs) {
        for (int i = 0; i < computingSerail.size(); i++) {
            String computingString = computingSerail.get(i);
            //如果测试成功，则打印出公式
            if (compute(computingString, rs) == WANTED_NUMBER) {
                printResult(computingString, rs);
                System.out.println("结果序号："+(++resolve_number));
            }
        }
    }

    /**
     * 格式化打印出运算公式
     * @param computingString
     * @param rs
     */
    private static void printResult(String computingString, List<Integer> rs) {
        //复制一份数组
        List<Integer> tmp = new ArrayList<>(numbers);
        Stack<String> stack = new Stack<>();
        String result = "";
        for (int i = 0; i < rs.size(); i++) {
            //得到计算顺序号
            int order = rs.get(i);
            //从数组中取出两个数字
            String left = String.valueOf(tmp.get(order - 1));
            String right = String.valueOf(tmp.get(order));
            //如果数字已经被标记为0，则从栈中取出暂存结果
            if (left.equals("0")) {
                left = stack.pop();
            }
            if (right.equals("0")) {
                right = stack.pop();
            }
            //得到计算符号
            char c = computingString.charAt(order-1);
            //组合计算公式，有时会左右颠倒，需要处理
            result = makeFomula(c,left,right);
            //标记
            tmp.set(order - 1, 0);
            tmp.set(order, 0);
            //入栈
            stack.push(result);
        }
        result = stack.pop();
        System.out.println(result);
    }

    /**
     * 组合计算公式
     * @param c
     * @param left
     * @param right
     * @return
     */
    private static String makeFomula(char c, String left, String right) {
        //找出左边公式中最大的数字
        String regex = "\\d*";
        int maxLeft = 0;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(left);
        while(m.find()) {
            if(!"".equals(m.group()))
                maxLeft = Integer.parseInt(m.group());
        }
        //找出右边公式中最大的数字
        int maxRight = 0;
        m = p.matcher(right);
        while(m.find()) {
            if(!"".equals(m.group()))
                maxRight = Integer.parseInt(m.group());
        }
        if (maxLeft > maxRight) {
            return "(" + right + c + left + ")";
        }
        return "(" + left + c + right + ")";
    }

    /**
     * 进行一次数组全计算
     * @param computingString
     * @param rs
     * @return
     */
    private static int compute(String computingString, List<Integer> rs) {
        //复制一份数组
        List<Integer> tmp = new ArrayList<>(numbers);
        //创建一个栈来暂存中间运算结果
        Stack<Float> stack = new Stack<>();

        float result = 0;
        for (int i = 0; i < rs.size(); i++) {
            //得到计算顺序号
            int order = rs.get(i);
            //从数组中取出两个数字
            float left = tmp.get(order - 1);
            float right = tmp.get(order);
            //如果数字已经被标记为0，则从栈中取出暂存结果
            if (left == 0) {
                left = stack.pop();
            }
            if (right == 0) {
                right = stack.pop();
            }
            //得到计算符号
            char c = computingString.charAt(order-1);
            //计算
            result = computeWith(c, left, right);
            //标记
            tmp.set(order - 1, 0);
            tmp.set(order, 0);
            //入栈
            stack.push(result);
        }
        result = stack.pop();
        return (int)result;
    }

    /**
     * 进行一次四则计算
     * @param c
     * @param left
     * @param right
     * @return
     */
    private static float computeWith(char c, float left, float right) {
        if (c == '+') {
            return left+right;
        } else if(c == '-'){
            return left-right;
        } else if (c == '*') {
            return left*right;
        } else if (c == '/') {
            return left/right;
        }
        return -1;
    }
}
