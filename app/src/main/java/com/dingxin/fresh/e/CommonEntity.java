package com.dingxin.fresh.e;

import com.google.gson.annotations.SerializedName;

public class CommonEntity {
    private int id;
    private String name;
    private String new_price;
    private String thumb;
    private int on_sale;
    private String freight_fee;
    private String service_fee;
    private Boolean is_on_sale;

    public Boolean getIs_on_sale() {
        return is_on_sale;
    }

    public void setIs_on_sale(Boolean is_on_sale) {
        this.is_on_sale = is_on_sale;
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

    public int getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(int on_sale) {
        this.on_sale = on_sale;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getNew_price() {
        return new_price;
    }

    public void setNew_price(String new_price) {
        this.new_price = new_price;
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


    /**
     * id : 137
     * parent_id : 1
     * name : 猪肉类
     * level : 2
     * unit : 斤
     * is_standard : 1
     * spec_num_limit : null
     */
    private int parent_id;
    private String level;
    private String unit;
    private int is_standard;
    private int spec_num_limit;


    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getIs_standard() {
        return is_standard;
    }

    public void setIs_standard(int is_standard) {
        this.is_standard = is_standard;
    }

    public int getSpec_num_limit() {
        return spec_num_limit;
    }

    public void setSpec_num_limit(int spec_num_limit) {
        this.spec_num_limit = spec_num_limit;
    }
}
