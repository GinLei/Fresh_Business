package com.dingxin.fresh.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dingxin.fresh.R;
import com.dingxin.fresh.e.CompletedEntity;
import com.dingxin.fresh.e.PrintEntity;
import com.dingxin.fresh.vm.CompletedViewModel;
import com.dingxin.fresh.vm.PrintViewModel;

import java.util.List;

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.ViewHolder> {
    private Context mContext;
    private List<CompletedEntity> list;
    private CompletedViewModel model;

    public CompletedAdapter(Activity activity, List<CompletedEntity> list, CompletedViewModel model) {
        mContext = activity.getApplicationContext();
        this.list = list;
        this.model = model;
    }

    @NonNull
    @Override
    public CompletedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_completed_rv_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedAdapter.ViewHolder holder, int position) {
        CompletedEntity entity = getList().get(position);
        holder.nick_name.setText("下单人:" + entity.getNick_name());
        holder.order_time.setText(entity.getCreated_at());
        holder.g_user.setText("实得菜钱:" + entity.getGuser_price());
        if (entity.getIs_train() != 0) {
            holder.is_test.setVisibility(View.VISIBLE);
        }
        holder.rv.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rv.setItemAnimator(new DefaultItemAnimator());
        holder.rv.setAdapter(new CompletedChildAdapter(mContext, entity.getOrder_goods_list()));
        holder.mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = entity.getMobile();
                if (!TextUtils.isEmpty(mobile)) {
                    model.phoneEvent.setValue(mobile);
                    model.phoneEvent.call();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView order_time, print, nick_name, g_user;
        public ImageView is_test, mobile;
        public RecyclerView rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            g_user = itemView.findViewById(R.id.g_user);
            nick_name = itemView.findViewById(R.id.nick_name);
            order_time = itemView.findViewById(R.id.order_time);
            is_test = itemView.findViewById(R.id.is_test);
            mobile = itemView.findViewById(R.id.mobile);
            rv = itemView.findViewById(R.id.rv);
            print = itemView.findViewById(R.id.print);
        }
    }

    public List<CompletedEntity> getList() {
        return list;
    }

}
