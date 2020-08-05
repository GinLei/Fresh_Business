package com.dingxin.fresh.e;

import java.io.Serializable;

public class GreensEntity implements Serializable {


    /**
     * id : 395
     * name : 牛肉
     * title : 牛排
     * thumb : http://test.7chezhibo.com/ueditor/php/upload/image/20200525/1590417817324226.jpg
     * uid : 4500
     * mid : 2
     * price : 10.80
     * make_price : 13.90
     * cost_price : 0.00
     * is_cost : 0
     * is_cost_status : 0
     * unit : 斤
     * is_need_weight : 1
     * is_standard : 1
     * is_hot : 0
     * is_top : 0
     * weight_limit : 0
     * total_weight : 0
     * note_name : 牛排牛排
     * on_sale : 1
     * created_at : 2020-07-18 14:14:23
     * updated_at : 2020-07-18 14:14:23
     * greens_cate_id : 8
     * tab_id : 1
     * tab_name : 肉类
     * cid : 8
     * cname : 牛肉
     * top_cname : 牛肉类
     * goods_specs : [{"min_title":"小包","max_title":"","spec_id":"202007185804","goods_price":"10.00","schedule_price":"13.00","unit_name":"斤","price_old":10.8,"money_old":13.9,"greens_id":395},{"min_title":"大包","max_title":"","spec_id":"202007183171","goods_price":"20.00","schedule_price":"30.00","unit_name":"斤","price_old":21.1,"money_old":31.4,"greens_id":395}]
     * origin_price : 10.00
     * spec_id : 202007185804
     * spec_name : 小包斤
     * is_cut : 1
     * min_weight : 0
     * max_weight : 0
     * is_exist_specs : true
     */
    private int spec_num_limit;

    public int getSpec_num_limit() {
        return spec_num_limit;
    }

    public void setSpec_num_limit(int spec_num_limit) {
        this.spec_num_limit = spec_num_limit;
    }

    private String goods_weight;

    public String getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(String goods_weight) {
        this.goods_weight = goods_weight;
    }

    private int id;
    private String name;
    private String title;
    private String thumb;
    private int uid;
    private int mid;
    private String price;
    private String make_price;
    private String cost_price;
    private int is_cost;
    private int is_cost_status;
    private String unit;
    private String unit_name;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    private int is_need_weight;
    private int is_standard;
    private int is_hot;
    private int is_top;
    private int weight_limit;
    private int total_weight;
    private String note_name;
    private int on_sale;
    private String created_at;
    private String updated_at;
    private int greens_cate_id;
    private int tab_id;
    private String tab_name;
    private int cid;
    private String cname;
    private String top_cname;
    private String goods_specs;
    private String origin_price;
    private String spec_id;
    private String spec_name;
    private int is_cut;
    //    private int min_weight;
//    private int max_weight;
    private boolean is_exist_specs;
    private String freight_fee;
    private String service_fee;
    private String goods_specs_weight;

    public String getGoods_specs_weight() {
        return goods_specs_weight;
    }

    public void setGoods_specs_weight(String goods_specs_weight) {
        this.goods_specs_weight = goods_specs_weight;
    }

    public String getFreight_fee() {
        return freight_fee;
    }

    public void setFreight_fee(String freight_fee) {
        this.freight_fee = freight_fee;
    }

    public String getService_fee() {
        return service_fee;
    }

    public void setService_fee(String service_fee) {
        this.service_fee = service_fee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMake_price() {
        return make_price;
    }

    public void setMake_price(String make_price) {
        this.make_price = make_price;
    }

    public String getCost_price() {
        return cost_price;
    }

    public void setCost_price(String cost_price) {
        this.cost_price = cost_price;
    }

    public int getIs_cost() {
        return is_cost;
    }

    public void setIs_cost(int is_cost) {
        this.is_cost = is_cost;
    }

    public int getIs_cost_status() {
        return is_cost_status;
    }

    public void setIs_cost_status(int is_cost_status) {
        this.is_cost_status = is_cost_status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getIs_need_weight() {
        return is_need_weight;
    }

    public void setIs_need_weight(int is_need_weight) {
        this.is_need_weight = is_need_weight;
    }

    public int getIs_standard() {
        return is_standard;
    }

    public void setIs_standard(int is_standard) {
        this.is_standard = is_standard;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getWeight_limit() {
        return weight_limit;
    }

    public void setWeight_limit(int weight_limit) {
        this.weight_limit = weight_limit;
    }

    public int getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(int total_weight) {
        this.total_weight = total_weight;
    }

    public String getNote_name() {
        return note_name;
    }

    public void setNote_name(String note_name) {
        this.note_name = note_name;
    }

    public int getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(int on_sale) {
        this.on_sale = on_sale;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getGreens_cate_id() {
        return greens_cate_id;
    }

    public void setGreens_cate_id(int greens_cate_id) {
        this.greens_cate_id = greens_cate_id;
    }

    public int getTab_id() {
        return tab_id;
    }

    public void setTab_id(int tab_id) {
        this.tab_id = tab_id;
    }

    public String getTab_name() {
        return tab_name;
    }

    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTop_cname() {
        return top_cname;
    }

    public void setTop_cname(String top_cname) {
        this.top_cname = top_cname;
    }

    public String getGoods_specs() {
        return goods_specs;
    }

    public void setGoods_specs(String goods_specs) {
        this.goods_specs = goods_specs;
    }

    public String getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(String origin_price) {
        this.origin_price = origin_price;
    }

    public String getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(String spec_id) {
        this.spec_id = spec_id;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public int getIs_cut() {
        return is_cut;
    }

    public void setIs_cut(int is_cut) {
        this.is_cut = is_cut;
    }

//    public int getMin_weight() {
//        return min_weight;
//    }
//
//    public void setMin_weight(int min_weight) {
//        this.min_weight = min_weight;
//    }
//
//    public int getMax_weight() {
//        return max_weight;
//    }
//
//    public void setMax_weight(int max_weight) {
//        this.max_weight = max_weight;
//    }

    public boolean isIs_exist_specs() {
        return is_exist_specs;
    }

    public void setIs_exist_specs(boolean is_exist_specs) {
        this.is_exist_specs = is_exist_specs;
    }

}
