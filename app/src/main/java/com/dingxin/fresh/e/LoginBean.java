package com.dingxin.fresh.e;

public class LoginBean {
    public int errcode;
    public String errinfo;
    public String user_id;
    public String p2pserver_ip;
    public int p2pserver_port;
    public String pushserver_ip;
    public int push_port;
    public String user_token;
    private String nat1;
    private String nat2;


    private String area;

    public String getNat1() {
        return nat1;
    }

    public void setNat1(String nat1) {
        this.nat1 = nat1;
    }

    public String getNat2() {
        return nat2;
    }

    public void setNat2(String nat2) {
        this.nat2 = nat2;
    }
    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrinfo() {
        return errinfo;
    }

    public void setErrinfo(String errinfo) {
        this.errinfo = errinfo;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getP2pserver_ip() {
        return p2pserver_ip;
    }

    public void setP2pserver_ip(String p2pserver_ip) {
        this.p2pserver_ip = p2pserver_ip;
    }

    public int getP2pserver_port() {
        return p2pserver_port;
    }

    public void setP2pserver_port(int p2pserver_port) {
        this.p2pserver_port = p2pserver_port;
    }

    public String getPushserver_ip() {
        return pushserver_ip;
    }

    public void setPushserver_ip(String pushserver_ip) {
        this.pushserver_ip = pushserver_ip;
    }

    public int getPush_port() {
        return push_port;
    }

    public void setPush_port(int push_port) {
        this.push_port = push_port;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "errcode=" + errcode +
                ", errinfo='" + errinfo + '\'' +
                ", user_id='" + user_id + '\'' +
                ", p2pserver_ip='" + p2pserver_ip + '\'' +
                ", p2pserver_port=" + p2pserver_port +
                ", pushserver_ip='" + pushserver_ip + '\'' +
                ", push_port=" + push_port +
                ", user_token='" + user_token + '\'' +
                ", nat1='" + nat1 + '\'' +
                ", nat2='" + nat2 + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
