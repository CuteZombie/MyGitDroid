package edu.zhuoxin.mygitdroid.hotuser.userlist.modle;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/2.
 *
 */
public class HotUser implements Serializable{

    /** 开发者姓名 */
    private String login;

    private int id;

    /** 开发者头像路径 */
    private String avatar_url;

    /** 用户类型 */
    private String type;

    private float score;

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    //        {
//            "login": "mojombo",
//                "id": 1,
//                "avatar_url": "https://secure.gravatar.com/avatar/25c7c18223fb42a4c6ae1c8db6f50f9b?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
//                "type": "User",
//                "score": 105.47857
//        }
}
