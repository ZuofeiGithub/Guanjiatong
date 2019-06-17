package com.huiketong.guanjiatong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


    /**
     * 相当于getView方法中创建View和ViewHolder
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        // 获取列表每行的布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.list_tools_item, viewGroup, false);
        Holder holder = new Holder(view);
        // 点击事件一般都写在绑定数据这里，当然写到上边的创建布局时候也是可以的
        return holder;
    }

    /**
     * 数据和View绑定
     * @param holder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        holder.tv.setText(bean.getRows().get(i).getFrontmodulename());
        if(icons.containsKey(bean.getRows().get(i).getFrontmoduleicon())){
            holder.iv.setImageResource(icons.get(bean.getRows().get(i).getFrontmoduleicon()));
        }
        // 点击事件一般都写在绑定数据这里，当然写到上边的创建布局时候也是可以的
        if (mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 这里利用回调来给RecyclerView设置点击事件
                    mItemClickListener.onItemClick(i);
                }
            });
        }
    }


    /**
     * 得到总条数
     * @return
     */
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
    // 利用接口 -> 给RecyclerView设置点击事件
    private ItemClickListener mItemClickListener ;
    public interface ItemClickListener{
         void onItemClick(int position) ;
    }
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener ;

    }
}
