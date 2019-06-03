package com.huiketong.guanjiatong.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.huiketong.guanjiatong.R;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 工具类
 */
public class Utils {
    // 存储文件名
    private static String SHARED_FILE_NAME = "shared";
    static Toast toast = null;

    /**
     * 显示Toast
     *
     * @param context 上下文
     * @param msg     提示信息
     */
    public static void Toast(Context context, String msg) {
        try {
            if (toast != null) {
                toast.setText(msg);
            } else {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            }
            toast.show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    /**
     * 设置存储信息
     *
     * @param context 上下文
     * @param key     键名
     * @param value   键值
     */
    public static void setShared(Context context, String key, Object value) {
        String name = value.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        switch (name) {
            case "String":
                editor.putString(key, (String) value);
                break;
            case "Float":
                editor.putFloat(key, (Float) value);
                break;
            case "Integer":
                editor.putInt(key, (Integer) value);
                break;
            case "Long":
                editor.putLong(key, (Long) value);
                break;
        }
        editor.apply();
    }

    /**设置存储信息
     *
     * @param context
     * @param share
     */
    public static void setShared(Context context, Map<String,Object> share) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Object value;
        String name;
        Set<String> keys = share.keySet();
        for (String key : keys){
            value = share.get(key);
            name = value.getClass().getSimpleName();
            switch (name) {
                case "String":
                    editor.putString(key, (String) value);
                    break;
                case "Float":
                    editor.putFloat(key, (Float) value);
                    break;
                case "Integer":
                    editor.putInt(key, (Integer) value);
                    break;
                case "Long":
                    editor.putLong(key, (Long) value);
                    break;
            }
        }
        editor.apply();
    }

    /**
     * 获取存储参数
     *
     * @param context       上下文
     * @param key           键名
     * @param defaultObject 默认值
     * @return
     */
    public static Object getShared(Context context, String key, Object defaultObject) {
        String name = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);
        switch (name) {
            case "String":
                return sp.getString(key, (String) defaultObject);
            case "Boolean":
                return sp.getBoolean(key, (Boolean) defaultObject);
            case "Float":
                return sp.getFloat(key, (Float) defaultObject);
            case "Integer":
                return sp.getInt(key, (Integer) defaultObject);
            case "Long":
                return sp.getLong(key, (Long) defaultObject);
        }
        return defaultObject;
    }

    /**
     * 清除所有存储数据
     *
     * @param context
     */
    public static void clearShare(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 清除指定数据
     * @param context
     * @param key
     */
    public static void removeShare(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();

    }

    /**
     * 根据手机的分辨率从dip转成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px的单位转成为dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int pxToDpi(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置listview高度
     *
     * @param listView
     */
    public static void setListViewHeight(ListView listView) {
        try {
            // 获取ListView对应的Adapter
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
                // listAdapter.getCount()返回数据项的数目
                View listItem = listAdapter.getView(i, null, listView);
                // 计算子项View 的宽高
                listItem.measure(0, 0);
                // 统计所有子项的总高度
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            // listView.getDividerHeight()获取子项间分隔符占用的高度
            // params.height最后得到整个ListView完整显示需要的高度
            listView.setLayoutParams(params);
        } catch (Exception e) {
            Logger.e("setListViewHeightBasedOnChildren___" + e.toString());
        }
    }

    /**
     * 银行卡号加星号处理
     *
     * @param bankCard
     * @return
     */
    public static String bankCardFormat(String bankCard) {
        int showCount = bankCard.length() % 4;
        if (showCount == 0) {
            showCount = 4;
        }
        int starCount = bankCard.length() - showCount;

        String star = "";
        for (int i = 1; i <= starCount; i++) {
            star += "*";
            if (i % 4 == 0) {
                star += " ";
            }
        }
        return star += bankCard.substring(starCount);
    }

    /**
     * Picker基础属性设置
     *
     * @param context
     * @param listener
     * @return
     */
    public static OptionsPickerBuilder pickerBuilder(Context context, OnOptionsSelectListener listener) {
        return new OptionsPickerBuilder(context, listener).setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 0)//默认选中项
                .setBgColor(context.getResources().getColor(R.color.pickerBgColor))
                .setTitleBgColor(context.getResources().getColor(R.color.pickerTitleBgColor))
                .setTitleColor(context.getResources().getColor(R.color.pickerTitleColor))
                .setCancelColor(context.getResources().getColor(R.color.pickerCancelColor))
                .setSubmitColor(context.getResources().getColor(R.color.pickerSubmitColor))
                .setTextColorCenter(context.getResources().getColor(R.color.pickerTextColorCenter))
                .setCancelText("取消")
                .setSubmitText("确认");

//                .setTitleText("城市选择")
//                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setLabels("省", "市", "区")
    }

    /**
     * 读取assets中的json文件
     *
     * @param fileName 文件名
     * @param context  上下文
     * @return
     */
    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 将bitmap转成file
     *
     * @param bitmap
     * @return
     */
    public static File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/temp/" + (new Date().getTime()) + ".jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    /**
     * 根据手机的分辨率从dip转成px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dpToPx(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale +0.5f);
    }

    /**
     * 根据手机的分辨率从px的单位转成为dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int pxToDp(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue /scale + 0.5f);
    }

}
