package com.dumaisoft.zhihu;

import java.text.DecimalFormat;

/**
 * Created by wxb on 2017/10/5 0005.
 */
public class DecimalFormatExam {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("###,##0.00");
        float f = 78881456777777.789f;
        System.out.println(df.format(f));
    }
}
