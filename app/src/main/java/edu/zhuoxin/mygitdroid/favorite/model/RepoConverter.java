package edu.zhuoxin.mygitdroid.favorite.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.zhuoxin.mygitdroid.hotrepo.repolist.modle.Repo;

/**
 * Created by Administrator on 2016/8/4.
 * 为了实现仓库的收藏功能
 * 将Repo(热门仓库)转换为LocalRepo(本地仓库)对象,
 */
public class RepoConverter {

    private RepoConverter() {}

    /**
     * 将Repo(热门仓库)转换为LocalRepo(本地仓库)对象
     * 默认为未分类
     */
    public static @NonNull LocalRepo convert(@NonNull Repo repo) {
        LocalRepo localRepo = new LocalRepo();
        localRepo.setAvatar(repo.getOwner().getAvatar());
        localRepo.setDescription(repo.getDescription());
        localRepo.setFull_name(repo.getFullName());
        localRepo.setId(repo.getId());
        localRepo.setName(repo.getName());
        localRepo.setStars_count(repo.getStarCount());
        localRepo.setForks_count(repo.getForkCount());
        //默认为未分类
        localRepo.setRepoGroup(null);
        return localRepo;
    }

    public static @NonNull
    List<LocalRepo> converAll(@NonNull List<Repo> repos) {
        ArrayList<LocalRepo> localRepos = new ArrayList<LocalRepo>();
        for (Repo repo : repos) {
            localRepos.add(convert(repo));
        }
        return localRepos;
    }
}
