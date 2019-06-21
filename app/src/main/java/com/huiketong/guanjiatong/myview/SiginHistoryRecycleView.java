package com.huiketong.guanjiatong.myview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.zly.www.easyrecyclerview.EasyRecyclerView;
import com.zly.www.easyrecyclerview.footer.ErvDefaultFooter;
import com.zly.www.easyrecyclerview.ptrlib.PtrClassicDefaultHeader;

public class SiginHistoryRecycleView extends EasyRecyclerView {

    private PtrClassicDefaultHeader mPtrClassicHeader;
    public SiginHistoryRecycleView(Context context) {
        this(context, null);;

    }

    public SiginHistoryRecycleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);;

    }

    public SiginHistoryRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setHeaderView(mPtrClassicHeader = new PtrClassicDefaultHeader(context));
        setFooterView(new ErvDefaultFooter(context));
        setLayoutManager(new LinearLayoutManager(getContext()));
        setOnEmptyViewClick(new OnClickListener() {
            @Override
            public void onClick(View v) {
                autoRefresh();
            }
        });
    }

    public void setLastUpdateTimeKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    public void setLastUpdateTimeRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }


}
