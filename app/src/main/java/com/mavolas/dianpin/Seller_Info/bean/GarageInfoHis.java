package com.mavolas.dianpin.Seller_Info.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 列表画面查询数据：设施信息 / 车库信息
 */
public class GarageInfoHis implements Serializable {
    /**
     * ID
     */
    public String ID;

    /**
     * 设施ID
     */
    public String FacilityID;

    /**
     * 位置信息
     */
    public String LocationInfo;

    /**
     * 名称
     */
    public String Name;

    /**
     * 类型
     */
    public String Type;

    /**
     * 地址
     */
    public String Address;

    /**
     * 车位数
     */
    public Integer ParkingNumber;

    /**
     * 物业公司
     */
    public String PropertyCompany;

    /**
     * 物业经理
     */
    public String PropertyManager;

    /**
     * 联系电话
     */
    public String ContactNumber;

    /**
     * 采集人员
     */
    public String CollectionName;

    /**
     * 采集时间
     */
    public Date CollectionTime;

}
