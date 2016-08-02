package edu.zhuoxin.mygitdroid.hotuser.userlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.zhuoxin.mygitdroid.R;

/**
 * Created by Administrator on 2016/8/2.
 *
 */
public class HotUserFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_user,container,false);
    }
}
