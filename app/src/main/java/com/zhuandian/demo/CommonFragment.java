package com.zhuandian.demo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * desc :
 * author：xiedong
 * data：2018/4/16
 */

public class CommonFragment extends LazyLoadFragment {
    @BindView(R.id.tv_pager_name)
    TextView tvPagerName;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    private String pagerName;
    private List<String> datas = new ArrayList<>();
    private List<Integer> images = new ArrayList() {{
        add(R.drawable.banner);
        add(R.drawable.banner);
        add(R.drawable.banner);
    }};


    public CommonFragment getInstance(String str, boolean isShowBanner) {
        CommonFragment commonFragment = new CommonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("pager_name", str);
        bundle.putBoolean("is_show_banner", isShowBanner);
        commonFragment.setArguments(bundle);
        return commonFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_common;
    }

    @Override
    protected void loadData() {
        pagerName = getArguments().getString("pager_name");
        boolean isShowBanner = getArguments().getBoolean("is_show_banner");
        convenientBanner.setVisibility(isShowBanner ? View.VISIBLE : View.GONE);
        initView();

    }

    private void initView() {
        tvPagerName.setText(pagerName);
        for (int i = 0; i < 25; i++) {
            datas.add("name------" + i);
        }

        rvList.setAdapter(new DataAdapter(getContext(), datas));
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, images)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        //设置翻页的效果，不需要翻页效果可用不设
//        convenientBanner.setManualPageable(false);//设置不能手动影响

    }


}
