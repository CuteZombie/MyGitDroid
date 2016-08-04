package edu.zhuoxin.mygitdroid.favorite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 * 收藏仓库表
 */
@DatabaseTable(tableName = "favorite_repo")
public class RepoGroupTable {
    /** 设置 id 为主键 */
    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = "NAME")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private static List<RepoGroupTable> defaultRepo;

    public static List<RepoGroupTable> getDefaultRepo(Context context) {
        if (defaultRepo != null) return defaultRepo;
        try {
            InputStream inputStream = context.getAssets().open("repogroup.json");
            String content = IOUtils.toString(inputStream);
            Gson gson = new Gson();
            defaultRepo = gson.fromJson(content,
                    new TypeToken<List<RepoGroupTable>>(){}.getType());
            return defaultRepo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
