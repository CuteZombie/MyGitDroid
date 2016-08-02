package edu.zhuoxin.mygitdroid.hotuser.userlist.modle;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 * 搜索流行开发者响应结果
 */
public class HotUserResult {

    private int total_count;

    private boolean incomplete_results;

    private List<HotUser> items;

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public int getTotal_count() {
        return total_count;
    }

    public List<HotUser> getItems() {
        return items;
    }


    //    "total_count": 12,
//            "incomplete_results": false,
//            "items": [{}]

}
