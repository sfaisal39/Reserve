package io.approots.reserve.Models;

public class Day_List {
    String DATE,DAY,FULL_DATE,SLOT_AVAIABLE,TB_ID;

    public Day_List(String DATE, String DAY, String FULL_DATE, String SLOT_AVAIABLE, String TB_ID) {
        this.DATE = DATE;
        this.DAY = DAY;
        this.FULL_DATE = FULL_DATE;
        this.SLOT_AVAIABLE = SLOT_AVAIABLE;
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

    public String getFULL_DATE() {
        return FULL_DATE;
    }

    public void setFULL_DATE(String FULL_DATE) {
        this.FULL_DATE = FULL_DATE;
    }

    public String getSLOT_AVAIABLE() {
        return SLOT_AVAIABLE;
    }

    public void setSLOT_AVAIABLE(String SLOT_AVAIABLE) {
        this.SLOT_AVAIABLE = SLOT_AVAIABLE;
    }

    public String getTB_ID() {
        return TB_ID;
    }

    public void setTB_ID(String TB_ID) {
        this.TB_ID = TB_ID;
    }
}
