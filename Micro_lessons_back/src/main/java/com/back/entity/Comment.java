package com.back.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;

/**
 * <p>
 *
 * </p>
 *
 * @author 李焱军
 * @since 2023-02-08
 */
public class Comment {
    private static final long serialVersionUID = 1L;
    @TableId(value = "c_id",type = IdType.AUTO)
    private int cId;
    private String content;
    private String createTime;
    private int isDelete;
    private int userId;
    private int videoId;
    private int likecount;
    private int rootcomment;
    private int tocomment;

    private String username;

    private String headimg;

    @Override
    public String toString() {
        return "Comment{" +
                "cId=" + cId +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", isDelete=" + isDelete +
                ", userId=" + userId +
                ", videoId=" + videoId +
                ", likecount=" + likecount +
                ", rootcomment=" + rootcomment +
                ", tocomment=" + tocomment +
                ", username='" + username + '\'' +
                ", headimg='" + headimg + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getLikecount() {
        return likecount;
    }

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }

    public int getRootcomment() {
        return rootcomment;
    }

    public void setRootcomment(int rootcomment) {
        this.rootcomment = rootcomment;
    }

    public int getTocomment() {
        return tocomment;
    }

    public void setTocomment(int tocomment) {
        this.tocomment = tocomment;
    }
}
