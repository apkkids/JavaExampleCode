package com.dumaisoft.zhihu;

/**
 * Created by wxb on 2017/8/29 0029.
 */
public class TestSQL {
    public static void main(String[] args) {
        String sql = "select * from orders where uid = '0A67CD5136EF46D286DCDDEE2C4B60B9' order by ordertime desc limit 0,4";

        System.out.println(sql);
        //test for github
    }
}
