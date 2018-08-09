package com.firefly.es.service.dto;

import java.util.Date;

/**
 *@author walter
 *@Date 2018/08/08
 */
public class CompanyDto {


    /**
     * 公司id
     */
    private String id;


    /**
     * 经营范围
     */
    private String businessscope;


    /**
     * 行业
     */
    private String industry;

    /**
     * 法人
     */
    private String legalpersonname;

    /**
     * 公司名称
     */
    private String name;


    /**
     * 组织号
     */
    private String orgnumber;


    /**
     * 注册号
     */
    private String regcapital;


    /**
     * 关态
     */
    private String regnumber;


    private String regstatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessscope() {
        return businessscope;
    }

    public void setBusinessscope(String businessscope) {
        this.businessscope = businessscope;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLegalpersonname() {
        return legalpersonname;
    }

    public void setLegalpersonname(String legalpersonname) {
        this.legalpersonname = legalpersonname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgnumber() {
        return orgnumber;
    }

    public void setOrgnumber(String orgnumber) {
        this.orgnumber = orgnumber;
    }

    public String getRegcapital() {
        return regcapital;
    }

    public void setRegcapital(String regcapital) {
        this.regcapital = regcapital;
    }

    public String getRegnumber() {
        return regnumber;
    }

    public void setRegnumber(String regnumber) {
        this.regnumber = regnumber;
    }

    public String getRegstatus() {
        return regstatus;
    }

    public void setRegstatus(String regstatus) {
        this.regstatus = regstatus;
    }
}
