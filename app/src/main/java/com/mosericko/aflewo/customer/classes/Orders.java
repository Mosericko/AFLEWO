package com.mosericko.aflewo.customer.classes;

public class Orders {
    String orderId, orderNo, mpesaCode, cust_id, orderDate, orderTime, amountPaid, orderStatus;

    public Orders(String orderNo, String mpesaCode, String orderDate, String orderTime, String amountPaid, String orderStatus) {
        this.orderNo = orderNo;
        this.mpesaCode = mpesaCode;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.amountPaid = amountPaid;
        this.orderStatus = orderStatus;
    }

    public Orders(String orderId, String orderNo, String mpesaCode, String cust_id, String orderDate, String orderTime, String amountPaid, String orderStatus) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.mpesaCode = mpesaCode;
        this.cust_id = cust_id;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.amountPaid = amountPaid;
        this.orderStatus = orderStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getMpesaCode() {
        return mpesaCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCust_id() {
        return cust_id;
    }
}
