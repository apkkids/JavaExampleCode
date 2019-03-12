package com.dumaisoft.collection;

/**
 * Created by wxb on 2018/4/28 0028.
 */

import org.apache.commons.collections.bag.HashBag;
import java.util.Iterator;
public class HashBagBagTest
{
    public static void main(String[] args) {
        HashBag hashBag = new HashBag();
        String s1 = "s1";
        String s2 = "s2";
        hashBag.add(s1);
        hashBag.add(s1);
        hashBag.add(s2, 3);
        // 获得包中元素迭代器
        Iterator<?> iterator = hashBag.iterator();
        System.out.println("包中元素为：");
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }
        System.out.println("包中元素个数为：" + hashBag.size());
        System.out.println("包中entity1个数为：" + hashBag.getCount(s1));
        System.out.println("去重后个数为：" + hashBag.uniqueSet().size());
    }
}