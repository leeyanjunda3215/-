package com.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * <p>
 *消息实体类
 * </p>
 *
 * @author 李焱军
 * @since 2023-04-11
 */
public class Message {
    private static final long serialVersionUID = 1L;
    @TableId(value = "m_id",type = IdType.AUTO)
    private int mId;

    private  int isDelete;

    private int uId;

    private int toId;

    private String message;



    @Override
    public String toString() {
        return "Message{" +
                "mId=" + mId +
                ", isDelete=" + isDelete +
                ", uId=" + uId +
                ", toId=" + toId +
                ", message='" + message + '\'' +
                '}';
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
