package org.example.mainpriject.dto.messages;

import org.example.mainpriject.model.GroupChat;

import java.util.List;

public class CreateGroupChatRequest {

    private String name;
    private String description;
    private boolean isPrivate;
    private GroupChat.GroupType type;
    private List<Long> initialParticipants;

    public CreateGroupChatRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public GroupChat.GroupType getType() {
        return type;
    }

    public void setType(GroupChat.GroupType type) {
        this.type = type;
    }

    public List<Long> getInitialParticipants() {
        return initialParticipants;
    }

    public void setInitialParticipants(List<Long> initialParticipants) {
        this.initialParticipants = initialParticipants;
    }
}
