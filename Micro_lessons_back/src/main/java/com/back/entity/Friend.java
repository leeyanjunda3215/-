package com.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * <p>
 *
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-30
 */
public class Friend {
    private static final long serialVersionUID = 1L;
    @TableId(value = "f_id",type = IdType.AUTO)
    private int fId;

    private int userId;

    private int followId;

    private int isDelete;

    @Override
    public String toString() {
        return "Friend{" +
                "fId=" + fId +
                ", userId=" + userId +
                ", followId=" + followId +
                ", isDelete=" + isDelete +
                '}';
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }
}
