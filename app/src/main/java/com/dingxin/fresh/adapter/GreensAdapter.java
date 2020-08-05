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

import com.dingxin.fresh.R;
import com.dingxin.fresh.e.GreensEntity;
import com.dingxin.fresh.e.SpecsEntity;
import com.dingxin.fresh.vm.GoodsListViewModel;

import java.util.List;

public class GreensAdapter extends RecyclerView.Adapter<GreensAdapter.ViewHolder> {
    private Context mContext;
    private List<SpecsEntity> list;
    private GoodsListViewModel viewModel;

    public GreensAdapter(Context context, List<SpecsEntity> list, GoodsListViewModel viewModel) {
        mContext = context;
        this.list = list;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public GreensAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GreensAdapter.ViewHolder holder = new GreensAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_greens, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecsEntity specsEntity = list.get(position);
        holder.tv_1.setText(specsEntity.getSpec_name());
        holder.tv_2.setText(specsEntity.getPrice_old());
        holder.tv_3.setText(specsEntity.getUnit_name());
        holder.tv_4.setText(specsEntity.getMoney_old());
        holder.tv_5.setText(specsEntity.getUnit_name());
        holder.tv_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.spec_id.set(specsEntity.getSpec_id());
                viewModel.set_greens_spec_sale();
            }
        });
        holder.tv_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.tv_6.setText(specsEntity.getIs_on_sale() ? "下架" : "上架");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_1 = itemView.findViewById(R.id.tv_1);
            tv_2 = itemView.findViewById(R.id.tv_2);
            tv_3 = itemView.findViewById(R.id.tv_3);
            tv_4 = itemView.findViewById(R.id.tv_4);
            tv_5 = itemView.findViewById(R.id.tv_5);
            tv_6 = itemView.findViewById(R.id.tv_6);
            tv_7 = itemView.findViewById(R.id.tv_7);
        }
    }

    public List<SpecsEntity> getList() {
        return list;
    }
}
