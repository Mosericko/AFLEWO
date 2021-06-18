package com.mosericko.aflewo.customer.classes;

public class CategoryInfo {
    String id;
    String image;
    String name;
    String color;
    String price;
    String category,size,quantity;

    public CategoryInfo(String id, String image, String name, String color, String price, String category, String size,String quantity) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.color = color;
        this.price = price;
        this.category = category;
        this.size = size;
        this.quantity = quantity;
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

    public String getQuantity() {
        return quantity;
    }
}
