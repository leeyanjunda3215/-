package com.back.dto;

public class FollowDTO {
    private int follow;
    private int fan;

    @Override
    public String toString() {
        return "followDTO{" +
                "follow=" + follow +
                ", fan=" + fan +
                '}';
    }

    public int getFan() {
        return fan;
    }

    public void setFan(int fan) {
        this.fan = fan;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }
}
