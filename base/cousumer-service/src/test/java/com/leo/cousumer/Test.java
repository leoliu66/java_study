package com.leo.cousumer;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName Test
 * @Description test
 * @Author liulu_leo
 * @Date 2020/9/17
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) {
        List<String> strings = new ArrayList<String>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("5");
        strings.add("6");
        Iterator<String> iterator = strings.iterator();
        while(iterator.hasNext()){
            String s = iterator.next();
            if(StringUtils.equals(s, "1")){
                iterator.remove();
                continue;
            }
            if(StringUtils.equals(s, "1") ||StringUtils.equals(s, "2")||StringUtils.equals(s, "3")){
                iterator.remove();
            }
        }
        System.out.println(strings);

    }
}
