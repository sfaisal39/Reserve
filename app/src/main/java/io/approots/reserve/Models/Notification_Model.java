package io.approots.reserve.Models;

public class Notification_Model {
    String TB_NAME,TB_DESC,TB_DATE,TB_ID;

    public Notification_Model(String TB_NAME, String TB_DESC, String TB_DATE, String TB_ID) {
        this.TB_NAME = TB_NAME;
        this.TB_DESC = TB_DESC;
        this.TB_DATE = TB_DATE;
        this.TB_ID = TB_ID;
    }

    public String getTB_NAME() {
        return TB_NAME;
    }

    public void setTB_NAME(String TB_NAME) {
        this.TB_NAME = TB_NAME;
    }

    public String getTB_DESC() {
        return TB_DESC;
    }

    public void setTB_DESC(String TB_DESC) {
        this.TB_DESC = TB_DESC;
    }

    public String getTB_DATE() {
        return TB_DATE;
    }

    public void setTB_DATE(String TB_DATE) {
        this.TB_DATE = TB_DATE;
    }

    public String getTB_ID() {
        return TB_ID;
    }

    public void setTB_ID(String TB_ID) {
        this.TB_ID = TB_ID;
    }
}
