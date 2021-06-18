package com.mosericko.aflewo.customer.classes;

public class OrderItems {
    String name,color,price,category,size,quantity;

    public OrderItems(String name, String color, String price, String category, String size, String quantity) {
        this.name = name;
        this.color = color;
        this.price = price;
        this.category = category;
        this.size = size;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
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

    public String getQuantity() {
        return quantity;
    }
}
