package com.oscar.festivelsms.bean;

/**
 * Created by Administrator on 2016/7/23 0023.
 */
public class Msg {
    private int id;//短信id
    private int fesId;//节日id
    private String content;//短息内容

    public Msg(int id, int fesId, String content) {
        this.id = id;
        this.fesId = fesId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFesId() {
        return fesId;
    }

    public void setFesId(int fesId) {
        this.fesId = fesId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
