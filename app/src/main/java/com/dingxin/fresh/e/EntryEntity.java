package com.dingxin.fresh.e;

public class EntryEntity {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EntryEntity(int icon, String info, String url) {
        this.icon = icon;
        this.info = info;
        this.url = url;
    }

    private int icon;
    private String info;
    private String url;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
