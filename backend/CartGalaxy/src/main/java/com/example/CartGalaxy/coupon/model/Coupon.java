package com.example.CartGalaxy.coupon.model;

import java.time.LocalDate;

public class Coupon {
    private int coupon_id;
    private String code;
    private float discount;
    private int total;
    private int used_count;
    private LocalDate valid_from;
    private LocalDate valid_to;
    private int user_id;

    public Coupon() {}

    public Coupon(int coupon_id, String code, float discount, int total,
                  int used_count, LocalDate valid_from, LocalDate valid_to, int user_id) {
        this.coupon_id = coupon_id;
        this.code = code;
        this.discount = discount;
        this.total = total;
        this.used_count = used_count;
        this.valid_from = valid_from;
        this.valid_to = valid_to;
        this.user_id = user_id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUsed_count() {
        return used_count;
    }

    public void setUsed_count(int used_count) {
        this.used_count = used_count;
    }

    public LocalDate getValid_from() {
        return valid_from;
    }

    public void setValid_from(LocalDate valid_from) {
        this.valid_from = valid_from;
    }

    public LocalDate getValid_to() {
        return valid_to;
    }

    public void setValid_to(LocalDate valid_to) {
        this.valid_to = valid_to;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
