package com.dingxin.fresh.e;

import java.util.List;

public class CompletedEntity {


    /**
     * guser_price : 10.00
     * order_goods_list : [{"rec_id":467,"order_id":1730,"goods_id":878,"spec_id":"202007316678","goods_name":"测试","goods_price":"11.00","final_price":"11.00","weight":"2.000","dada_weight":1,"member_goods_price":"11.00","print_status":0,"guser_price":"10.00","is_train":0,"is_special":1,"min_dada_weight":"1.0","thumb":"http://test.7chezhibo.com/storage/app/uploads/member/thumb/202007311037065f2383d22cc41.jpg","cancel_weight_status":0,"goods_weight":"","unit":"/把","spec_name":"中","min_title":null,"max_title":null}]
     * created_at : 2020-08-01 17:03:24
     * id : null
     * order_id : null
     * guid : 4671
     * is_train : 0
     * order_sn : 2020080199499857
     * amount : 11.00
     * note :
     * mobile : 17380273701
     */

    private String guser_price;
    private String created_at;
    private Object id;
    private Object order_id;
    private int guid;
    private int is_train;
    private String order_sn;
    private String amount;
    private String note;
    private String mobile;
    private List<OrderGoodsListBean> order_goods_list;

    public String getGuser_price() {
        return guser_price;
    }

    public void setGuser_price(String guser_price) {
        this.guser_price = guser_price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Object order_id) {
        this.order_id = order_id;
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

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
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
         * rec_id : 467
         * order_id : 1730
         * goods_id : 878
         * spec_id : 202007316678
         * goods_name : 测试
         * goods_price : 11.00
         * final_price : 11.00
         * weight : 2.000
         * dada_weight : 1
         * member_goods_price : 11.00
         * print_status : 0
         * guser_price : 10.00
         * is_train : 0
         * is_special : 1
         * min_dada_weight : 1.0
         * thumb : http://test.7chezhibo.com/storage/app/uploads/member/thumb/202007311037065f2383d22cc41.jpg
         * cancel_weight_status : 0
         * goods_weight :
         * unit : /把
         * spec_name : 中
         * min_title : null
         * max_title : null
         */
        private String unit_name;

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        private int rec_id;
        private int order_id;
        private int goods_id;
        private String spec_id;
        private String goods_name;
        private String goods_price;
        private String final_price;
        private String weight;
        private int dada_weight;
        private String member_goods_price;
        private int print_status;
        private String guser_price;
        private int is_train;
        private int is_special;
        private String min_dada_weight;
        private String thumb;
        private int cancel_weight_status;
        private String goods_weight;
        private String unit;
        private String spec_name;
        private Object min_title;
        private Object max_title;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(String spec_id) {
            this.spec_id = spec_id;
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

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public int getDada_weight() {
            return dada_weight;
        }

        public void setDada_weight(int dada_weight) {
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

        public String getGuser_price() {
            return guser_price;
        }

        public void setGuser_price(String guser_price) {
            this.guser_price = guser_price;
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

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getSpec_name() {
            return spec_name;
        }

        public void setSpec_name(String spec_name) {
            this.spec_name = spec_name;
        }

        public Object getMin_title() {
            return min_title;
        }

        public void setMin_title(Object min_title) {
            this.min_title = min_title;
        }

        public Object getMax_title() {
            return max_title;
        }

        public void setMax_title(Object max_title) {
            this.max_title = max_title;
        }
    }

}
