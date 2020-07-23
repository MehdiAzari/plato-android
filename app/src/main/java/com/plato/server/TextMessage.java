package com.plato.server;
import java.io.Serializable;
import java.util.Date;

public class TextMessage extends Message implements Serializable {
    private static final long serialVersionUID = -1232451233L;
    private User sender ;
    private String content ;

    public TextMessage(Date date, User sender , String content) {
        super(date);
        this.content = content;
        this.sender = sender ;


    }
    // Testing


    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return super.toString() + "TextMessage{" +
                "sender=" + sender +
                ", content='" + content + '\'' +
                '}';
    }
}

