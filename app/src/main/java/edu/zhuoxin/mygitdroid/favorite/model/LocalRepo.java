package edu.zhuoxin.mygitdroid.favorite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 * 本地仓库
 */
@DatabaseTable(tableName = "local_repo")
public class LocalRepo {
    public static final String COLUMN_GROUP_ID = "group_id";

    /** 主键 */
    @DatabaseField(id = true)
    private long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String full_name;

    @DatabaseField
    @SerializedName("avatar_url")
    private String avatar;

    @DatabaseField
    private String description;

    @DatabaseField(columnName = "stars_count")
    @SerializedName("stargazers_count")
    private int stars_count;

    @DatabaseField
    private int forks_count;

    /** 设置 group 为外键 可以为空 */
    @DatabaseField(columnName = COLUMN_GROUP_ID,foreign = true,canBeNull = true)
    @SerializedName("group")
    private RepoGroupTable repoGroup;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars_count() {
        return stars_count;
    }

    public void setStars_count(int stars_count) {
        this.stars_count = stars_count;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public RepoGroupTable getRepoGroup() {
        return repoGroup;
    }

    public void setRepoGroup(RepoGroupTable repoGroup) {
        this.repoGroup = repoGroup;
    }

    /** 获取本地默认仓库数据 */
    public static List<LocalRepo> getDefaultLocalRepos(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("defaultrepos.json");
            String content = IOUtils.toString(inputStream);
            Gson gson = new Gson();
            return gson.fromJson(content,
                    new TypeToken<List<LocalRepo>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    {
//              "id": 10188673,
//            "name": "android-volley",
//            "full_name": "mcxiaoke/android-volley",
//            "description": "DEPRECATED",
//            "stargazers_count": 3879,
//            "forks_count": 1637,
//            "avatar_url": "https://avatars.githubusercontent.com/u/464330?v=3",
//            "group":{
//                  "id": 1,
//                  "name": "网络连接"
//    }
//    }
}
