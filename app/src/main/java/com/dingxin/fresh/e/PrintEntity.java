package com.dingxin.fresh.e;

import java.util.List;

public class PrintEntity {


    /**
     * order_goods_list : [{"rec_id":484,"order_id":1739,"goods_id":881,"goods_name":"10","goods_sn":"","cart_id":2060,"spec_id":"202007317829","market_shop_sn":"10086","print_status":0,"guid":4671,"goods_num":1,"weight":"0.990","weight_status":1,"is_train":0,"is_cancel":null,"is_need_weight":1,"cancel_weight_status":0,"goods_weight":"1.0斤","dada_weight":1,"final_guser_price":null,"final_price":"10.39","goods_price":"10.50","guser_price":"9.90","cost_price":"0.00","member_goods_price":"10.39","give_integral":0,"item_id":0,"spec_key":"","spec_key_name":"","bar_code":"","is_comment":0,"prom_type":0,"prom_id":0,"is_send":0,"delivery_id":0,"sku":"","way_id":null,"way_price":null,"is_miaosha":0,"is_special":1,"min_dada_weight":"1.0","thumb":"http://test.7chezhibo.com/storage/app/uploads/member/thumb/202007311054145f2387d6ed687.jpg","spec_name":"","unit_name":"/斤","price":"10.50元/斤","title":"10"},{"rec_id":485,"order_id":1739,"goods_id":878,"goods_name":"测试","goods_sn":"","cart_id":2061,"spec_id":"202007316678","market_shop_sn":"10086","print_status":0,"guid":4671,"goods_num":1,"weight":"2.000","weight_status":1,"is_train":0,"is_cancel":null,"is_need_weight":0,"cancel_weight_status":0,"goods_weight":"2.0斤","dada_weight":1,"final_guser_price":null,"final_price":"11.00","goods_price":"11.00","guser_price":"10.00","cost_price":"0.00","member_goods_price":"11.00","give_integral":0,"item_id":0,"spec_key":"","spec_key_name":"","bar_code":"","is_comment":0,"prom_type":0,"prom_id":0,"is_send":0,"delivery_id":0,"sku":"","way_id":null,"way_price":null,"is_miaosha":0,"is_special":1,"min_dada_weight":"1.0","thumb":"http://test.7chezhibo.com/storage/app/uploads/member/thumb/202007311037065f2383d22cc41.jpg","spec_name":"中","unit_name":"/把","price":"11.00元/把","title":"测试(中)"}]
     * created_at : 2020-08-02 11:24:17
     * id : 0
     * order_id : 0
     * guid : 4671
     * print_status : 0
     * order_sn : 2020080249102515
     * is_train : 0
     * nick_name : 王
     * addressinfo : 金地豪苑(重庆市渝北区民安大道447号)梭梭树
     * amount : 21.39
     * note :
     * mobile : 18502308959
     */

    private String created_at;
    private int id;
    private int order_id;
    private int guid;
    private int print_status;
    private String order_sn;
    private int is_train;
    private String nick_name;
    private String addressinfo;
    private String amount;
    private String note;
    private String mobile;
    private List<OrderGoodsListBean> order_goods_list;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getGuid() {
        return guid;
    }

    public void setGuid(int guid) {
        this.guid = guid;
    }

    public int getPrint_status() {
        return print_status;
    }

