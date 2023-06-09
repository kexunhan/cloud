package cn.kolmap.exercise9999;

import org.junit.platform.commons.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : kxhan
 * @create 2023/6/9 08:58
 */
public class ReduceTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("java", "c", "c++", "go", "python", "spark", "hadoop"));

        List<Integer> collect = list.stream().map(e -> e.length()).collect(Collectors.toList());

        List<Integer> collect1 = collect.stream().sorted((a, b) -> {
            int c = a > b ? 0 : -1;
            return c;
        }).collect(Collectors.toList());

        Integer integer = collect.stream().reduce((a, b) -> a += b).get();
        System.out.println(integer);
        List<Integer> list1 = new ArrayList<>(Arrays.asList(12,13,24));




    }


    static class A implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            int a = o1 > o2 ? 0 : -1;
            return a;
        }
    }
}
