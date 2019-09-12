package io.approots.reserve.Models;

public class Branch_Model {
    String TB_NAME, TB_ADDRESS, TB_LATI, TB_LONG, TB_PHONE1, TB_PHONE2, TB_PHONE3;

    public Branch_Model(String TB_NAME, String TB_ADDRESS, String TB_LATI, String TB_LONG, String TB_PHONE1, String TB_PHONE2, String TB_PHONE3) {
        this.TB_NAME = TB_NAME;
        this.TB_ADDRESS = TB_ADDRESS;
        this.TB_LATI = TB_LATI;
        this.TB_LONG = TB_LONG;
        this.TB_PHONE1 = TB_PHONE1;
        this.TB_PHONE2 = TB_PHONE2;
        this.TB_PHONE3 = TB_PHONE3;
    }

    public String getTB_NAME() {
        return TB_NAME;
    }

    public void setTB_NAME(String TB_NAME) {
        this.TB_NAME = TB_NAME;
    }

    public String getTB_ADDRESS() {
        return TB_ADDRESS;
    }

    public void setTB_ADDRESS(String TB_ADDRESS) {
        this.TB_ADDRESS = TB_ADDRESS;
    }

    public String getTB_LATI() {
        return TB_LATI;
    }

    public void setTB_LATI(String TB_LATI) {
        this.TB_LATI = TB_LATI;
    }

    public String getTB_LONG() {
        return TB_LONG;
    }

    public void setTB_LONG(String TB_LONG) {
        this.TB_LONG = TB_LONG;
    }

    public String getTB_PHONE1() {
        return TB_PHONE1;
    }

    public void setTB_PHONE1(String TB_PHONE1) {
        this.TB_PHONE1 = TB_PHONE1;
    }

    public String getTB_PHONE2() {
        return TB_PHONE2;
    }

    public void setTB_PHONE2(String TB_PHONE2) {
        this.TB_PHONE2 = TB_PHONE2;
    }

    public String getTB_PHONE3() {
        return TB_PHONE3;
    }

    public void setTB_PHONE3(String TB_PHONE3) {
        this.TB_PHONE3 = TB_PHONE3;
    }
}
