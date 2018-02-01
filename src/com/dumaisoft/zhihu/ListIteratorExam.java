package com.dumaisoft.zhihu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by wxb on 2017/10/19 0019.
 */
public class ListIteratorExam {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        ListIterator<Integer> listIter = list.listIterator();
        Iterator<Integer> iter = list.iterator();
        //Iterator只能从前向后遍历
        System.out.println("Iterator只能从前向后遍历:");
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
        }
        System.out.println();

        //ListIterator可以从前向后，也可以从后向前
        System.out.println("ListIterator可以从前向后遍历：");
        while (listIter.hasNext()) {
            System.out.print(listIter.next() + " ");
        }
        System.out.println("\nListIterator也可以从后向前遍历：");
        while (listIter.hasPrevious()) {
            System.out.print(listIter.previous() + " ");
        }

        System.out.println("\nIterator可以remove元素:这里remove第一个元素");
        iter = list.iterator();
        iter.next();
        iter.remove();
        System.out.println(list);

        System.out.println("ListIterator也可以remove元素：这里继续remove第一个元素");
        listIter = list.listIterator();
        listIter.next();
        listIter.remove();
        System.out.println(list);

        System.out.println("ListIterator还可以获取当前cursor前后的索引（ListIterator总是指向两个索引之间的位置）");
        System.out.println("previousIndex:" + listIter.previousIndex());
        System.out.println("nextIndex:" + listIter.nextIndex());

        System.out.println("ListIterator还可以add或者set元素，操作的是上一次遍历过的元素（使用next或者previous方法）：");
        listIter.next();
        listIter.add(118);
        System.out.println(list);
        listIter.next();
        listIter.set(119);
        System.out.println(list);
    }
}
