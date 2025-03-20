package org.example.mainpriject.dto;

import java.time.Instant;
import java.util.List;

public class GroupChatDto {

    private String name;
    private Long creatorId;
    private List<Long> participants;
    private Instant createdAt;
    private String description;
    private boolean isPrivate;

    public GroupChatDto() {
    }

    public GroupChatDto(String name, Long creatorId, List<Long> participants, Instant createdAt, String description, boolean isPrivate) {
        this.name = name;
        this.creatorId = creatorId;
        this.participants = participants;
        this.createdAt = createdAt;
        this.description = description;
        this.isPrivate = isPrivate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public List<Long> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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
}
