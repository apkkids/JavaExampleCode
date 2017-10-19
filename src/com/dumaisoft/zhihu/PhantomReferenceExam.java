package com.dumaisoft.zhihu;

import java.lang.ref.ReferenceQueue;

/**
 * Created by wxb on 2017/10/10 0010.
 */
public class PhantomReferenceExam {
    public static void main(String[] args) {
        String str = new String("Alex");
        ReferenceQueue<String> rq = new ReferenceQueue();
        //这样是可以的，如果你没有在本package内定义一个自己的PhantomReference，也可以不要java.lang.ref.
        java.lang.ref.PhantomReference<String> pr = new java.lang.ref.PhantomReference(str,rq);
    }
}
