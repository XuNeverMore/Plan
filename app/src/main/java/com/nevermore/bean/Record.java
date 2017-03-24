package com.nevermore.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/3/11.
 */
@Entity
public class Record implements MultiItemEntity{
    @Id
    private long id;

    private long time;

    private String text;

    private int type;

    private String color;

    private String imgpath;

    public String getImgpath() {
        return this.imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Generated(hash = 551272113)
    public Record(long id, long time, String text, int type, String color,
            String imgpath) {
        this.id = id;
        this.time = time;
        this.text = text;
        this.type = type;
        this.color = color;
        this.imgpath = imgpath;
    }

    @Generated(hash = 477726293)
    public Record() {
    }

    @Override
    public int getItemType() {
        return type;
    }
}
