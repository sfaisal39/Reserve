package io.approots.reserve.Models;

public class Car_Gerages {
    String TB_ID,TB_NAME;

    public Car_Gerages(String TB_ID, String TB_NAME) {
        this.TB_ID = TB_ID;
        this.TB_NAME = TB_NAME;
    }

    public String getTB_ID() {
        return TB_ID;
    }

    public void setTB_ID(String TB_ID) {
        this.TB_ID = TB_ID;
    }

    public String getTB_NAME() {
        return TB_NAME;
    }

    public void setTB_NAME(String TB_NAME) {
        this.TB_NAME = TB_NAME;
    }
}
