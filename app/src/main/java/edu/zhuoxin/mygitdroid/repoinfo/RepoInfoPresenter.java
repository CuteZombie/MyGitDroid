package edu.zhuoxin.mygitdroid.repoinfo;

import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.IOException;

import edu.zhuoxin.mygitdroid.hotrepo.repolist.modle.Repo;
import edu.zhuoxin.mygitdroid.network.GitHubClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/1.
 * 业务（视图业务和逻辑业务）
 */
public class RepoInfoPresenter {

    /** 视图接口 */
    public interface RepoInfoView {
        void showProgress();

        void hideProgress();

        void showMessage(String msg);

        void setData(String htmlContent);
    }

    private RepoInfoView repoInfoView;

    private Call<RepoContentResult> repoContentCall;

    private Call<ResponseBody> mdhtmlCall;

    public RepoInfoPresenter(@NonNull RepoInfoView repoInfoView) {
        this.repoInfoView = repoInfoView;
    }

    public void getReadme(Repo repo) {
        repoInfoView.showProgress();

        String login = repo.getOwner().getLogin();
        String name = repo.getName();
        if (repoContentCall != null) {
            repoContentCall.cancel();
        }
        repoContentCall = GitHubClient.getInstance().getReadme(login,name);
        repoContentCall.enqueue(repoContentCallback);
    }

    private Callback<RepoContentResult> repoContentCallback = new Callback<RepoContentResult>() {
        @Override
        public void onResponse(Call<RepoContentResult> call, Response<RepoContentResult> response) {
            String content = response.body().getContent();
            /** Base64解码 */
            byte[] data = Base64.decode(content,Base64.DEFAULT);
            /** 根据data获取到markdown（也就是readme文件）的HTML格式文件 */
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType,data);
            if (mdhtmlCall != null) {
                mdhtmlCall.cancel();
            }
            mdhtmlCall = GitHubClient.getInstance().markDown(body);
            mdhtmlCall.enqueue(mdhtmlCallback);
        }

        @Override
        public void onFailure(Call<RepoContentResult> call, Throwable t) {
            repoInfoView.hideProgress();
            repoInfoView.showMessage(t.getMessage());
        }
    };

    private Callback<ResponseBody> mdhtmlCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            repoInfoView.hideProgress();
            try {
                String htmlContent = response.body().string();
                repoInfoView.setData(htmlContent);
            } catch (IOException e) {
                onFailure(call,e);
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            repoInfoView.hideProgress();
            repoInfoView.showMessage(t.getMessage());
        }
    };
}
