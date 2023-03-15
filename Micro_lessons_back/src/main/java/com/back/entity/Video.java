package com.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-18
 */
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "v_id",type = IdType.AUTO)
    private Integer vId;

    private String brief;

    private String vName;

    private Integer isDelete;

    private String videopath;

    private String classify;

    private Integer times;

    private String tag;

    private Integer episodes;

    private String cover;

    private int userId;

    @Override
    public String toString() {
        return "Video{" +
                "vId=" + vId +
                ", brief='" + brief + '\'' +
                ", vName='" + vName + '\'' +
                ", isDelete=" + isDelete +
                ", videopath='" + videopath + '\'' +
                ", classify='" + classify + '\'' +
                ", times=" + times +
                ", tag='" + tag + '\'' +
                ", episodes=" + episodes +
                ", cover='" + cover + '\'' +
                ", userId=" + userId +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Integer getvId() {
        return vId;
    }

    public void setvId(Integer vId) {
        this.vId = vId;
    }
    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath;
    }
    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }
    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


}
