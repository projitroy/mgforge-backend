package com.mgforge.MGForge.dto;

public class PageInput {

    private Integer first;
    private String after;

    public PageInput() {
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }
}
