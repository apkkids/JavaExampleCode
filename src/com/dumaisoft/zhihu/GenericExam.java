package com.dumaisoft.zhihu;

import java.io.Serializable;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/6
 * Create Time: 22:23
 * Description:
 */
public class GenericExam {
    public static  <T extends Serializable> T getById(Class<T> clazz, Serializable id){
        System.out.println("clazz is :"+clazz.getName());
        System.out.println("id content is :"+id.toString());
        return (T) id;
    }

    public static void main(String[] args) {
        String str = "Alex Wang";
        //调用getById时用String类型指定T
        String result = getById(String.class, str);
    }
}
