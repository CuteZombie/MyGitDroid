package edu.zhuoxin.mygitdroid.hotuser.userlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.mygitdroid.R;
import edu.zhuoxin.mygitdroid.login.modle.User;

/**
 * Created by Administrator on 2016/8/2.
 *
 */
public class HotUserAdapter extends BaseAdapter{

    private final List<User> datas;

    public HotUserAdapter() {
        datas = new ArrayList<User>();
    }

    public void addAll(Collection<User> users) {
        datas.addAll(users);
        notifyDataSetChanged();
    }

    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public User getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.layout_item_user,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        User user = getItem(position);
        viewHolder.user_name.setText(user.getLogin());
        ImageLoader.getInstance().displayImage(user.getAvatar(),viewHolder.user_icon);
        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.user_icon)ImageView user_icon;
        @BindView(R.id.user_name)TextView user_name;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
