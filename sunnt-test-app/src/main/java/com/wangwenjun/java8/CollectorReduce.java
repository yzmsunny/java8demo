package com.wangwenjun.java8;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @Description: reduce 可以应用到找最大值、最小值、总和，
 * (i,j)->{i是上一次结果，比如i+j=下一循环的i}
 * @Date: 2019/7/5 11:25
 */
public class CollectorReduce {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));

        /** @desc 根据vegetarian条件过滤，统计数目 */
        long count = menu.stream().filter(d -> d.isVegetarian()).count();
        /** @desc 根据vegetarian条件过滤，统计数目 */
        Long collect = menu.stream().filter(d -> d.isVegetarian()).collect(Collectors.counting());

        /** @desc 获取calories最大值 */
        Optional<Integer> maxCalories = menu.stream().map(Dish::getCalories).reduce(Integer::min);

        /** @desc //返回最大Dish{name='pork', vegetarian=false, calories=800, type=MEAT} */
        menu.stream().reduce((dish, dish2) -> dish.getCalories() > dish2.getCalories()
                ? dish : dish2)
        // .ifPresent(System.out::println)
        ;
        /** @desc //返回最大Dish{name='pork', vegetarian=false, calories=800, type=MEAT} */
        Optional<Dish> maxCaloriesCollect = menu.stream().max(Comparator.comparingInt(Dish::getCalories));

        Integer collect1 = menu.stream().collect(Collectors.collectingAndThen(toList(), t -> t.size()));

        /** @desc 根据指定Type分类*/
        Map<Dish.Type, List<Dish>> collect2 = menu.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println(collect2);
        /** @desc 根据指定Type分类,并计算分类的平均calories*/
        Map<Dish.Type, Double> collect3 = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.averagingInt(Dish::getCalories)));

        /** @desc 根据指定Type分类,并计算分类的最大*/
        Map<Dish.Type, Optional<Dish>> collect4 = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.maxBy(Comparator.comparing(Dish::getCalories))));
        System.out.println(collect4);

        /** @desc 计算总和 */
        Optional<Integer> reduce = menu.stream().map(Dish::getCalories).reduce(Integer::sum);
        reduce.ifPresent(System.out::println);
    }
}
