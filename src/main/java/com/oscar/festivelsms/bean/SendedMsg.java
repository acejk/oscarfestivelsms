package com.oscar.festivelsms.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class SendedMsg {
    private int id;
    private String msg;//短信内容
    private String numbers;//联系人电话
    private String names;//联系人名字
    private String festivalName;//节日名字
    private Date date;//发送日期
    private String dateStr;
    private DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm");

    public static final String TABLE_NAME = "tb_sended_msg";
    public static final String COLUMN_MSG = "msg";
    public static final String COLUMN_NUMBERS = "numbers";
    public static final String COLUMN_NAMES = "names";
    public static final String COLUMN_FESTIVALNAME = "festivalName";
    public static final String COLUMN_DATE = "date";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateStr() {
        dateStr = df.format(date);
        return dateStr;
    }



    public DateFormat getDf() {
        return df;
    }

    public void setDf(DateFormat df) {
        this.df = df;
    }
}
