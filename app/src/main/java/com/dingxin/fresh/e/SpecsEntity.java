package com.dingxin.fresh.e;

public class SpecsEntity {
    private String min_title;
    private String max_title;
    private String goods_price;
    private String schedule_price;
    private String spec_id;
    private String unit_name;
    private String price_old;
    private String money_old;
    private String goods_weight;
    private String spec_name;
    private String goods_specs_weight;
    private Boolean is_on_sale;

    public Boolean getIs_on_sale() {
        return is_on_sale;
    }

    public void setIs_on_sale(Boolean is_on_sale) {
        this.is_on_sale = is_on_sale;
    }

    public String getGoods_specs_weight() {
        return goods_specs_weight;
    }

    public void setGoods_specs_weight(String goods_specs_weight) {
        this.goods_specs_weight = goods_specs_weight;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(String goods_weight) {
        this.goods_weight = goods_weight;
    }

    public String getPrice_old() {
        return price_old;
    }

    public void setPrice_old(String price_old) {
        this.price_old = price_old;
    }

    public String getMoney_old() {
        return money_old;
    }

    public void setMoney_old(String money_old) {
        this.money_old = money_old;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(String spec_id) {
        this.spec_id = spec_id;
    }

    public String getMin_title() {
        return min_title;
    }

    public void setMin_title(String min_title) {
        this.min_title = min_title;
    }

    public String getMax_title() {
        return max_title;
    }

    public void setMax_title(String max_title) {
        this.max_title = max_title;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getSchedule_price() {
        return schedule_price;
    }

    public void setSchedule_price(String schedule_price) {
        this.schedule_price = schedule_price;
    }
}
