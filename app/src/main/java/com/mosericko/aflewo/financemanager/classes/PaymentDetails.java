package com.mosericko.aflewo.financemanager.classes;

public class PaymentDetails {
    String id, userId, amount, mpesa, date, status, firstName, lastName;

    public PaymentDetails(String id, String userId, String amount, String mpesa, String date, String status, String firstName, String lastName) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.mpesa = mpesa;
        this.date = date;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getAmount() {
        return amount;
    }

    public String getMpesa() {
        return mpesa;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
