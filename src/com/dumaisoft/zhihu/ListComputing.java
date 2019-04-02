package com.dumaisoft.zhihu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 先计算个复杂度：
 * 首先，将10个数全排列，是10的阶乘=3628800
 * 第二，将4个运算随机运用9次，为4的9次方=262144
 * 则穷举次数=951268147200，9512亿
 * @Author alex
 * @Date 2019/3/16 0016 下午 10:15
 */
public class ListComputing {
    private static int WANTED_NUMBER = 2000;
    private static List<String> computingSerail;
    private static int resolve_number = 0;

    public static void main(String[] args) {
        List<Integer> s = new ArrayList<Integer>();
        List<Integer> rs = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++)
            s.add(i);

        long start = System.currentTimeMillis();
        //生成运算符号数组
        computingSerail = getAllComputing(s.size());
        System.out.println("运算符号数组长度："+computingSerail.size());

        //生成全排列数组，同时求解
        pl(s, rs);
        long end = System.currentTimeMillis();
        System.out.println("解的个数为："+resolve_number+",所用的时间为："+(end -start)+"毫秒");
    }

    /**
     * 全排列生成,计算复杂度n!
     *
     * @param s
     * @param rs
     */
    public static void pl(List<Integer> s, List<Integer> rs) {
        if (s.size() == 1) {
            rs.add(s.get(0));
            //这里标志生成了一个全排列
            System.out.println(rs.toString());
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
     * 四则运算测试，使用运算符号数组来测试rs
     * @param rs
     */
    private static void testComputing(List<Integer> rs) {
        int n = computingSerail.size();
        for (int i = 0; i < n; i++) {
            String computingString = computingSerail.get(i);
            if (compute(computingString, rs) == WANTED_NUMBER) {
                System.out.println("得到一个解：");
                System.out.println(computingString);
                System.out.println(rs);
                resolve_number++;
            }
        }
    }

    /**
     * 具体计算
     * @param computingString
     * @param rs
     * @return
     */
    private static int compute(String computingString, List<Integer> rs) {
        int result = rs.get(0);
        for (int i = 0; i < rs.size()-1; i++) {
            char c = computingString.charAt(i);
            if (c == '+') {
                result += rs.get(i + 1);
            } else if(c == '-'){
                result -= rs.get(i + 1);
            } else if (c == '*') {
                result *= rs.get(i + 1);
            } else if (c == '/') {
                result /= rs.get(i + 1);
            }
        }
        return result;
    }
}
