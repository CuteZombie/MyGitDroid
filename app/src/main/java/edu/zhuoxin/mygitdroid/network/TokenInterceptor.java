package edu.zhuoxin.mygitdroid.network;

import java.io.IOException;

import edu.zhuoxin.mygitdroid.login.UserRepo;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/8/1.
 * Token拦截器
 */
public class TokenInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {

        /** 每次请求都加一个token值 */
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        /** 判断是否有token（注意 token 后面有个空格） */
        if (UserRepo.hasAccessToken()) {
            builder.header("Authorization", "token " + UserRepo.getAccessToken());
        }
        Response response = chain.proceed(builder.build());

        /** 响应成功 */
        if (response.isSuccessful()) {
            return response;
        }

        /** 响应出错 */
        if (response.code() == 401 || response.code() == 403) {
            throw new IOException("未经授权的！限制是每分钟10次！");
        } else {
            throw new IOException("响应码:" + response.code());
        }
    }
}
