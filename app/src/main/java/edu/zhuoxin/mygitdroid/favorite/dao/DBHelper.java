package edu.zhuoxin.mygitdroid.favorite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import edu.zhuoxin.mygitdroid.favorite.model.LocalRepo;
import edu.zhuoxin.mygitdroid.favorite.model.RepoGroupTable;

/**
 * Created by Administrator on 2016/8/3.
 *
 */
public class DBHelper extends OrmLiteSqliteOpenHelper{

    private static final String TABLE_NAME = "favorite_repo.db";
    private static final int VERSION = 1;
    private Context context;

    private DBHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION);
        this.context = context;
    }
    private static DBHelper helper;

    public static synchronized DBHelper getInstance(Context context) {
        if (helper == null) {
            helper = new DBHelper(context.getApplicationContext());
        }
        return helper;
    }

    /** 创建表 */
    @Override
    public void onCreate(SQLiteDatabase database,
                         ConnectionSource connectionSource) {
        try {
            //创建表（里面还没有数据）
            TableUtils.createTableIfNotExists(connectionSource, RepoGroupTable.class);
            TableUtils.createTableIfNotExists(connectionSource, LocalRepo.class);
            //把默认数据添加到表里
            new RepoGroupDao(this).createOrUpdate(RepoGroupTable.getDefaultRepo(context));
            new LocalRepoDao(this).createOrUpdate(LocalRepo.getDefaultLocalRepos(context));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, RepoGroupTable.class, true);
            TableUtils.dropTable(connectionSource, LocalRepo.class, true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
