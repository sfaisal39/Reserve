package io.approots.reserve.Models;

public class PROD_Model {
    String TB_NAME,TB_DESC,TB_ADDRESS,TB_ABOUT,TB_LATI,TB_LONG,TB_PHONE1,TB_PHONE2,TB_IMG,TB_ID,BRANCHES_ARRAY;

    public PROD_Model(String TB_NAME, String TB_DESC, String TB_ADDRESS, String TB_ABOUT, String TB_LATI, String TB_LONG, String TB_PHONE1, String TB_PHONE2, String TB_IMG, String TB_ID, String BRANCHES_ARRAY) {
        this.TB_NAME = TB_NAME;
        this.TB_DESC = TB_DESC;
        this.TB_ADDRESS = TB_ADDRESS;
        this.TB_ABOUT = TB_ABOUT;
        this.TB_LATI = TB_LATI;
        this.TB_LONG = TB_LONG;
        this.TB_PHONE1 = TB_PHONE1;
        this.TB_PHONE2 = TB_PHONE2;
        this.TB_IMG = TB_IMG;
        this.TB_ID = TB_ID;
        this.BRANCHES_ARRAY = BRANCHES_ARRAY;
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

    public String getTB_ADDRESS() {
        return TB_ADDRESS;
    }

    public void setTB_ADDRESS(String TB_ADDRESS) {
        this.TB_ADDRESS = TB_ADDRESS;
    }

    public String getTB_ABOUT() {
        return TB_ABOUT;
    }

    public void setTB_ABOUT(String TB_ABOUT) {
        this.TB_ABOUT = TB_ABOUT;
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

    public String getTB_IMG() {
        return TB_IMG;
    }

    public void setTB_IMG(String TB_IMG) {
        this.TB_IMG = TB_IMG;
    }

    public String getTB_ID() {
        return TB_ID;
    }

    public void setTB_ID(String TB_ID) {
        this.TB_ID = TB_ID;
    }

    public String getBRANCHES_ARRAY() {
        return BRANCHES_ARRAY;
    }

    public void setBRANCHES_ARRAY(String BRANCHES_ARRAY) {
        this.BRANCHES_ARRAY = BRANCHES_ARRAY;
    }
}
