package com.huiketong.guanjiatong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.bean.SigninBean;

import java.util.List;

public class SiginListAdapter extends BaseAdapter {
    public List<SigninBean> list;
    public LayoutInflater inflater;

    public SiginListAdapter(Context context,List<SigninBean> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if(convertView == null){
            view = inflater.inflate(R.layout.sigin_history_item,null);
            holder = new ViewHolder();
            holder.projectName = view.findViewById(R.id.projectName);
            holder.userName = view.findViewById(R.id.userName);
            holder.address = view.findViewById(R.id.address);
            holder.siginTime = view.findViewById(R.id.siginTime);
            view.setTag(holder); //为了复用holder
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        //给控件赋值
        holder.projectName.setText(list.get(position).getProjectName());
        holder.userName.setText(list.get(position).getUsername());
        holder.address.setText(list.get(position).getAddress());
        holder.siginTime.setText(list.get(position).getSiginTime());
        return view;
    }

    /**
     * 强制动态刷新数据进而调用getView方法
     * @param nowList
     */
    public void updateView(List<SigninBean> nowList){
        this.list = nowList;
        this.notifyDataSetChanged();
    }

    static class ViewHolder
    {
        TextView projectName;
        TextView userName;
        TextView address;
        TextView siginTime;
    }
}
