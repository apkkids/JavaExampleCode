package com.dumaisoft.reflection;

import java.lang.reflect.Method;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/6/27
 * Create Time: 21:46
 * Description:
 */
public class SafeMethod {
    public static void main(String[] args) throws Exception {
        DummySon d1 = new DummySon();//safeMethod can be invoked inside superclass constructor
        d1.trySafeMethod();//safeMethod can be invoked inside other Dummy class methods
        System.out.println("-------------------");

        //Lets check if it is possible to invoke it via reflection
        Method m2 = Dummy.class.getDeclaredMethod("safeMethod");
        // m.invoke(d);//exception java.lang.IllegalAccessException
        m2.setAccessible(true);
        m2.invoke(d1);
    }
}


class DummyFather {
    private void safeMethod() {
        StackTraceElement[] st = new Exception().getStackTrace();
        // if method was invoked by reflection stack trace would be simmilar
        // to something like this:
        /*
        java.lang.Exception
            at package1.b.Dummy.safeMethod(SomeClass.java:38)
            at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
            at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
            at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        ->  at java.lang.reflect.Method.invoke(Method.java:601)
            at package1.b.Test.main(SomeClass.java:65)
        */
        //5th line marked by "->" is interesting one so I will try to use that info

        if (st.length > 5 &&
                st[4].getClassName().equals("java.lang.reflect.Method"))
            throw new RuntimeException("safeMethod() is accessible only by Dummy object");

        // now normal code of method
        System.out.println("code of safe method");
    }

    //I will check if it is possible to normally use that method inside this class
    public void trySafeMethod(){
        safeMethod();
    }

    DummyFather() {
        safeMethod();
    }
}

class DummySon extends DummyFather {}
