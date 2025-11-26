package com.example.CartGalaxy.product.model;

public class Product {
    private int product_id;
    private float selling_price;
    private float discounted_price;
    private String description;
    private String brand;
    private String category;
    private String image;
    private Boolean availability;

    public Product(){}

    public Product(int product_id, float selling_price, float discounted_price, String description, String brand, String category, String image, Boolean availability){
        this.product_id = product_id;
        this.selling_price = selling_price;
        this.discounted_price = discounted_price;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.image = image;
        this.availability = availability;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public float getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(float selling_price) {
        this.selling_price = selling_price;
    }

    public float getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(float discounted_price) {
        this.discounted_price = discounted_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}

