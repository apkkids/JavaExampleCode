package com.dumaisoft.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/6/27
 * Create Time: 21:44
 * Description: how to use reflection to access private field and method in other class.
 */
public class ReflectionExample {
    public static void main(String[] args) throws Exception {
        Dummy d=new Dummy();

        /*---  [INVOKING CTOR]  ---*/
        Method ctor=Dummy.class.getDeclaredMethod("Dummy");
        //m.invoke(d);//exception java.lang.IllegalAccessException
        ctor.setAccessible(true);//Abracadabra
        ctor.invoke(d);//now its ok

        /*---  [INVOKING PRIVATE METHOD]  ---*/
        Method m=Dummy.class.getDeclaredMethod("foo");
        //m.invoke(d);//exception java.lang.IllegalAccessException
        m.setAccessible(true);//Abracadabra
        m.invoke(d);//now its ok

        /*---  [GETING VALUE FROM PRIVATE FIELD]  ---*/
        Field f=Dummy.class.getDeclaredField("i");
        //System.out.println(f.get(d));//not accessible now
        f.setAccessible(true);//Abracadabra
        System.out.println(f.get(d));//now its ok

        /*---  [SETTING VALUE OF PRIVATE FIELD]  ---*/
        Field f2=Dummy.class.getDeclaredField("i");
        //f2.set(d,20);//not accessible now
        f2.setAccessible(true);//Abracadabra
        f2.set(d,20);//now its ok
        System.out.println(f2.get(d));
    }
}


class Dummy{
    public Dummy(){

    }
    private void foo(){
        System.out.println("hello foo()");
    }
    private int i=10;
}
