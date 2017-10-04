package com.dumaisoft.zhihu;

/**
 * Created by wxb on 2017/9/17 0017.
 */
public class CommonTest {
    public static GenArray<Integer>[] genArray;

    public static void main(String[] args) {
        GenArray<MyClass> gar = new GenArray<>(10);
        for (int i = 0; i < 10; i++) {
            gar.put(i, new MyClass(i, "a", i));
        }
//        Object[] array = gar.rep();
        MyClass[] array = (MyClass[]) gar.rep(); // (1)
        for (int i = 0; i < 10; i++) {
            MyClass myClass = (MyClass) array[i]; //(2)
            System.out.println(myClass.id);
        }
    }
}

class GenArray<T> {
    private T[] array;

    public GenArray(int size) {
        array = (T[]) new Object[size];
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    public T[] rep() {
        return array;
    }
}


class MyClass {
    int id;
    String name;
    int age;

    MyClass(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}