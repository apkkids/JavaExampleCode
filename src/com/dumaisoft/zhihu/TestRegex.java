package com.dumaisoft.zhihu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description TODO
 * @Author alex
 * @Date 2019/3/17 0017 上午 11:51
 */
public class TestRegex {
    public static void main(String[] args) {
        String str = "((1+2)*8-10)";
        String regex = "\\d*";
        String result = "";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while(m.find()) {
            if(!"".equals(m.group()))
                result = m.group();
        }
        System.out.println(result);

    }
}
//        int rel = compute("+++-++*+-", s);
//        System.out.println(rel);
//        printResult("+++-++*+-",s);

//        s.clear();
//        Integer[] a = {3,4,5,1,2,6,7,9,8};
//        s = asList( a);
//        System.out.println(s);
//        printResult("+++-++*+-",s);
//        System.out.println(compute("+++-++*+-",s));