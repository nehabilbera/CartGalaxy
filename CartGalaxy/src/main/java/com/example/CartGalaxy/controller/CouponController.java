package com.example.CartGalaxy.controller;

import com.example.CartGalaxy.model.ApiResponse;
import com.example.CartGalaxy.model.Coupon;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @GetMapping
    public ApiResponse<List<Coupon>> getCouponList() {
        return ApiResponse.success(List.of(), "Get coupons list");
    }

    @GetMapping("/{coupon_id}")
    public ApiResponse<Coupon> getCoupon(@PathVariable int coupon_id) {
        return ApiResponse.success(new Coupon(), "Get coupon with coupon id: " + coupon_id);
    }

    @PostMapping
    public ApiResponse<List<Coupon>> addCoupon(@RequestBody List<Coupon> coupon) {
        return ApiResponse.success(coupon, "Added coupons");
    }

    @PutMapping
    public ApiResponse<List<Coupon>> updateCouponList(@RequestBody List<Coupon> update_coupon) {
        return ApiResponse.success(update_coupon, "Update coupons");
    }

    @PutMapping("/{coupon_id}")
    public ApiResponse<Coupon> updateCoupon(@PathVariable int coupon_id, @RequestBody Coupon update_coupon) {
        return ApiResponse.success(update_coupon, "Updated coupon with coupon id: " + coupon_id);
    }

    @DeleteMapping("/{coupon_id}")
    public ApiResponse<String> deleteCoupon(@PathVariable int coupon_id) {
        return ApiResponse.success("Deleted", "Delete coupon with coupon id: " + coupon_id);
    }
}
