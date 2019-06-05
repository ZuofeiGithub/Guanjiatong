package com.huiketong.guanjiatong.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.bean.ModuleBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 模块
 */
public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.Holder> {

    private ModuleBean bean;

    private Context context;
    private Map<String, Integer> icons;

    public ModuleAdapter(ModuleBean bean, Context context) {
        this.bean = bean;
        this.context = context;
        icons = new HashMap<>();
        icons.put("punish-circle",R.drawable.icon_punish_circle);
        icons.put("cart1",R.drawable.icon_cart1);
        icons.put("yanqi1",R.drawable.icon_yanqi1);
        icons.put("shouru",R.drawable.icon_shouru);
        icons.put("live",R.drawable.icon_live);
        icons.put("shenhe1",R.drawable.icon_shenhe1);
        icons.put("anli",R.drawable.icon_anli);
        icons.put("shoucang",R.drawable.icon_shoucang);
        icons.put("shangcheng",R.drawable.icon_shangcheng);
        icons.put("daoru",R.drawable.icon_daoru);
        icons.put("xiangmuxg",R.drawable.icon_xiangmuxg);
        icons.put("cailiao",R.drawable.icon_cailiao);
        icons.put("task",R.drawable.icon_task);
        icons.put("dangan",R.drawable.icon_dangan);
        icons.put("qiandao1",R.drawable.icon_qiandao1);
        icons.put("information",R.drawable.icon_information);
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // 获取列表每行的布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.list_tools_item, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.tv.setText(bean.getRows().get(i).getFrontmodulename());
        if(icons.containsKey(bean.getRows().get(i).getFrontmoduleicon())){
            holder.iv.setImageResource(icons.get(bean.getRows().get(i).getFrontmoduleicon()));
        }
    }


    @Override
    public int getItemCount() {
        return bean.getRows().size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_icon);
            tv = itemView.findViewById(R.id.tv_name);
        }
    }
}
