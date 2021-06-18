package com.mosericko.aflewo.customer.classes;

public class FeedBackData {
    String sender,receiver, title, message, messageTime, messageDate;

    public FeedBackData(String sender, String receiver, String title, String message, String messageTime, String messageDate) {
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.message = message;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
    }

    public FeedBackData(String title, String message, String messageTime, String messageDate) {
        this.title = title;
        this.message = message;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
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
}
