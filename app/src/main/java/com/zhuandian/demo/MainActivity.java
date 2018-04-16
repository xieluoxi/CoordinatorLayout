package com.zhuandian.demo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.appbarLayout)
    AppBarLayout appbarLayout;
    private List<CommonFragment> fragmentList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ImmersionBar.with(this)
                .navigationBarColor(R.color.colorPrimary)
                .init(); //初始化，默认透明状态栏和黑色导航栏
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        toolBar.setTitle("新闻");
        titles.add("页面1");
        titles.add("页面2");
        titles.add("页面3");
        fragmentList.add(new CommonFragment().getInstance("pager one",true));
        fragmentList.add(new CommonFragment().getInstance("pager two",false));
        fragmentList.add(new CommonFragment().getInstance("pager three",false));
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList, titles);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        appbarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

                if (state == State.EXPANDED) {
                    //展开状态
                    if (titles.size() > 3) {
                        titles.remove(4);
                        titles.remove(3);
                        fragmentList.remove(4);
                        fragmentList.remove(3);
                        pagerAdapter.notifyDataSetChanged();
                    }

                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    if (titles.size() < 5) {
                        titles.add("页面4");
                        titles.add("页面5");
                        fragmentList.add(new CommonFragment().getInstance("4",false));
                        fragmentList.add(new CommonFragment().getInstance("5",false));
                        pagerAdapter.notifyDataSetChanged();
                    }


                } else {
                    //中间状态


                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态

    }
}
