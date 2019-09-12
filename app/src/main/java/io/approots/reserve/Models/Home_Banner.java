package io.approots.reserve.Models;

public class Home_Banner {
    String TB_NAME,TB_IMG,TB_TYPE,TB_ID;

    public Home_Banner(String TB_NAME, String TB_IMG, String TB_TYPE, String TB_ID) {
        this.TB_NAME = TB_NAME;
        this.TB_IMG = TB_IMG;
        this.TB_TYPE = TB_TYPE;
        this.TB_ID = TB_ID;
    }

    public String getTB_NAME() {
        return TB_NAME;
    }

    public void setTB_NAME(String TB_NAME) {
        this.TB_NAME = TB_NAME;
    }

    public String getTB_IMG() {
        return TB_IMG;
    }

    public void setTB_IMG(String TB_IMG) {
        this.TB_IMG = TB_IMG;
    }

    public String getTB_TYPE() {
        return TB_TYPE;
    }

    public void setTB_TYPE(String TB_TYPE) {
        this.TB_TYPE = TB_TYPE;
    }

    public String getTB_ID() {
        return TB_ID;
    }

    public void setTB_ID(String TB_ID) {
        this.TB_ID = TB_ID;
    }
}
