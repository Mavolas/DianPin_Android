package com.mavolas.dianpin.bean;

/**
 * Author by Andy
 * Date on 2019-04-15.
 */
public class Sellers_Item{
    public String ID;
    public String area;
    public String type;
    public String name;
    public String star;
    public String phone;
    public String commentCount;
    public String address;
    public String status;
    public String remark;


    public Sellers_Item(String id, String area, String type, String name, String star, String phone, String commentCount, String address) {
        this.ID = id;
        this.area = area;
        this.type = type;
        this.name = name;
        this.star = star;
        this.phone = phone;
        this.commentCount = commentCount;
        this.address = address;
    }

    public Sellers_Item(String id, String area, String type, String name, String star, String phone, String commentCount, String address, String status, String remark) {
        this.ID = id;
        this.area = area;
        this.type = type;
        this.name = name;
        this.star = star;
        this.phone = phone;
        this.commentCount = commentCount;
        this.address = address;
        this.status = status;
        this.remark = remark;
    }
}
