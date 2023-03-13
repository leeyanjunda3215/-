package com.back.dto;

import java.lang.reflect.Array;
import java.util.List;

public class VideoForm {
    private String vName;
    private String brief;
    private List classify;

    @Override
    public String toString() {
        return "VideoForm{" +
                "vName='" + vName + '\'' +
                ", brief='" + brief + '\'' +
                ", classify=" + classify +
                '}';
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public List getClassify() {
        return classify;
    }

    public void setClassify(List classify) {
        this.classify = classify;
    }
}
