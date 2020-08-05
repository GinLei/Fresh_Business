package com.dingxin.fresh.p;

import com.dingxin.fresh.e.PrintEntity;

import java.util.ArrayList;
import java.util.List;

public class PrinterFormat {

    public static ArrayList<byte[]> getPrintData(PrintEntity entity) {
        ArrayList<byte[]> bytes = new ArrayList<>();
        bytes.addAll(PrinterCommand.addLeftText("下单时间:" + entity.getCreated_at()));
        bytes.addAll(PrinterCommand.addGapLine());
        bytes.addAll(PrinterCommand.addTitleH1("鲜到家平台订单"));
        bytes.addAll(PrinterCommand.addTitleH1("在线支付(商户联)"));
        bytes.addAll(PrinterCommand.addCenterText("订单号：" + entity.getOrder_id()));
        bytes.addAll(PrinterCommand.addLeftText("------------下单商品------------"));
        List<PrintEntity.OrderGoodsListBean> goods_list = entity.getOrder_goods_list();
        for (int i = 0; i < goods_list.size(); i++) {
            PrintEntity.OrderGoodsListBean goodsListBean = goods_list.get(i);
            bytes.addAll(PrinterCommand.addLeftText(goodsListBean.getTitle()));
            bytes.addAll(PrinterCommand.LeftToRightBold(goodsListBean.getPrice(), String.valueOf(goodsListBean.getGoods_num())));
            bytes.addAll(PrinterCommand.LeftToRightBold(goodsListBean.getWeight(), goodsListBean.getMember_goods_price()));
            bytes.add(PrinterCommand.addLineSpacing());
        }
        bytes.addAll(PrinterCommand.addLeftText("------------收货地址------------"));
        bytes.addAll(PrinterCommand.addLeftText("收货人：" + entity.getNick_name()));
        bytes.addAll(PrinterCommand.addLeftText("收货地址：" + entity.getAddressinfo()));
        bytes.addAll(PrinterCommand.addGapLine());
        bytes.addAll(PrinterCommand.addRightText("配送费另计"));
        bytes.addAll(PrinterCommand.addRightText("商品总价：" + entity.getAmount()));
        bytes.addAll(PrinterCommand.addGapLine());
        bytes.addAll(PrinterCommand.addLeftText("备注"));
        bytes.addAll(PrinterCommand.addLeftText(entity.getNote()));
        bytes.addAll(PrinterCommand.addGapLine());
        bytes.add(PrinterCommand.addLineSpacing());
        return bytes;
    }
}