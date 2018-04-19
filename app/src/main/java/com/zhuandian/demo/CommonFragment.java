package com.zhuandian.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


public class CommonFragment extends LazyLoadFragment {
    @BindView(R.id.tv_pager_name)
    TextView tvPagerName;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.gank_swipe_refresh_layout)
    SwipeRefreshLayout gankSwipeRefreshLayout;
    Unbinder unbinder;
    //  SwipeRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.convenientBanner)
//    ConvenientBanner convenientBanner;
    private String pagerName;
    private List<String> datas = new ArrayList<>();
    private List<Integer> images = new ArrayList() {{
        add(R.drawable.banner);
        add(R.drawable.banner);
        add(R.drawable.banner);
    }};

    private int lastVisibleItem;
    private boolean isLoading;
    private DataAdapter dataAdapter;
    private LinearLayoutManager linearLayoutManager;


    public CommonFragment getInstance(String str, boolean isShowBanner, boolean isContainBanner) {
        CommonFragment commonFragment = new CommonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("pager_name", str);
        bundle.putBoolean("is_show_banner", isShowBanner);
        bundle.putBoolean("dataAdapter", isContainBanner);
        commonFragment.setArguments(bundle);
        return commonFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_common;


    }

    //每次上拉加载的时候，给RecyclerView的后面添加了10条数据数据
    private void loadMoreData() {
        Toast.makeText(getActivity(), "正在加载数据....", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < 10; i++) {
            datas.add("嘿，我是“上拉加载”生出来的" + i);
        }
        dataAdapter.notifyDataSetChanged();
    }


    @Override
    protected void loadData() {
        pagerName = getArguments().getString("pager_name");
        initView();
    }


    private void initView() {
        tvPagerName.setText(pagerName);
        for (int i = 0; i < 10; i++) {
            datas.add("---" + i);
        }
        dataAdapter = new DataAdapter(getContext(), datas, images, getArguments().getBoolean("dataAdapter"));
        rvList.setAdapter(dataAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(linearLayoutManager);
        gankSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "加载数据", Toast.LENGTH_SHORT).show();
                gankSwipeRefreshLayout.setRefreshing(false);
            }
        });

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == dataAdapter.getItemCount() && !isLoading) {

                    isLoading = true;
                    //模拟网络延时
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadMoreData();
                        }
                    }, 1500);
                }else {
                    isLoading =false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //拿到最后一个出现的item的位置
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });


    }


}
