package com.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * <p>
 *
 * </p>
 *
 * @author 李焱军
 * @since 2023-02-09
 */
public class Liked {

    private static final long serialVersionUID = 1L;
    @TableId(value = "l_id",type = IdType.AUTO)
    private int lId;



    private int vId;

    private int uId;

    private int liked;

    private int collectioned;

    @Override
    public String toString() {
        return "Like{" +
                "lId=" + lId +
                ", vId=" + vId +
                ", uId=" + uId +
                ", liked=" + liked +
                ", collectioned=" + collectioned +
                '}';
    }

    public int getlId() {
        return lId;
    }

    public void setlId(int lId) {
        this.lId = lId;
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

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getCollectioned() {
        return collectioned;
    }

    public void setCollectioned(int collectioned) {
        this.collectioned = collectioned;
    }
}
