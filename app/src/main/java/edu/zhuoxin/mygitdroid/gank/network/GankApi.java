package edu.zhuoxin.mygitdroid.gank.network;

import edu.zhuoxin.mygitdroid.gank.model.GankResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/8/5.
 * 接口来自代码家的干货集中营：http://gank.io/api。
 */
public interface GankApi {

    String ENDPOINT = "http://gank.io/api/";

    @GET("day{year}/{month}/{day}")
    Call<GankResult> getDailyData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);
}
