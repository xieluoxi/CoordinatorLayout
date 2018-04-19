package com.zhuandian.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> datas;
    private List<String> titles;
    List<Integer> images;
    public static final int ITME_TYPE_IMAGE = 1;
    public static final int ITME_TYPE_CONTENT = 2;
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    Boolean flag =false;

    private View mHeaderView;

    public DataAdapter(Context context, List<String> datas,List<Integer> images ,Boolean flag) {
        this.context = context;
        this.datas = datas;
        this.images = images;
        this.flag = flag;
    }

    //HeaderView和FooterView的get和set函数
     public View getHeaderView() {
        return mHeaderView;
    }
     public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        if (viewType==TYPE_HEADER)
//        {
//            return new HeadViewHolder(LayoutInflater.from(context).inflate(R.layout.item_head, parent, false));
//        }
//
//        if (viewType == ITME_TYPE_CONTENT) {
//            return new ContentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_content_layout, parent, false));
//        } else {
//            return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_layout, parent, false));
//        }
//        if (viewType == ITME_TYPE_CONTENT)
//        {
//            return new ContentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_content_layout, parent, false));
//        }
//        if (viewType==ITME_TYPE_IMAGE)
//        {
//            return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_layout, parent, false));
//        }
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {

            case TYPE_HEADER:
                if (flag){
                    holder =  new HeadViewHolder(LayoutInflater.from(context).inflate(R.layout.item_head, parent, false));
                }else {
                    //没有图的布局，没有头布局的时候
                    holder = new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_layout, parent, false));
                }
                break;
            case ITME_TYPE_CONTENT:
                holder = new ContentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
                break;
            case  ITME_TYPE_IMAGE:   
                holder = new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_layout, parent, false));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).tvName.setText("习近平在强调：全面贯彻落实总体国家安全观 开创新时代国家安全工作新局面");
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).tvName.setText("全面改革开放全面实现小康社会的方针");
        }

    }


    @Override
    public int getItemViewType(int position) {

//        if (position == 0){
//            return TYPE_HEADER;
//        }else if (position % 2 == 0){
//            return ITME_TYPE_IMAGE;
//        }else {
//            return ITME_TYPE_CONTENT;
//        }

          if (position == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }

//        if (position % 2 == 0) {//原始
//            return ITME_TYPE_IMAGE;
//        } else {
//            return ITME_TYPE_CONTENT;
//        }

        if (position % 2 == 0) {//原始
            return ITME_TYPE_IMAGE;
        }
        if (position % 2 == 1){
            return ITME_TYPE_CONTENT;
        }


        return 0;

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        ConvenientBanner convenientBanner;

        public HeadViewHolder(View itemView) {
            super(itemView);

            titles = new ArrayList<>();
            titles.add("我是第一页的内容啊");
            titles.add("我是第二页的内容啊");
            titles.add("我是第三页的内容啊");
            Banner banner = itemView.findViewById(R.id.banner);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(images);
            banner.setBannerTitles(titles);
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            banner.setIndicatorGravity(BannerConfig.RIGHT);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
//            convenientBanner =itemView.findViewById(R.id.convenientBanner);
//
//            convenientBanner.setPages(
//                    new CBViewHolderCreator<LocalImageHolderView>() {
//                        @Override
//                        public LocalImageHolderView createHolder() {
//                            return new LocalImageHolderView();
//                        }
//                    }, images)
//                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                    .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
//                    //设置指示器的方向
//                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
//        }
        }
    }
}
