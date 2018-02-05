package com.dumaisoft.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by wxb on 2018/2/5 0005.
 */
public class FunctionInterfaceDemo {
    @FunctionalInterface
    interface MyPredicate<T> {
        boolean test(T t);
    }

    public static boolean doPredicate(int age, MyPredicate<Integer> predicate) {
        return predicate.test(age);
    }

    /**
     * 供给型接口
     *
     * @param num
     * @param supplier
     * @return
     */
    public static List<Integer> supply(Integer num, Supplier<Integer> supplier) {
        List<Integer> resultList = new ArrayList<Integer>();
        for (int x = 0; x < num; x++)
            resultList.add(supplier.get());
        return resultList;
    }

    public static Integer convert(String str, Function<String, Integer> function) {
        return function.apply(str);
    }

    public static List<String> filter(List<String> fruits, Predicate<String> predicate) {
        List<String> f = new ArrayList<>();
        for (String s : fruits) {
            if (predicate.test(s)) {
                f.add(s);
            }
        }
        return f;
    }

    public static void donation(Integer money, Consumer<Integer> consumer) {
        consumer.accept(money);
    }

    public static void main(String[] args) {
        MyPredicate<Integer> predicate = (x) -> x >= 18;
        System.out.println(doPredicate(20, predicate));
        System.out.println(doPredicate(17, predicate));
        System.out.println("-------------供给型接口--------------");
        List<Integer> list = supply(10, () -> (int) (Math.random() * 100));
        list.forEach(System.out::println);
        System.out.println("-------------函数型接口--------------");
        Integer value = convert("28", x -> Integer.parseInt(x));
        System.out.println(value);
        System.out.println("-------------断言型接口--------------");
        List<String> fruit = Arrays.asList("香蕉", "哈密瓜", "榴莲", "火龙果", "水蜜桃");
        List<String> newFruit = filter(fruit, (f) -> f.length() == 2);
        System.out.println(newFruit);
        System.out.println("-------------消费型接口--------------");
        donation(1000, (money) -> System.out.println("好心的麦乐迪为Blade捐赠了" + money + "元"));
    }


}
