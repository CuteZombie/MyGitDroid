package edu.zhuoxin.mygitdroid.favorite.dao;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import edu.zhuoxin.mygitdroid.favorite.model.RepoGroupTable;

/**
 * Created by Administrator on 2016/8/3.
 * 仓库类别表的 dao
 */
public class RepoGroupDao {

    private Dao<RepoGroupTable, Long> dao;

    public RepoGroupDao(DBHelper helper) {
        try {
            //创建仓库类别的dao
            dao = helper.getDao(RepoGroupTable.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 添加和更新喜爱仓库表 */
    public void createOrUpdate(RepoGroupTable table) {
        try {
            dao.createOrUpdate(table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 添加和更新喜爱仓库表 */
    public void createOrUpdate(List<RepoGroupTable> list) {
        for (RepoGroupTable table : list) {
            createOrUpdate(table);
        }
    }

    /** 通过 id 查询 */
    public RepoGroupTable queryForId(long id) {
        try {
           return dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 查询所有 */
    public List<RepoGroupTable> queryForAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }
}
