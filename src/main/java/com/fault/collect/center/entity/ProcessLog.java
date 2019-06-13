package com.fault.collect.center.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProcessLog {
    int id;
    Date date;
    String content;
    String dateStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(this.date);
        return strDate;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public String toString() {
        return "date: " + dateStr + "content: " + content;
    }
}
