package com.dumaisoft.language;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/7
 * Create Time: 11:27
 * Description:
 */
class SomeObject {
    int flag;

    public SomeObject(int i) {
        this.flag = i;
    }

    @Override
    public String toString() {
        return "obj(flag=" + flag + ")";
    }
}

class ObjectPointer<T> {
    T pointer;

    public ObjectPointer(T value) {
        this.pointer = value;
    }

    public T getPointer() {
        return pointer;
    }

    public static <T> void swap(ObjectPointer<T> a, ObjectPointer<T> b) {
        T tmp = a.pointer;
        a.pointer = b.pointer;
        b.pointer = tmp;
    }
}

public class SwapTest {
    public static void main(String[] args) {
        SomeObject obj1 = new SomeObject(1);
        SomeObject obj2 = new SomeObject(2);
        System.out.println("before swap:");
        System.out.println("obj1=" + obj1);
        System.out.println("obj2=" + obj2);


        ObjectPointer<SomeObject> p1 = new ObjectPointer<>(obj1);
        ObjectPointer<SomeObject> p2 = new ObjectPointer<>(obj2);
        ObjectPointer.swap(p1, p2);

        obj1 = p1.getPointer();
        obj2 = p2.getPointer();
        System.out.println("after swap:");
        System.out.println("obj1=" + obj1);
        System.out.println("obj2=" + obj2);
    }
}
