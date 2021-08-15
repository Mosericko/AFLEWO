package com.mosericko.aflewo.eventsmanager;

public class Bookings {
    String user_id, firstName, lastName, gender, phone, mpesaCode, paymentStatus, auditionStatus, uniqueId;

    public Bookings(String user_id, String firstName, String lastName, String gender, String phone, String mpesaCode, String paymentStatus, String auditionStatus) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.mpesaCode = mpesaCode;
        this.paymentStatus = paymentStatus;
        this.auditionStatus = auditionStatus;
    }

    public Bookings(String user_id, String firstName, String lastName, String gender, String phone) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
    }

    public Bookings(String user_id, String firstName, String lastName, String gender, String phone, String uniqueId) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.uniqueId = uniqueId;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getPhone() {
        return phone;
    }

    public String getMpesaCode() {
        return mpesaCode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getAuditionStatus() {
        return auditionStatus;
    }
}
