package com.example.demo.yuanxing;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @ClassName Thing
 * @Description
 * @Author liulu_leo
 * @Date 2021/3/18
 * @Version 1.0
 */
public class Thing implements Cloneable{

    private ArrayList<String> list = new ArrayList();

    @Override
    public Thing clone(){
        Thing thing = null;
        try {
            thing = (Thing) super.clone();
            this.list = (ArrayList<String>)this.list.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return thing;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(String list) {
        this.list.add(list);
    }

}
