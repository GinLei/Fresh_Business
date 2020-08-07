package com.dingxin.fresh.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dingxin.fresh.R;
import com.dingxin.fresh.e.WeightEntity;
import com.dingxin.fresh.vm.MWeighViewModel;


import java.util.List;

public class MWeightOrderAdapter extends RecyclerView.Adapter<MWeightOrderAdapter.ViewHolder> {
    private Context mContext;
    private List<WeightEntity> list;
    private MWeighViewModel model;


    public MWeightOrderAdapter(Context context, List<WeightEntity> list, MWeighViewModel model) {
        mContext = context;
        this.list = list;
        this.model = model;
    }

    @NonNull
    @Override
    public MWeightOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_mweigh_rv_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MWeightOrderAdapter.ViewHolder holder, int position) {
        WeightEntity entity = getList().get(position);
        holder.order_time.setText(entity.getCreated_at());
        holder.nick_name.setText("下单人:" + entity.getNick_name());
        if (entity.getIs_train() == 0) {
            holder.is_test.setVisibility(View.GONE);
        }
        holder.rv.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rv.setItemAnimator(new DefaultItemAnimator());
        holder.rv.setAdapter(new MWeightOrderChildAdapter(mContext, entity.getOrder_goods_list(), model));
        holder.mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = entity.getMobile();
                if (!TextUtils.isEmpty(mobile)) {
                    model.callPhone(mobile);
                }
            }
        });
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.order_id.set(String.valueOf(entity.getOrder_id()));
                model.commit_weight();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView order_time, confirm, nick_name;
        public ImageView is_test;
        public LinearLayout mobile;
        public RecyclerView rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nick_name = itemView.findViewById(R.id.nick_name);
            order_time = itemView.findViewById(R.id.order_time);
            is_test = itemView.findViewById(R.id.is_test);
            mobile = itemView.findViewById(R.id.mobile);
            rv = itemView.findViewById(R.id.rv);
            confirm = itemView.findViewById(R.id.confirm);
        }
    }

    public List<WeightEntity> getList() {
        return list;
    }

}
