package com.dingxin.fresh.e;

import java.util.List;

public class WeightEntity {


    /**
     * order_goods_list : [{"rec_id":56,"order_id":1420,"goods_id":319,"goods_name":"卤菜","goods_price":"10.80","final_price":"4.32","is_need_weight":1,"goods_weight":null,"weight":"0.000","weight_status":0,"cancel_weight_status":null,"dada_weight":"0.400","member_goods_price":"0.00","print_status":0,"is_train":0,"is_special":0,"is_canceled":0,"mobile":"18502308959","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200624/1592982715221674.jpg","message":"斤数差距过大"},{"rec_id":58,"order_id":1420,"goods_id":319,"goods_name":"卤菜","goods_price":"10.80","final_price":"17.28","is_need_weight":1,"goods_weight":null,"weight":"0.000","weight_status":0,"cancel_weight_status":null,"dada_weight":"1.600","member_goods_price":"0.00","print_status":0,"is_train":0,"is_special":0,"is_canceled":0,"mobile":"18502308959","thumb":"http://test.7chezhibo.com/ueditor/php/upload/image/20200624/1592982715221674.jpg","message":"斤数差距过大"}]
     * created_at : 2020-07-07 17:55:32
     * order_id : 1420
     * id : 1420
     * guid : 4473
     * is_train : 0
     * offset : 0
     * page : 1
     * state : true
     */

    private String created_at;
    private int order_id;
    private int id;
    private int guid;
    private int is_train;
    private int offset;
    private String page;
    private boolean state;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private List<OrderGoodsListBean> order_goods_list;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGuid() {
        return guid;
    }

    public void setGuid(int guid) {
        this.guid = guid;
    }

    public int getIs_train() {
        return is_train;
    }

    public void setIs_train(int is_train) {
        this.is_train = is_train;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<OrderGoodsListBean> getOrder_goods_list() {
        return order_goods_list;
    }

    public void setOrder_goods_list(List<OrderGoodsListBean> order_goods_list) {
        this.order_goods_list = order_goods_list;
    }

    public static class OrderGoodsListBean {
        /**
         * rec_id : 56
         * order_id : 1420
         * goods_id : 319
         * goods_name : 卤菜
         * goods_price : 10.80
         * final_price : 4.32
         * is_need_weight : 1
         * goods_weight : null
         * weight : 0.000
         * weight_status : 0
         * cancel_weight_status : null
         * dada_weight : 0.400
         * member_goods_price : 0.00
         * print_status : 0
         * is_train : 0
         * is_special : 0
         * is_canceled : 0
         * mobile : 18502308959
         * thumb : http://test.7chezhibo.com/ueditor/php/upload/image/20200624/1592982715221674.jpg
         * message : 斤数差距过大
         */

        private String spec_id;

        public String getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(String spec_id) {
            this.spec_id = spec_id;
        }

        private int is_standard;

        public int getIs_standard() {
            return is_standard;
        }

        public void setIs_standard(int is_standard) {
            this.is_standard = is_standard;
        }

        private String spec_name;

        public String getSpec_name() {
            return spec_name;
        }

        public void setSpec_name(String spec_name) {
            this.spec_name = spec_name;
        }

        private String unit_name;

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        private String min_dada_weight;
        private int rec_id;
        private int order_id;
        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String final_price;
        private int is_need_weight;
        private Object goods_weight;
        private String weight;
        private int weight_status;
        private Object cancel_weight_status;
        private String dada_weight;
        private String member_goods_price;
        private int print_status;
        private int is_train;
        private int is_special;
        private int is_canceled;
        private String mobile;
        private String thumb;
        private String message;
        private String unit;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getMin_dada_weight() {
            return min_dada_weight;
        }

        public void setMin_dada_weight(String min_dada_weight) {
            this.min_dada_weight = min_dada_weight;
        }

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

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getFinal_price() {
            return final_price;
        }

        public void setFinal_price(String final_price) {
            this.final_price = final_price;
        }

        public int getIs_need_weight() {
            return is_need_weight;
        }

        public void setIs_need_weight(int is_need_weight) {
            this.is_need_weight = is_need_weight;
        }

        public Object getGoods_weight() {
            return goods_weight;
        }

        public void setGoods_weight(Object goods_weight) {
            this.goods_weight = goods_weight;
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

        public Object getCancel_weight_status() {
            return cancel_weight_status;
        }

        public void setCancel_weight_status(Object cancel_weight_status) {
            this.cancel_weight_status = cancel_weight_status;
        }

        public String getDada_weight() {
            return dada_weight;
        }

        public void setDada_weight(String dada_weight) {
            this.dada_weight = dada_weight;
        }

        public String getMember_goods_price() {
            return member_goods_price;
        }

        public void setMember_goods_price(String member_goods_price) {
            this.member_goods_price = member_goods_price;
        }

        public int getPrint_status() {
            return print_status;
        }

        public void setPrint_status(int print_status) {
            this.print_status = print_status;
        }

        public int getIs_train() {
            return is_train;
        }

        public void setIs_train(int is_train) {
            this.is_train = is_train;
        }

        public int getIs_special() {
            return is_special;
        }

        public void setIs_special(int is_special) {
            this.is_special = is_special;
        }

        public int getIs_canceled() {
            return is_canceled;
        }

        public void setIs_canceled(int is_canceled) {
            this.is_canceled = is_canceled;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
