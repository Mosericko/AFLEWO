package com.mosericko.aflewo.customer.classes;

public class Orders {
    String orderNo,mpesaCode,orderDate,orderTime,amountPaid,orderStatus;

    public Orders(String orderNo, String mpesaCode, String orderDate, String orderTime, String amountPaid, String orderStatus) {
        this.orderNo = orderNo;
        this.mpesaCode = mpesaCode;
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
}
