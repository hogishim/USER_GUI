package com.example.user;

public class recipeCooking {

    int idx;
    String cooking_order;
    int cooking_order_no;
    int total_list_ID;

    public recipeCooking(int idx, String cooking_order, int cooking_order_no, int total_list_ID) {
        this.idx = idx;
        this.cooking_order = cooking_order;
        this.cooking_order_no = cooking_order_no;
        this.total_list_ID = total_list_ID;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getCooking_order() {
        return cooking_order;
    }

    public void setCooking_order(String cooking_order) {
        this.cooking_order = cooking_order;
    }

    public int getCooking_order_no() {
        return cooking_order_no;
    }

    public void setCooking_order_no(int cooking_order_no) {
        this.cooking_order_no = cooking_order_no;
    }

    public int getTotal_list_ID() {
        return total_list_ID;
    }

    public void setTotal_list_ID(int total_list_ID) {
        this.total_list_ID = total_list_ID;
    }
}
