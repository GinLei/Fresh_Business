package com.dingxin.fresh.e;

public class TargetEntity {

    /**
     * id : 1
     * parent_id : 0
     * name : 肉类
     * level : 1
     */

    private int id;
    private int parent_id;
    private String name;
    private String level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
