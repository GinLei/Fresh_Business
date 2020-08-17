package com.dingxin.fresh.e;

public class LoginEntity {
    private String token;
    private String rtmpUrl;

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    private String nickname;

    private int id;

    private String avatar;

    private String scale_name;

    private String ticket_name;

    private String tab;

    private int usertype;

    private String scale;

    private String ticket;

    private String invite;

    private String info_background_img;

    private String livesid;

    private int is_cooper;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setScale_name(String scale_name) {
        this.scale_name = scale_name;
    }

    public String getScale_name() {
        return this.scale_name;
    }

    public void setTicket_name(String ticket_name) {
        this.ticket_name = ticket_name;
    }

    public String getTicket_name() {
        return this.ticket_name;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getTab() {
        return this.tab;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public int getUsertype() {
        return this.usertype;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getScale() {
        return this.scale;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return this.ticket;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public String getInvite() {
        return this.invite;
    }

    public void setInfo_background_img(String info_background_img) {
        this.info_background_img = info_background_img;
    }

    public String getInfo_background_img() {
        return this.info_background_img;
    }

    public void setLivesid(String livesid) {
        this.livesid = livesid;
    }

    public String getLivesid() {
        return this.livesid;
    }

    public void setIs_cooper(int is_cooper) {
        this.is_cooper = is_cooper;
    }

    public int getIs_cooper() {
        return this.is_cooper;
    }

}
