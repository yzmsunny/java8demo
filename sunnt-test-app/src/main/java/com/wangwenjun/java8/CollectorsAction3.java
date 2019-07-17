package com.wangwenjun.java8;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static com.wangwenjun.java8.CollectorsAction.menu;

public class CollectorsAction3 {

    public static void main(String[] args) {
        testPartitioningByWithPredicate();
        testPartitioningByWithPredicateAndCollector();
        testReducingBinaryOperator();
        testReducingBinaryOperatorAndIdentiy();
        testReducingBinaryOperatorAndIdentiyAndFunction();
        testSummarizingDouble();
        testSummarizingLong();
        testSummarizingInt();
    }

    /** @desc 与groupingBy类似，这个是根据boolean分组 */
    private static void testPartitioningByWithPredicate() {
        System.out.println("testPartitioningByWithPredicate");
        Map<Boolean, List<Dish>> collect = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        Optional.of(collect).ifPresent(System.out::println);

        Map<Boolean, List<Dish>> collect1 = menu.stream().collect(Collectors.groupingBy(Dish::isVegetarian));
        System.out.println(collect1);

    }
    /** @desc 与groupingBy类似，这个是根据boolean分组 ,计算某字段平均值*/
    private static void testPartitioningByWithPredicateAndCollector() {
        System.out.println("testPartitioningByWithPredicateAndCollector");
        Map<Boolean, Double> collect = menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.averagingInt(Dish::getCalories)));
        Optional.of(collect).ifPresent(System.out::println);
    }

    private static void testReducingBinaryOperator() {
        System.out.println("testReducingBinaryOperator");
        menu.stream().collect(
                Collectors.reducing(
                        BinaryOperator.maxBy(
                                Comparator.comparingInt(Dish::getCalories)
                        )
                )
        ).ifPresent(System.out::println);
    }

    /** @desc 计算总和 */
    private static void testReducingBinaryOperatorAndIdentiy() {
        System.out.println("testReducingBinaryOperatorAndIdentiy");
        Integer result = menu.stream()
                .map(Dish::getCalories).collect(Collectors.reducing(0, (d1, d2) -> d1 + d2));
        System.out.println(result);
    }

    /** @desc 根据某字段汇总：{count=9, sum=4200.000000, min=120.000000, average=466.666667, max=800.000000} */
    private static void testReducingBinaryOperatorAndIdentiyAndFunction() {
        System.out.println("testReducingBinaryOperatorAndIdentiyAndFunction");
        Integer result = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (d1, d2) -> d1 + d2));
        System.out.println(result);
    }

    private static void testSummarizingDouble() {
        System.out.println("testSummarizingDouble");
        Optional.of(menu.stream().collect(Collectors.summarizingDouble(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    private static void testSummarizingLong() {
        System.out.println("testSummarizingLong");
        Optional.of(menu.stream().collect(Collectors.summarizingLong(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    private static void testSummarizingInt() {
        System.out.println("testSummarizingLong");
        Optional.of(menu.stream().collect(Collectors.summarizingInt(Dish::getCalories)))
                .ifPresent(System.out::println);

    }
}