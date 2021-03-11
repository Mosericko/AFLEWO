package com.mosericko.aflewo.customer;


public class Products {
    String id;
    String productImage;
    String productName;
    String color;
    String price;
    String category,size;

    public Products(String id, String productImage, String productName, String color, String price, String category, String size) {
        this.id = id;
        this.productImage = productImage;
        this.productName = productName;
        this.color = color;
        this.price = price;
        this.category = category;
        this.size = size;
    }

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
}