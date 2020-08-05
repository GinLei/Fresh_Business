package com.dingxin.fresh.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dingxin.fresh.R;
import com.dingxin.fresh.e.CommonEntity;

import java.util.List;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.ViewHolder> {
    private Context mContext;
    private List<CommonEntity> list;

    public PicAdapter(Context context, List<CommonEntity> list) {
        mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_pic, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PicAdapter.ViewHolder holder, int position) {
        String thumb = getList().get(position).getThumb();

        if (!TextUtils.isEmpty(thumb)) {
            //使用Glide框架加载图片
            Glide.with(mContext)
                    .load(thumb)
                    .into(holder.img);
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(thumb);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }

    public List<CommonEntity> getList() {
        return list;
    }

    public interface OnItemClickListener {
        void onClick(String uri);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
