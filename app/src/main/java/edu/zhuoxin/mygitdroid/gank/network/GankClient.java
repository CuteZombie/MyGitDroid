package edu.zhuoxin.mygitdroid.gank.network;

import edu.zhuoxin.mygitdroid.commons.LoggingInterceptor;
import edu.zhuoxin.mygitdroid.gank.model.GankResult;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/8/5.
 *
 */
public class GankClient implements GankApi{

    private static GankClient mGankClient;

    public static GankClient getmGankClient() {
        if (mGankClient == null) {
            mGankClient = new GankClient();
        }
        return mGankClient;
    }

    private final GankApi gankApi;

    private GankClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gankApi = retrofit.create(GankApi.class);
    }

    @Override
    public Call<GankResult> getDailyData(int year, int month, int day) {
        return gankApi.getDailyData(year, month, day);
    }


}
