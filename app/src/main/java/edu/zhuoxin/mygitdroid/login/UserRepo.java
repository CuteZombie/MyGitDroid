package edu.zhuoxin.mygitdroid.login;

import edu.zhuoxin.mygitdroid.login.modle.User;

/**
 * Created by Administrator on 2016/8/1.
 * 此类是一个用来缓存当前用户信息的，极简单的实现
 * 我们就是强用来保存
 */
public class UserRepo {

    private UserRepo(){}

    private static String accessToken;

    private static User user;

    /** 判断当前是否有token */
    public static boolean hasAccessToken(){
        return accessToken != null;
    }

    /** 判断当前是否是"空的"(还没有登录过) */
    public static boolean isEmpty(){
        return accessToken == null || user == null;
    }

    /** 清除信息 */
    public static void clear(){
        accessToken = null;
        user = null;
    }

    public static void setAccessToken(String accessToken) {
        UserRepo.accessToken = accessToken;
    }

    public static void setUser(User user) {
        UserRepo.user = user;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static User getUser() {
        return user;
    }
}
