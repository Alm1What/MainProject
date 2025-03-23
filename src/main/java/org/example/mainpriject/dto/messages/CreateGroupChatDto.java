package org.example.mainpriject.dto.messages;

import org.example.mainpriject.enum_model.GroupType;

import java.util.List;

public class CreateGroupChatDto {

    private String name;
    private String description;
    private boolean isPrivate;
    private GroupType type;
    private String avatarUrl;
    private List<Long> initialParticipantIds;

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

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<Long> getInitialParticipantIds() {
        return initialParticipantIds;
    }

    public void setInitialParticipantIds(List<Long> initialParticipantIds) {
        this.initialParticipantIds = initialParticipantIds;
    }
}
