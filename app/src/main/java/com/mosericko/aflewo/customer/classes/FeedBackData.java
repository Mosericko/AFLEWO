package com.mosericko.aflewo.customer.classes;

public class FeedBackData {
    String id, sender,receiver, title, message, messageTime, messageDate,reply;

    public FeedBackData(String id, String sender, String receiver, String title, String message, String messageTime, String messageDate, String reply) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.message = message;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
        this.reply = reply;
    }

    public FeedBackData(String title, String message, String messageTime, String messageDate, String reply) {
        this.title = title;
        this.message = message;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
        this.reply = reply;
    }

    public FeedBackData(String id, String sender, String title, String message, String messageTime, String messageDate, String reply) {
        this.id = id;
        this.sender = sender;
        this.title = title;
        this.message = message;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
        this.reply = reply;
    }

    public FeedBackData(String title, String message, String messageDate) {
        this.title = title;
        this.message = message;
        this.messageDate = messageDate;
    }

    public FeedBackData(String id, String title, String message, String messageTime, String messageDate, String reply) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
        this.reply = reply;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public String getId() {
        return id;
    }

    public String getReply() {
        return reply;
    }
}
