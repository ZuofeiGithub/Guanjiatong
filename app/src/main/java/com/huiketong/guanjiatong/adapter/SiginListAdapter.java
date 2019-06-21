package com.huiketong.guanjiatong.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.bean.SigninBean;
import com.zly.www.easyrecyclerview.adapter.CommonAdapter;
import com.zly.www.easyrecyclerview.adapter.viewholder.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SiginListAdapter extends CommonAdapter<SigninBean,SiginListAdapter.ViewHolder> {

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(R.layout.sigin_history_item, parent));
    }

    @Override
    public void bindCustomViewHolder(final ViewHolder holder, SigninBean signinBean, final int position) {
        holder.projectTextView.setText(signinBean.getProjectName());
        holder.userTextView.setText(signinBean.getUsername());
        holder.addressTextView.setText(signinBean.getAddress());
        holder.siginTimeTextView.setText(signinBean.getSiginTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.projectName)
        TextView projectTextView;
        @BindView(R.id.userName)
        TextView userTextView;
        @BindView(R.id.address)
        TextView addressTextView;
        @BindView(R.id.siginTime)
        TextView siginTimeTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
