package com.fline.list;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestArrayList {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.add("aaa");
        list.add("aaaa");
        list.add("eee");
        list.add("bbb");
        list.add("ccc");

        List<Long> listb = new ArrayList<>();
        listb.add(1L);
        listb.add(2L);
        List<Long> listc = new ArrayList<>();
        listc.add(1L);


       /* StringBuilder builder = new StringBuilder();
        for(String str : list) {
            // 如果不存在返回 -1。
            if(builder.indexOf(","+str+",") > -1) {
                System.out.println("重复的有："+str);
            } else {
                builder.append(",").append(str).append(",");
            }
        }*/
        List<String> duplicateElements = getDuplicateElements(list);
        listb.containsAll(listc);
        System.out.println(duplicateElements);
    }


    //java 8 方法，优雅
    public static <E> List<E> getDuplicateElements(List<E> list) {
        return list.stream()                              // list 对应的 Stream
                .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet().stream()                   // 所有 entry 对应的 Stream
                .filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
                .map(entry -> entry.getKey())          // 获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList());         // 转化为 List
    }
}
