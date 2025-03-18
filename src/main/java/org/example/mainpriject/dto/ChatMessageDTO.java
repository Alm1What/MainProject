package org.example.mainpriject.dto;

import java.time.Instant;

public class ChatMessageDTO {

    private String id;
    private String senderName;
    private String receiverName;
    private String content;
    private Instant timestamp;

    public ChatMessageDTO() {
    }

    public ChatMessageDTO(String id, String senderName, String receiverName, String content, Instant timestamp) {
        this.id = id;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
