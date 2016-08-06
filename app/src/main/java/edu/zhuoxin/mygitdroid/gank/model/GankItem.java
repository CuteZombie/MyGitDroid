package edu.zhuoxin.mygitdroid.gank.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Administrator on 2016/8/5.
 *
 * {
 *   "_id":"56cc6d23421aa95caa707a69",
 *   "createdAt":"2015-08-06T07:15:52.65Z",
 *   "desc":"类似Link Bubble的悬浮式操作设计",
 *   "publishedAt":"2015-08-07T03:57:48.45Z",
 *   "type":"Android",
 *   "url":"https://github.com/recruit-lifestyle/FloatingView",
 *   "used":true,
 *   "who":"Tom"
 * }
 */
public class GankItem {

    @SerializedName("_id")
    private String objectId;

    private Date createdAt;

    private String desc;

    private Date publishedAt;

    private String type;

    private String url;

    private boolean used;

    private String who;

    public String getObjectId() {
        return objectId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean isUsed() {
        return used;
    }

    public String getWho() {
        return who;
    }
}
