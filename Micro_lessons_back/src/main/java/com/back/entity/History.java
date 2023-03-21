package com.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class History {
    private static final long serialVersionUID = 1L;
    @TableId(value = "h_id", type = IdType.AUTO)
    private int hId;
    private int isDelete;
    private int vId;
    private int uId;
    private String createtime;

    @Override
    public String toString() {
        return "History{" +
                "hId=" + hId +
                ", isDelete=" + isDelete +
                ", vId=" + vId +
                ", uId=" + uId +
                ", createtime='" + createtime + '\'' +
                '}';
    }

    public int gethId() {
        return hId;
    }

    public void sethId(int hId) {
        this.hId = hId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
