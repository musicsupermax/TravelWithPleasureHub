package com.kh021j.travelwithpleasurehub.model;

/**
 * Created by ${JDEEK} on ${11.11.2018}.
 */

public class ChatMessage {

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    private MessageType messageType;
    private String content;
    private String sender;

    public ChatMessage() {
    }

    public MessageType getType() {
        return messageType;
    }

    public void setType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
