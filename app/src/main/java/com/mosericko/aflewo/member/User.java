package com.mosericko.aflewo.member;

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String gender;
    private String email;
    private String phonenumber;
    private String usertype;
    private String status;

    public User(int id, String firstname, String lastname, String gender, String email, String phonenumber, String usertype) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
        this.phonenumber = phonenumber;
        this.usertype = usertype;
    }

    public User(String firstname, String lastname, String gender/*, String email, String phonenumber*/) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        /*this.email = email;
        this.phonenumber = phonenumber;*/
    }

    public User(int id, String firstname, String lastname, String gender, String email, String phonenumber, String usertype, String status) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
        this.phonenumber = phonenumber;
        this.usertype = usertype;
        this.status = status;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getUsertype() {
        return usertype;
    }
}
