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
import com.dingxin.fresh.e.PrintEntity;
import com.dingxin.fresh.vm.PrintViewModel;


import java.util.List;

public class PrintOrderChildAdapter extends RecyclerView.Adapter<PrintOrderChildAdapter.ViewHolder> {
    private Context mContext;
    private List<PrintEntity.OrderGoodsListBean> list;
    private PrintViewModel model;

    public PrintOrderChildAdapter(Context context, List<PrintEntity.OrderGoodsListBean> list, PrintViewModel model) {
        mContext = context;
        this.list = list;
        this.model = model;
    }

    @NonNull
    @Override
    public PrintOrderChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_print_rv_item_child, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrintOrderChildAdapter.ViewHolder holder, int position) {
        PrintEntity.OrderGoodsListBean entity = getList().get(position);
        Glide.with(mContext.getApplicationContext()).load(entity.getThumb()).into(holder.img);
        holder.tv_1.setText(entity.getTitle());
        holder.tv_2.setText(entity.getGoods_price() + entity.getUnit_name());
        String spec_name = entity.getSpec_name();
        if (!TextUtils.isEmpty(spec_name)) {
            holder.tv_3.setText("规格:" + entity.getSpec_name());
        } else {
            holder.tv_3.setText("规格:无");
        }
        holder.tv_4.setText("数量: x" + entity.getGoods_num());
        holder.tv_5.setText("重量:" + entity.getGoods_weight());
        int cancel_weight_status = entity.getCancel_weight_status();
        if (cancel_weight_status == 1) {
            holder.tv_6.setText("商品缺货");
        } else {
            holder.tv_6.setText("称重重量:" + entity.getWeight());
        }
        holder.tv_7.setText("预付价格:¥" + entity.getFinal_price());
        holder.tv_8.setText("实付价格:¥" + entity.getMember_goods_price());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8;
        public ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_1 = itemView.findViewById(R.id.tv_1);
            tv_2 = itemView.findViewById(R.id.tv_2);
            tv_3 = itemView.findViewById(R.id.tv_3);
            tv_4 = itemView.findViewById(R.id.tv_4);
            tv_5 = itemView.findViewById(R.id.tv_5);
            tv_6 = itemView.findViewById(R.id.tv_6);
            tv_7 = itemView.findViewById(R.id.tv_7);
            tv_8 = itemView.findViewById(R.id.tv_8);
            img = itemView.findViewById(R.id.img);
        }
    }

    private List<PrintEntity.OrderGoodsListBean> getList() {
        return list;
    }
}
