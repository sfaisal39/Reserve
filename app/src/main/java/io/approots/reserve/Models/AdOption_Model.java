package io.approots.reserve.Models;

public class AdOption_Model {
    String TB_ID, TB_WEB, TB_IMG, TB_DATE;

    public AdOption_Model(String TB_ID, String TB_WEB, String TB_IMG, String TB_DATE) {
        this.TB_ID = TB_ID;
        this.TB_WEB = TB_WEB;
        this.TB_IMG = TB_IMG;
        this.TB_DATE = TB_DATE;
    }

    public String getTB_ID() {
        return TB_ID;
    }

    public void setTB_ID(String TB_ID) {
        this.TB_ID = TB_ID;
    }

    public String getTB_WEB() {
        return TB_WEB;
    }

    public void setTB_WEB(String TB_WEB) {
        this.TB_WEB = TB_WEB;
    }

    public String getTB_IMG() {
        return TB_IMG;
    }

    public void setTB_IMG(String TB_IMG) {
        this.TB_IMG = TB_IMG;
    }

    public String getTB_DATE() {
        return TB_DATE;
    }

    public void setTB_DATE(String TB_DATE) {
        this.TB_DATE = TB_DATE;
    }
}
