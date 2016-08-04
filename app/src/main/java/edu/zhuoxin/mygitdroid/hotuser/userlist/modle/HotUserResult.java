package edu.zhuoxin.mygitdroid.hotuser.userlist.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import edu.zhuoxin.mygitdroid.login.modle.User;

/**
 * Created by Administrator on 2016/8/2.
 * 搜索流行开发者响应结果
 */
public class HotUserResult {

    private int total_count;

    private boolean incomplete_results;

    @SerializedName("items")
    private List<User> users;

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public int getTotal_count() {
        return total_count;
    }

    public List<User> getAllUsers() {
        return users;
    }


    //    "total_count": 12,
//            "incomplete_results": false,
//            "items": [{}]

}
