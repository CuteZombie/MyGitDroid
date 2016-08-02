package edu.zhuoxin.mygitdroid.network;

import edu.zhuoxin.mygitdroid.hotrepo.repolist.modle.RepoResult;
import edu.zhuoxin.mygitdroid.hotuser.userlist.modle.HotUserResult;
import edu.zhuoxin.mygitdroid.login.modle.AccessTokenResult;
import edu.zhuoxin.mygitdroid.login.modle.User;
import edu.zhuoxin.mygitdroid.repoinfo.RepoContentResult;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/29.
 *
 * Retrofit能将标准的reset接口，用Java接口来描述(通过注解),
 *
 * 通过Retrofit的create方法，去创建Call模型
 */
public interface GitHubApi {

    /** GitHub开发者 申请自己的GitHub账号 */
    String CLIENT_ID = "afc0e729e890d51e4fb4";
    String CLIENT_SECRET = "197512b3a3a0cfe049a12aeb5e103957826bb2e0";

    /** GitHub开发者，申请时填写的(重定向返回时的一个标记) */
    String CALL_BACK = "http";

    /** 授权时申请的可访问域 */
    String AUTH_SCOPE = "user,public_repo,repo";

    /** 授权登录页面（使用WebView来加载） */
    String AUTH_URL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + AUTH_SCOPE;

    /** 获取访问令牌API token api */
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAuthToken(
            @Field("client_id") String client,
            @Field("client_secret") String clientSecret,
            @Field("code") String code);

    /** 获取用户信息 */
    @GET("user")
    Call<User> getUserInfo();

    /***
     * 获取readme
     * @param owner 仓库拥有者
     * @param repo 仓库名称
     * @return 仓库的readme页面内容,将是markdown格式且做了base64处理
     */
    @GET("/repos/{owner}/{repo}/readme")
    Call<RepoContentResult> getReadme(
            @Path("owner") String owner,
            @Path("repo") String repo);

    /***
     * 获取一个markdonw内容对应的HTML页面
     *
     * @param body 请求体,内容来自getReadme后的RepoContentResult
     */
    @Headers({"Content-Type:text/plain"})
    @POST("/markdown/raw")
    Call<ResponseBody> markDown(@Body RequestBody body);

    /**
     * 获取仓库
     * @param query 查询参数（language ： java）
     * @param pageId 查询页数据（从1开始）
     * */
    @GET("/search/repositories")
    Call<RepoResult> searchRepos(
            @Query("q")String query,
            @Query("page")int pageId);

    /**
     * 获取仓库
     * @param query 查询参数（user_name）
     * @param repos 用户仓库数量
     * */
    @GET("/search/users")
    Call<HotUserResult> searchUsers(
            @Query("q")String query,
            @Query("repositories")int repos);
}
