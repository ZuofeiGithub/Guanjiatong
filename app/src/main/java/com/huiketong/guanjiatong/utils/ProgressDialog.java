package com.huiketong.guanjiatong.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huiketong.guanjiatong.R;

/**
 * 进度对话框
 */
public class ProgressDialog {
    private static ProgressDialog progressDialog;

    private Dialog mDialog;

    public static ProgressDialog getInstance() {
        if (progressDialog == null) {
            synchronized (ProgressDialog.class) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog();
                }
            }
        }
        return progressDialog;
    }


    private Dialog createProgressDialog(Context context,String message) {
        //创建对话框(为对话框设置样式)
        Dialog mDialog = new Dialog(context, R.style.myDialog);
        //指定对话框的布局
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null, false);
        //为对话框中的控件填充内容（如文字）
        TextView tvMessage = (TextView) view.findViewById(R.id.message);
        tvMessage.setText(message);
        //为对话框设置布局
        mDialog.setContentView(view);
        //设置触屏不会取消
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    //显示自定义等待对话框
    public void showDialog(Context context,String message){
        //若不存在，则创建
        if (mDialog == null) {
            mDialog = createProgressDialog(context,message);
        }
        //若没有正在显示，则显示
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }
    //显示自定义等待对话框
    public void showDialog(Context context){
        //若不存在，则创建
        if (mDialog == null) {
            mDialog = createProgressDialog(context,"正在请求...");
        }
        //若没有正在显示，则显示
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    //关闭自定义等待对话框
    public void closeDialog(){
        //若存在，且正在显示中，则关闭
        if (mDialog!=null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        //释放内存
        mDialog = null;
    }

}
