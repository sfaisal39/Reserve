package io.approots.reserve.Models;

public class TimeSlop_List {
    String TB_TIME,ORDER_LIMIT,ORDER_DONE,ORDER_REMAIN,SLOT_AVAIABLE,SAVE_GREEN,FULL_DATE,TB_ID,SLOT_ID,SLOT_AVAIABLE_LABEL;

    public TimeSlop_List(String TB_TIME, String ORDER_LIMIT, String ORDER_DONE, String ORDER_REMAIN, String SLOT_AVAIABLE, String SAVE_GREEN, String FULL_DATE, String TB_ID, String SLOT_ID, String SLOT_AVAIABLE_LABEL) {
        this.TB_TIME = TB_TIME;
        this.ORDER_LIMIT = ORDER_LIMIT;
        this.ORDER_DONE = ORDER_DONE;
        this.ORDER_REMAIN = ORDER_REMAIN;
        this.SLOT_AVAIABLE = SLOT_AVAIABLE;
        this.SAVE_GREEN = SAVE_GREEN;
        this.FULL_DATE = FULL_DATE;
        this.TB_ID = TB_ID;
        this.SLOT_ID = SLOT_ID;
        this.SLOT_AVAIABLE_LABEL = SLOT_AVAIABLE_LABEL;
    }

    public String getTB_TIME() {
        return TB_TIME;
    }

    public void setTB_TIME(String TB_TIME) {
        this.TB_TIME = TB_TIME;
    }

    public String getORDER_LIMIT() {
        return ORDER_LIMIT;
    }

    public void setORDER_LIMIT(String ORDER_LIMIT) {
        this.ORDER_LIMIT = ORDER_LIMIT;
    }

    public String getORDER_DONE() {
        return ORDER_DONE;
    }

    public void setORDER_DONE(String ORDER_DONE) {
        this.ORDER_DONE = ORDER_DONE;
    }

    public String getORDER_REMAIN() {
        return ORDER_REMAIN;
    }

    public void setORDER_REMAIN(String ORDER_REMAIN) {
        this.ORDER_REMAIN = ORDER_REMAIN;
    }

    public String getSLOT_AVAIABLE() {
        return SLOT_AVAIABLE;
    }

    public void setSLOT_AVAIABLE(String SLOT_AVAIABLE) {
        this.SLOT_AVAIABLE = SLOT_AVAIABLE;
    }

    public String getSAVE_GREEN() {
        return SAVE_GREEN;
    }

    public void setSAVE_GREEN(String SAVE_GREEN) {
        this.SAVE_GREEN = SAVE_GREEN;
    }

    public String getFULL_DATE() {
        return FULL_DATE;
    }

    public void setFULL_DATE(String FULL_DATE) {
        this.FULL_DATE = FULL_DATE;
    }

    public String getTB_ID() {
        return TB_ID;
    }

    public void setTB_ID(String TB_ID) {
        this.TB_ID = TB_ID;
    }

    public String getSLOT_ID() {
        return SLOT_ID;
    }

    public void setSLOT_ID(String SLOT_ID) {
        this.SLOT_ID = SLOT_ID;
    }

    public String getSLOT_AVAIABLE_LABEL() {
        return SLOT_AVAIABLE_LABEL;
    }

    public void setSLOT_AVAIABLE_LABEL(String SLOT_AVAIABLE_LABEL) {
        this.SLOT_AVAIABLE_LABEL = SLOT_AVAIABLE_LABEL;
    }
}
