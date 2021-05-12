package com.mosericko.aflewo.customer;

public class CartDetails {
    String id;
    String productImage;
    String productName;
    String color;
    String price;
    String category,size;
    String quantity = "1";

    public CartDetails() {
    }

    public CartDetails(String id, String productImage, String productName, String color, String price, String category, String size) {
        this.id = id;
        this.productImage = productImage;
        this.productName = productName;
        this.color = color;
        this.price = price;
        this.category = category;
        this.size = size;
    }

    public CartDetails(String id, String productImage, String productName, String color, String price, String category, String size, String quantity) {
        this.id = id;
        this.productImage = productImage;
        this.productName = productName;
        this.color = color;
        this.price = price;
        this.category = category;
        this.size = size;
        this.quantity = quantity;
    }

    public CartDetails(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
