package com.huiketong.guanjiatong.activity;

import android.app.ActionBar;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.fragment.ClientFragment;
import com.huiketong.guanjiatong.fragment.HomeFragment;
import com.huiketong.guanjiatong.fragment.MyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * 首页activity
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_client)
    RadioButton rbClient;
    @BindView(R.id.rb_my)
    RadioButton rbMy;
    private FragmentManager fragmentManager;

    enum Page {HOME, CLIENT, MY}

    private Page currentPage;

    private HomeFragment homeFragment;
    private ClientFragment clientFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        currentPage = Page.HOME;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setRadioButtonDrawable();
        switch (currentPage) {
            case HOME:
                radioGroup.check(R.id.rb_home);
                break;
            case CLIENT:
                radioGroup.check(R.id.rb_client);
                break;
            case MY:
                radioGroup.check(R.id.rb_my);
                break;
        }
    }

    private void setRadioButtonDrawable() {
        RadioButton[] rbs = new RadioButton[3];
        rbs[0] = rbHome;
        rbs[1] = rbClient;
        rbs[2] = rbMy;
        for (RadioButton rb : rbs) {
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            Drawable[] drawables = rb.getCompoundDrawables();
            //获取drawables
            Rect r = new Rect(0, 0, drawables[1].getMinimumWidth() * 2 / 3, drawables[1].getMinimumHeight() * 2 / 3);
            //定义一个Rect边界
            drawables[1].setBounds(r);
            //添加限制给控件
            rb.setCompoundDrawables(null, drawables[1], null, null);
        }
    }

    @OnCheckedChanged({R.id.rb_home, R.id.rb_client, R.id.rb_my})
    public void radioChange(RadioButton view, boolean ischanged) {
        if (!ischanged) {
            return;
        }
        switch (view.getId()) {
            case R.id.rb_home:  //首页
                currentPage = Page.HOME;
                break;
            case R.id.rb_client:    //客户
                currentPage = Page.CLIENT;
                break;
            case R.id.rb_my:    //我的
                currentPage = Page.MY;
                break;
        }
        changeFragment();
    }

    /**
     * 修改Fragment
     */
    private void changeFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (currentPage) {
            case HOME:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.frameLayout, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case CLIENT:
                if (clientFragment == null) {
                    clientFragment = new ClientFragment();
                    transaction.add(R.id.frameLayout, clientFragment);
                } else {
                    transaction.show(clientFragment);
                }
                break;
            case MY:
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    transaction.add(R.id.frameLayout, myFragment);
                } else {
                    transaction.show(myFragment);
                }

                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏Fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (clientFragment != null) {
            transaction.hide(clientFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }
}
