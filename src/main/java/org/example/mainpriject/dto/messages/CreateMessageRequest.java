package org.example.mainpriject.dto.messages;

import org.example.mainpriject.model.ChatMessage;

public class CreateMessageRequest {

    private Long receiverId;
    private String groupChatId;
    private String content;
    private ChatMessage.MessageType type;

    public CreateMessageRequest() {
    }

    public CreateMessageRequest(Long receiverId, String groupChatId, String content, ChatMessage.MessageType type) {
        this.receiverId = receiverId;
        this.groupChatId = groupChatId;
        this.content = content;
        this.type = type;
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

    public ChatMessage.MessageType getType() {
        return type;
    }

    public void setType(ChatMessage.MessageType type) {
        this.type = type;
    }
}
