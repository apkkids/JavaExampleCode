package com.dumaisoft.zhihu;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/2/11
 * Create Time: 22:36
 * Description:
 */
public class Test {
    public static void main(String[] args0) {
        Collection c = new HashSet();
        c.add("holy");
        c.add("shit");
        Iterator it = c.iterator();
        System.out.println(c.getClass());
        System.out.println(it.getClass());
        System.out.println(it.hasNext());
    }
}
