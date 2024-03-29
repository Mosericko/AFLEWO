package com.mosericko.aflewo.customer.classes;


public class Products {
    String id;
    String productImage;
    String productName;
    String color;
    String price;
    String category, size, quantity;

    public Products(String id, String productImage, String productName, String color, String price, String category, String size, String quantity) {
        this.id = id;
        this.productImage = productImage;
        this.productName = productName;
        this.color = color;
        this.price = price;
        this.category = category;
        this.size = size;
        this.quantity = quantity;
    }

    public Products() {
    }

//    public Products(String id, String productName, String productImage) {
//        this.id = id;
//        this.productName = productName;
//        this.productImage = productImage;
//    }


    public String getId() {
        return id;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getColor() {
        return color;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getSize() {
        return size;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
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