    public void setPrint_status(int print_status) {
        this.print_status = print_status;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getIs_train() {
        return is_train;
    }

    public void setIs_train(int is_train) {
        this.is_train = is_train;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAddressinfo() {
        return addressinfo;
    }

    public void setAddressinfo(String addressinfo) {
        this.addressinfo = addressinfo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<OrderGoodsListBean> getOrder_goods_list() {
        return order_goods_list;
    }

    public void setOrder_goods_list(List<OrderGoodsListBean> order_goods_list) {
        this.order_goods_list = order_goods_list;
    }

    public static class OrderGoodsListBean {
        /**
         * rec_id : 484
         * order_id : 1739
         * goods_id : 881
         * goods_name : 10
         * goods_sn :
         * cart_id : 2060
         * spec_id : 202007317829
         * market_shop_sn : 10086
         * print_status : 0
         * guid : 4671
         * goods_num : 1
         * weight : 0.990
         * weight_status : 1
         * is_train : 0
         * is_cancel : null
         * is_need_weight : 1
         * cancel_weight_status : 0
         * goods_weight : 1.0斤
         * dada_weight : 1
         * final_guser_price : null
         * final_price : 10.39
         * goods_price : 10.50
         * guser_price : 9.90
         * cost_price : 0.00
         * member_goods_price : 10.39
         * give_integral : 0
         * item_id : 0
         * spec_key :
         * spec_key_name :
         * bar_code :
         * is_comment : 0
         * prom_type : 0
         * prom_id : 0
         * is_send : 0
         * delivery_id : 0
         * sku :
         * way_id : null
         * way_price : null
         * is_miaosha : 0
         * is_special : 1
         * min_dada_weight : 1.0
         * thumb : http://test.7chezhibo.com/storage/app/uploads/member/thumb/202007311054145f2387d6ed687.jpg
         * spec_name :
         * unit_name : /斤
         * price : 10.50元/斤
         * title : 10
         */

        private int rec_id;
        private int order_id;
        private int goods_id;
        private String goods_name;
        private String goods_sn;
        private int cart_id;
        private String spec_id;
        private String market_shop_sn;
        private int print_status;
        private int guid;
        private int goods_num;
        private String weight;
        private int weight_status;
        private int is_train;
        private Object is_cancel;
        private int is_need_weight;
        private int cancel_weight_status;
        private String goods_weight;
        private int dada_weight;
        private Object final_guser_price;
        private String final_price;
        private String goods_price;
        private String guser_price;
        private String cost_price;
        private String member_goods_price;
        private int give_integral;
        private int item_id;
        private String spec_key;
        private String spec_key_name;
        private String bar_code;
        private int is_comment;
        private int prom_type;
        private int prom_id;
        private int is_send;
        private int delivery_id;
        private String sku;
        private Object way_id;
        private Object way_price;
        private int is_miaosha;
        private int is_special;
        private String min_dada_weight;
        private String thumb;
        private String spec_name;
        private String unit_name;
        private String price;
        private String title;

        public int getRec_id() {
            return rec_id;
        }

        public void setRec_id(int rec_id) {
            this.rec_id = rec_id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_sn() {
            return goods_sn;
        }

        public void setGoods_sn(String goods_sn) {
            this.goods_sn = goods_sn;
        }

        public int getCart_id() {
            return cart_id;
        }

        public void setCart_id(int cart_id) {
            this.cart_id = cart_id;
        }

        public String getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(String spec_id) {
            this.spec_id = spec_id;
        }

        public String getMarket_shop_sn() {
            return market_shop_sn;
        }

        public void setMarket_shop_sn(String market_shop_sn) {
            this.market_shop_sn = market_shop_sn;
        }

        public int getPrint_status() {
            return print_status;
        }

        public void setPrint_status(int print_status) {
            this.print_status = print_status;
        }

        public int getGuid() {
            return guid;
        }

        public void setGuid(int guid) {
            this.guid = guid;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public int getWeight_status() {
            return weight_status;
        }

        public void setWeight_status(int weight_status) {
            this.weight_status = weight_status;
        }

        public int getIs_train() {
            return is_train;
        }

        public void setIs_train(int is_train) {
            this.is_train = is_train;
        }

        public Object getIs_cancel() {
            return is_cancel;
        }

        public void setIs_cancel(Object is_cancel) {
            this.is_cancel = is_cancel;
        }

        public int getIs_need_weight() {
            return is_need_weight;
        }

        public void setIs_need_weight(int is_need_weight) {
            this.is_need_weight = is_need_weight;
        }

        public int getCancel_weight_status() {
            return cancel_weight_status;
        }

        public void setCancel_weight_status(int cancel_weight_status) {
            this.cancel_weight_status = cancel_weight_status;
        }

        public String getGoods_weight() {
            return goods_weight;
        }

        public void setGoods_weight(String goods_weight) {
            this.goods_weight = goods_weight;
        }

        public int getDada_weight() {
            return dada_weight;
        }

        public void setDada_weight(int dada_weight) {
            this.dada_weight = dada_weight;
        }

        public Object getFinal_guser_price() {
            return final_guser_price;
        }

        public void setFinal_guser_price(Object final_guser_price) {
            this.final_guser_price = final_guser_price;
        }

        public String getFinal_price() {
            return final_price;
        }

        public void setFinal_price(String final_price) {
            this.final_price = final_price;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGuser_price() {
            return guser_price;
        }

        public void setGuser_price(String guser_price) {
            this.guser_price = guser_price;
        }

        public String getCost_price() {
            return cost_price;
        }

        public void setCost_price(String cost_price) {
            this.cost_price = cost_price;
        }

        public String getMember_goods_price() {
            return member_goods_price;
        }

        public void setMember_goods_price(String member_goods_price) {
            this.member_goods_price = member_goods_price;
        }

        public int getGive_integral() {
            return give_integral;
        }

        public void setGive_integral(int give_integral) {
            this.give_integral = give_integral;
        }

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public String getSpec_key() {
            return spec_key;
        }

        public void setSpec_key(String spec_key) {
            this.spec_key = spec_key;
        }

        public String getSpec_key_name() {
            return spec_key_name;
        }

        public void setSpec_key_name(String spec_key_name) {
            this.spec_key_name = spec_key_name;
        }

        public String getBar_code() {
            return bar_code;
        }

        public void setBar_code(String bar_code) {
            this.bar_code = bar_code;
        }

        public int getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(int is_comment) {
            this.is_comment = is_comment;
        }

        public int getProm_type() {
            return prom_type;
        }

        public void setProm_type(int prom_type) {
            this.prom_type = prom_type;
        }

        public int getProm_id() {
            return prom_id;
        }

        public void setProm_id(int prom_id) {
            this.prom_id = prom_id;
        }

        public int getIs_send() {
            return is_send;
        }

        public void setIs_send(int is_send) {
            this.is_send = is_send;
        }

        public int getDelivery_id() {
            return delivery_id;
        }

        public void setDelivery_id(int delivery_id) {
            this.delivery_id = delivery_id;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public Object getWay_id() {
            return way_id;
        }

        public void setWay_id(Object way_id) {
            this.way_id = way_id;
        }

        public Object getWay_price() {
            return way_price;
        }

        public void setWay_price(Object way_price) {
            this.way_price = way_price;
        }

        public int getIs_miaosha() {
            return is_miaosha;
        }

        public void setIs_miaosha(int is_miaosha) {
            this.is_miaosha = is_miaosha;
        }

        public int getIs_special() {
            return is_special;
        }

        public void setIs_special(int is_special) {
            this.is_special = is_special;
        }

        public String getMin_dada_weight() {
            return min_dada_weight;
        }

        public void setMin_dada_weight(String min_dada_weight) {
            this.min_dada_weight = min_dada_weight;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getSpec_name() {
            return spec_name;
        }

        public void setSpec_name(String spec_name) {
            this.spec_name = spec_name;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
