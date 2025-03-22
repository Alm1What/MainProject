package org.example.mainpriject.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "chat_messages")
public class ChatMessage {

    @Id
    private String id;
    private Long senderId;
    private Long receiverId; // Для приватних повідомлень
    private String groupChatId; // ID групового чату, якщо повідомлення в груповому чаті
    private String content;
    private Instant timestamp;
    private boolean isRead;
    private boolean isDeleted;
    private MessageType type;


    public enum MessageType {
        TEXT, IMAGE, FILE, VOICE, VIDEO
    }

    public ChatMessage() {
    }

    public ChatMessage(String id, Long senderId, Long receiverId, String groupChatId, String content, Instant timestamp, boolean isRead, boolean isDeleted, MessageType type) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.groupChatId = groupChatId;
        this.content = content;
        this.timestamp = timestamp;
        this.isRead = isRead;
        this.isDeleted = isDeleted;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(String groupChatId) {
        this.groupChatId = groupChatId;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
