package com.dingxin.fresh.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dingxin.fresh.R;
import com.dingxin.fresh.e.CompletedEntity;
import com.dingxin.fresh.e.PrintEntity;
import com.dingxin.fresh.vm.PrintViewModel;

import java.util.List;

public class CompletedChildAdapter extends RecyclerView.Adapter<CompletedChildAdapter.ViewHolder> {
    private Context mContext;
    private List<CompletedEntity.OrderGoodsListBean> list;

    public CompletedChildAdapter(Context context, List<CompletedEntity.OrderGoodsListBean> list) {
        mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CompletedChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_completed_rv_item_child, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedChildAdapter.ViewHolder holder, int position) {
        CompletedEntity.OrderGoodsListBean entity = getList().get(position);
        Glide.with(mContext.getApplicationContext()).load(entity.getThumb()).into(holder.img);
        holder.tv_1.setText(entity.getTitle());
        holder.tv_2.setText(entity.getGoods_price() + entity.getUnit_name());
        String spec_name = entity.getSpec_name();
        if (!TextUtils.isEmpty(spec_name)) {
            holder.tv_3.setText("规格:" + entity.getSpec_name());
        } else {
            holder.tv_3.setText("规格:无");
        }
        holder.tv_4.setText("数量: x" + entity.getMin_dada_weight());
        holder.tv_5.setText("重量:" + entity.getGoods_weight());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6;
        public ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_1 = itemView.findViewById(R.id.tv_1);
            tv_2 = itemView.findViewById(R.id.tv_2);
            tv_3 = itemView.findViewById(R.id.tv_3);
            tv_4 = itemView.findViewById(R.id.tv_4);
            tv_5 = itemView.findViewById(R.id.tv_5);
            tv_6 = itemView.findViewById(R.id.tv_6);
            img = itemView.findViewById(R.id.img);
        }
    }

    private List<CompletedEntity.OrderGoodsListBean> getList() {
        return list;
    }
}
