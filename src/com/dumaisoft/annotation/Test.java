package com.dumaisoft.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by wxb on 2018/9/15 0015.
 */
@Retention(RetentionPolicy.RUNTIME)
@interface Check{
    String value();
}
@Retention(RetentionPolicy.RUNTIME)
@interface Perform{}

class Hero{
    public void say(){
        System.out.println("hero say");
    }
    public void speak(){
        System.out.println("hero speak");
    }
}

@TestAnnotation(id=3,msg = "hello annotation")
public class Test {
    @Check(value = "hi")
    int a;

    @Perform
    public void testMethod(){}

    @SuppressWarnings("deprecation")
    public void test1(){
        Hero hero = new Hero();
        hero.say();
        hero.speak();
    }

    public static void main(String[] args) {

        boolean hasAnnotation = Test.class.isAnnotationPresent(TestAnnotation.class);
        if (hasAnnotation) {
            TestAnnotation testAnnotation = Test.class.getAnnotation(TestAnnotation.class);
            System.out.println("id:" + testAnnotation.id());
            System.out.println("msg:" + testAnnotation.msg());
        }
        try {
            Field a = Test.class.getDeclaredField("a");
            a.setAccessible(true);
            Check check = a.getAnnotation(Check.class);
            if (check != null) {
                System.out.println("check value:"+check.value());
            }

            Method testMethod = Test.class.getDeclaredMethod("testMethod");
            if (testMethod != null) {
                Annotation[] ans =testMethod.getAnnotations();
                for (int i = 0; i < ans.length; i++) {
                    System.out.println("method testMethod annotation:"+ans[i].annotationType().getSimpleName());
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
