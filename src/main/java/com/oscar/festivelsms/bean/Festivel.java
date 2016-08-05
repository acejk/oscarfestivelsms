package com.oscar.festivelsms.bean;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class Festivel {
    private int id;//节日id
    private String name;//节日名称

    public Festivel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
