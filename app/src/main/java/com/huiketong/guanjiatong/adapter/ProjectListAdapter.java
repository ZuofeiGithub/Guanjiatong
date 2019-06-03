package com.huiketong.guanjiatong.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.bean.ProjectListItemBean;
import com.huiketong.guanjiatong.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.List;

public class ProjectListAdapter extends BaseExpandableListAdapter {
    private Context context;
    public List<String> group;
    public List<ProjectListItemBean> item;
    public ProjectListAdapter(Context context) {
        this.context = context;
    }

    /**
     * 获取分组的个数
     * @return
     */
    @Override
    public int getGroupCount() {
        return group.size();
    }
    //获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return item.get(groupPosition).getRows().size();
    }
    //获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }
    //获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return item.get(groupPosition).getRows().get(childPosition);
    }
    //获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    //获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 获取显示指定组的视图对象
     * @param groupPosition  组位置
     * @param isExpanded 该组是展开状态还是伸缩状态
     * @param convertView 重用已有的视图对象
     * @param parent 返回的视图对象始终依附于的视图组
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_project_group,parent,false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = (TextView)convertView.findViewById(R.id.home_group);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder)convertView.getTag();
        }
        groupViewHolder.tvTitle.setText(group.get(groupPosition));
        return convertView;
    }
    /**
     *
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild 子元素是否处于组中的最后一个
     * @param convertView 重用已有的视图(View)对象
     * @param parent 返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, View,
     *      ViewGroup)
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_project_item,parent,false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.layout = convertView.findViewById(R.id.layout);
            childViewHolder.logogram = convertView.findViewById(R.id.logogram);
            childViewHolder.title = convertView.findViewById(R.id.title);
            childViewHolder.progress_text = convertView.findViewById(R.id.progress_text);
            childViewHolder.remind = convertView.findViewById(R.id.remind);
            childViewHolder.badge = convertView.findViewById(R.id.badge);
            childViewHolder.progress = convertView.findViewById(R.id.progress);
            convertView.setTag(childViewHolder);

        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.logogram.setText(item.get(groupPosition).getRows().get(childPosition).getProjectname());
        childViewHolder.title.setText(item.get(groupPosition).getRows().get(childPosition).getProjectname() + item.get(groupPosition).getRows().get(childPosition).getHousenumber());
        childViewHolder.progress_text.setText(String.valueOf(item.get(groupPosition).getRows().get(childPosition).getProjectprocess())+ "%");
        childViewHolder.badge.setText(String.valueOf(item.get(groupPosition).getRows().get(childPosition).getTipcount()));
        //给提醒按钮设置点击事件，跳转页面
        childViewHolder.remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转提醒
                Logger.d("提醒");
//                Intent intent = new Intent(context, MyRemindActivity.class);
//                intent.putExtra("projectCode",item.get(groupPosition).getRows().get(childPosition).getProjectcode());
//                context.startActivity(intent);
            }
        });
        childViewHolder.progress.setProgress(item.get(groupPosition).getRows().get(childPosition).getProjectprocess());
        if(childPosition + 1 == item.get(groupPosition).getRows().size()){
            childViewHolder.layout.setPadding(0,0,0,0);
        }else{
            childViewHolder.layout.setPadding(0,0,0,Utils.dpToPx(context,12));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvTitle;
    }

    static class ChildViewHolder {
        LinearLayout layout;    //子项
        TextView logogram;  //标题
        TextView title;     //标题
        TextView progress_text; //进度
        Button remind;  //提醒按钮
        TextView badge; //角标
        ProgressBar progress; //进度条
    }
}
