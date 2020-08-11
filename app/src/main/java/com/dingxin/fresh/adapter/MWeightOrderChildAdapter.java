package com.dingxin.fresh.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dingxin.fresh.R;
import com.dingxin.fresh.e.WeightEntity.OrderGoodsListBean;
import com.dingxin.fresh.vm.MWeighViewModel;

import java.util.List;

public class MWeightOrderChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<OrderGoodsListBean> list;
    private MWeighViewModel model;
    public int TYPE_ONE = 1;
    public int TYPE_TWO = 2;
    public int TYPE_THREE = 3;
    public int TYPE_FOUR = 4;

    public MWeightOrderChildAdapter(Context context, List<OrderGoodsListBean> list, MWeighViewModel model) {
        mContext = context;
        this.list = list;
        this.model = model;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 1:
                viewHolder = new ViewHolder1(LayoutInflater.from(mContext).inflate(R.layout.fragment_weigh_rv_item_one, parent, false));
                break;
            case 2:
                viewHolder = new ViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.fragment_weigh_rv_item_two, parent, false));
                break;
            case 3:
                viewHolder = new ViewHolder3(LayoutInflater.from(mContext).inflate(R.layout.fragment_weigh_rv_item_three, parent, false));
                break;
            case 4:
                viewHolder = new ViewHolder4(LayoutInflater.from(mContext).inflate(R.layout.fragment_weigh_rv_item_four, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderGoodsListBean entity = getList().get(position);
        if (holder instanceof ViewHolder1) {
            ViewHolder1 viewHolder1 = (ViewHolder1) holder;
            Glide.with(mContext.getApplicationContext()).load(entity.getThumb()).into(viewHolder1.img);
            viewHolder1.tv_1.setText(entity.getTitle());
            viewHolder1.tv_2.setText(entity.getGoods_price() + entity.getUnit_name());
            String spec_name = entity.getSpec_name();
            if (!TextUtils.isEmpty(spec_name)) {
                viewHolder1.tv_3.setText("规格:" + entity.getSpec_name());
            } else {
                viewHolder1.tv_3.setText("规格:无");
            }
            viewHolder1.tv_4.setText("数量: x" + entity.getMin_dada_weight());
            viewHolder1.tv_5.setText("重量:" + entity.getGoods_weight());
            viewHolder1.tv_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.goods_id.set(entity.getGoods_id());
                    model.order_id.set(String.valueOf(entity.getOrder_id()));
                    model.spec_id.set(entity.getSpec_id());
                    model.weight();
                }
            });
            viewHolder1.tv_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.goods_id.set(entity.getGoods_id());
                    model.order_id.set(String.valueOf(entity.getOrder_id()));
                    model.spec_id.set(entity.getSpec_id());
                    model.cancelGoods();
                }
            });
        } else if (holder instanceof ViewHolder2) {
            ViewHolder2 viewHolder2 = (ViewHolder2) holder;
            Glide.with(mContext.getApplicationContext()).load(entity.getThumb()).into(viewHolder2.img);
            viewHolder2.tv_1.setText(entity.getTitle());
            viewHolder2.tv_2.setText(entity.getGoods_price() + entity.getUnit_name());
            String spec_name = entity.getSpec_name();
            if (!TextUtils.isEmpty(spec_name)) {
                viewHolder2.tv_3.setText("规格:" + entity.getSpec_name());
            } else {
                viewHolder2.tv_3.setText("规格:无");
            }
            viewHolder2.tv_4.setText("数量: x" + entity.getMin_dada_weight());
            viewHolder2.tv_5.setText("重量:" + entity.getGoods_weight());
            viewHolder2.tv_6.setText(entity.getWeight());
            viewHolder2.tv_7.setText(entity.getMessage());
            viewHolder2.tv_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.goods_id.set(entity.getGoods_id());
                    model.order_id.set(String.valueOf(entity.getOrder_id()));
                    model.spec_id.set(entity.getSpec_id());
                    model.weight();
                }
            });
        } else if (holder instanceof ViewHolder3) {
            ViewHolder3 ViewHolder3 = (ViewHolder3) holder;
            Glide.with(mContext.getApplicationContext()).load(entity.getThumb()).into(ViewHolder3.img);
            ViewHolder3.tv_1.setText(entity.getTitle());
            ViewHolder3.tv_2.setText(entity.getGoods_price() + entity.getUnit_name());
            String spec_name = entity.getSpec_name();
            if (!TextUtils.isEmpty(spec_name)) {
                ViewHolder3.tv_3.setText("规格:" + entity.getSpec_name());
            } else {
                ViewHolder3.tv_3.setText("规格:无");
            }
            ViewHolder3.tv_4.setText("数量: x" + entity.getMin_dada_weight());
            ViewHolder3.tv_5.setText("重量:" + entity.getGoods_weight());
            ViewHolder3.tv_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.goods_id.set(entity.getGoods_id());
                    model.order_id.set(String.valueOf(entity.getOrder_id()));
                    model.spec_id.set(entity.getSpec_id());
                    model.cancelGoods();
                }
            });
        } else if (holder instanceof ViewHolder4) {
            ViewHolder4 viewHolder4 = (ViewHolder4) holder;
            Glide.with(mContext.getApplicationContext()).load(entity.getThumb()).into(viewHolder4.img);
            viewHolder4.tv_1.setText(entity.getTitle());
            viewHolder4.tv_2.setText(entity.getGoods_price() + entity.getUnit_name());
            String spec_name = entity.getSpec_name();
            if (!TextUtils.isEmpty(spec_name)) {
                viewHolder4.tv_3.setText("规格:" + entity.getSpec_name());
            } else {
                viewHolder4.tv_3.setText("规格:无");
            }
            viewHolder4.tv_4.setText("数量: x" + entity.getMin_dada_weight());
            viewHolder4.tv_5.setText("重量:" + entity.getGoods_weight());
//            viewHolder4.tv_6.setText(entity.getWeight());
            viewHolder4.tv_6.setText("不需要称重");
            viewHolder4.tv_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.goods_id.set(entity.getGoods_id());
                    model.order_id.set(String.valueOf(entity.getOrder_id()));
                    model.spec_id.set(entity.getSpec_id());
                    model.cancelGoods();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        OrderGoodsListBean bean = getList().get(position);
        int is_need_weight = bean.getIs_need_weight();
        if (is_need_weight == 0) {
            if (bean.getIs_canceled() == 1) {
                return TYPE_THREE;
            }
            return TYPE_FOUR;
        } else if (is_need_weight == 1) {
            if (bean.getIs_canceled() == 0) {
                if (bean.getWeight_status() == 0) {
                    return TYPE_ONE;
                }
                return TYPE_TWO;
            } else if (bean.getIs_canceled() == 1) {
                return TYPE_THREE;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        public TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7;
        public ImageView img;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            tv_1 = itemView.findViewById(R.id.tv_1);
            tv_2 = itemView.findViewById(R.id.tv_2);
            tv_3 = itemView.findViewById(R.id.tv_3);
            tv_4 = itemView.findViewById(R.id.tv_4);
            tv_5 = itemView.findViewById(R.id.tv_5);
            tv_6 = itemView.findViewById(R.id.tv_6);
            tv_7 = itemView.findViewById(R.id.tv_7);
            img = itemView.findViewById(R.id.img);
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8;
        public ImageView img;

        public ViewHolder2(@NonNull View itemView) {
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

    public static class ViewHolder3 extends RecyclerView.ViewHolder {
        public TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6;
        public ImageView img;

        public ViewHolder3(@NonNull View itemView) {
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

    public static class ViewHolder4 extends RecyclerView.ViewHolder {
        public TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7;
        public ImageView img;

        public ViewHolder4(@NonNull View itemView) {
            super(itemView);
            tv_1 = itemView.findViewById(R.id.tv_1);
            tv_2 = itemView.findViewById(R.id.tv_2);
            tv_3 = itemView.findViewById(R.id.tv_3);
            tv_4 = itemView.findViewById(R.id.tv_4);
            tv_5 = itemView.findViewById(R.id.tv_5);
            tv_6 = itemView.findViewById(R.id.tv_6);
            tv_7 = itemView.findViewById(R.id.tv_7);
            img = itemView.findViewById(R.id.img);
        }
    }

    private List<OrderGoodsListBean> getList() {
        return list;
    }

}
