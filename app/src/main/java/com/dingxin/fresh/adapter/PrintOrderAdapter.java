package com.dingxin.fresh.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dingxin.fresh.R;
import com.dingxin.fresh.e.PrintEntity;
import com.dingxin.fresh.vm.PrintViewModel;

import java.util.List;

public class PrintOrderAdapter extends RecyclerView.Adapter<PrintOrderAdapter.ViewHolder> {
    private Context mContext;
    private List<PrintEntity> list;
    private PrintViewModel model;
    private Activity activity;

    public PrintOrderAdapter(Activity activity, List<PrintEntity> list, PrintViewModel model) {
        mContext = activity.getApplicationContext();
        this.activity = activity;
        this.list = list;
        this.model = model;
    }

    @NonNull
    @Override
    public PrintOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_print_rv_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrintOrderAdapter.ViewHolder holder, int position) {
        PrintEntity entity = getList().get(position);
        holder.nick_name.setText("下单人:" + entity.getNick_name());
        holder.order_time.setText(entity.getCreated_at());
        if (entity.getIs_train() == 0) {
            holder.is_test.setVisibility(View.GONE);
        }
        holder.rv.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rv.setItemAnimator(new DefaultItemAnimator());
        holder.rv.setAdapter(new PrintOrderChildAdapter(mContext, entity.getOrder_goods_list(), model));
        holder.mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = entity.getMobile();
                if (!TextUtils.isEmpty(mobile)) {
                    model.callPhone(mobile);
                }
            }
        });
        if (entity.getPrint_status() == 1) {
            holder.print.setText("重复打印");
            holder.print.setTextColor(Color.parseColor("#999999"));
            holder.print.setBackground(ContextCompat.getDrawable(mContext, R.drawable.gray_border_shape_button));
        } else {
            holder.print.setText("立即打印");
            holder.print.setTextColor(Color.parseColor("#ffe54d42"));
            holder.print.setBackground(ContextCompat.getDrawable(mContext, R.drawable.orange_border_shape_button));
        }
        holder.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model.entity.set(entity);
                model.print();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView order_time, print, nick_name;
        public ImageView is_test, mobile;
        public RecyclerView rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nick_name = itemView.findViewById(R.id.nick_name);
            order_time = itemView.findViewById(R.id.order_time);
            is_test = itemView.findViewById(R.id.is_test);
            mobile = itemView.findViewById(R.id.mobile);
            rv = itemView.findViewById(R.id.rv);
            print = itemView.findViewById(R.id.print);
        }
    }

    public List<PrintEntity> getList() {
        return list;
    }

}
