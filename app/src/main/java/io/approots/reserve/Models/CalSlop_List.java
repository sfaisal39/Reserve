package io.approots.reserve.Models;

public class CalSlop_List {
    String TB_TIME,FULL_DATE,TB_NAME,TB_TYPE,TB_DELIVERY_SLOTID,TB_STATUS,TB_STATUS_LABEL,TB_PLATE_NO,TB_PHONE,TB_CUST_NAME,ORDER_ID;

    public CalSlop_List(String TB_TIME, String FULL_DATE, String TB_NAME, String TB_TYPE, String TB_DELIVERY_SLOTID, String TB_STATUS, String TB_STATUS_LABEL, String TB_PLATE_NO, String TB_PHONE, String TB_CUST_NAME, String ORDER_ID) {
        this.TB_TIME = TB_TIME;
        this.FULL_DATE = FULL_DATE;
        this.TB_NAME = TB_NAME;
        this.TB_TYPE = TB_TYPE;
        this.TB_DELIVERY_SLOTID = TB_DELIVERY_SLOTID;
        this.TB_STATUS = TB_STATUS;
        this.TB_STATUS_LABEL = TB_STATUS_LABEL;
        this.TB_PLATE_NO = TB_PLATE_NO;
        this.TB_PHONE = TB_PHONE;
        this.TB_CUST_NAME = TB_CUST_NAME;
        this.ORDER_ID = ORDER_ID;
    }

    public String getTB_TIME() {
        return TB_TIME;
    }

    public void setTB_TIME(String TB_TIME) {
        this.TB_TIME = TB_TIME;
    }

    public String getFULL_DATE() {
        return FULL_DATE;
    }

    public void setFULL_DATE(String FULL_DATE) {
        this.FULL_DATE = FULL_DATE;
    }

    public String getTB_NAME() {
        return TB_NAME;
    }

    public void setTB_NAME(String TB_NAME) {
        this.TB_NAME = TB_NAME;
    }

    public String getTB_TYPE() {
        return TB_TYPE;
    }

    public void setTB_TYPE(String TB_TYPE) {
        this.TB_TYPE = TB_TYPE;
    }

    public String getTB_DELIVERY_SLOTID() {
        return TB_DELIVERY_SLOTID;
    }

    public void setTB_DELIVERY_SLOTID(String TB_DELIVERY_SLOTID) {
        this.TB_DELIVERY_SLOTID = TB_DELIVERY_SLOTID;
    }

    public String getTB_STATUS() {
        return TB_STATUS;
    }

    public void setTB_STATUS(String TB_STATUS) {
        this.TB_STATUS = TB_STATUS;
    }

    public String getTB_STATUS_LABEL() {
        return TB_STATUS_LABEL;
    }

    public void setTB_STATUS_LABEL(String TB_STATUS_LABEL) {
        this.TB_STATUS_LABEL = TB_STATUS_LABEL;
    }

    public String getTB_PLATE_NO() {
        return TB_PLATE_NO;
    }

    public void setTB_PLATE_NO(String TB_PLATE_NO) {
        this.TB_PLATE_NO = TB_PLATE_NO;
    }

    public String getTB_PHONE() {
        return TB_PHONE;
    }

    public void setTB_PHONE(String TB_PHONE) {
        this.TB_PHONE = TB_PHONE;
    }

    public String getTB_CUST_NAME() {
        return TB_CUST_NAME;
    }

    public void setTB_CUST_NAME(String TB_CUST_NAME) {
        this.TB_CUST_NAME = TB_CUST_NAME;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }
}
