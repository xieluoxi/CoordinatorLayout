package com.zhuandian.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc :
 * author：xiedong
 * data：2018/4/16
 */

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> datas;

    public static final int ITME_TYPE_IMAGE = 1;
    public static final int ITME_TYPE_CONTENT = 2;

    public DataAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITME_TYPE_CONTENT) {
            return new ContentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_content_layout, parent, false));
        } else {
            return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).tvName.setText("我是普通的Item");
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).tvName.setText("我是有图的Item");
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return ITME_TYPE_IMAGE;
        } else {
            return ITME_TYPE_CONTENT;
        }
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
}
