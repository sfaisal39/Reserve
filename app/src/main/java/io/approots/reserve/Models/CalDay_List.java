package io.approots.reserve.Models;

public class CalDay_List {
    String DATE, DAY, Month, FULL_DATE, SLOT_RESERVE, TB_ID;

    public CalDay_List(String DATE, String DAY, String month, String FULL_DATE, String SLOT_RESERVE, String TB_ID) {
        this.DATE = DATE;
        this.DAY = DAY;
        Month = month;
        this.FULL_DATE = FULL_DATE;
        this.SLOT_RESERVE = SLOT_RESERVE;
        this.TB_ID = TB_ID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getDAY() {
        return DAY;
    }

    public void setDAY(String DAY) {
        this.DAY = DAY;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getFULL_DATE() {
        return FULL_DATE;
    }

    public void setFULL_DATE(String FULL_DATE) {
        this.FULL_DATE = FULL_DATE;
    }

    public String getSLOT_RESERVE() {
        return SLOT_RESERVE;
    }

    public void setSLOT_RESERVE(String SLOT_RESERVE) {
        this.SLOT_RESERVE = SLOT_RESERVE;
    }

    public String getTB_ID() {
        return TB_ID;
    }

    public void setTB_ID(String TB_ID) {
        this.TB_ID = TB_ID;
    }
}
