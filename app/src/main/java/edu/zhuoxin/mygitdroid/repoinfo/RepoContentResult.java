package edu.zhuoxin.mygitdroid.repoinfo;

/**
 * Created by Administrator on 2016/8/1.
 * 获取 readme 响应结果
 */
public class RepoContentResult {

    private String content;

    /** 需要 base64 进行解码 */
    private String encoding;

    public String getContent() {
        return content;
    }

    public String getEncoding() {
        return encoding;
    }

    //    {
//        "encoding": "base64",
//            "content": "encoded content ..."
//    }
}
