package com.mosericko.aflewo.customer;

public class CategoryInfo {
    String id;
    String image;
    String name;
    String color;
    String price;
    String category,size;

    public CategoryInfo(String id, String image, String name, String color, String price, String category, String size) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.color = color;
        this.price = price;
        this.category = category;
        this.size = size;
    }

    public CategoryInfo(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getId() {
        return id;
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

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
