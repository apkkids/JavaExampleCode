package com.dumaisoft.annotation;

import java.lang.annotation.Repeatable;

/**
 * Created by wxb on 2018/9/15 0015.
 */
@interface Persons{
    Person[] value();
}
@Repeatable(Persons.class)
@interface Person{
    String role() default "";
}

@Person(role = "artist")
@Person(role = "coder")
@Person(role = "PM")
public class SuperMan {
}
