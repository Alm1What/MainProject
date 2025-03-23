package org.example.mainpriject.model;


/*
4. Групові чати
Ідея: Дозвольте користувачам створювати групові чати.

Реалізація:

Додайте сутність GroupChat з полями name, creatorId, participants (список userId).

Змініть ChatMessage, щоб воно могло належати як до групового, так і до приватного чату.
Додати також поле чи приватний чат чи ні (зробити так щоб користувач міг вводити пошук чатів якщо вони не приватні)
І подавати заявку на приєднання туди а користувач який створив цю групу міг одобрити або відхилити заявку користувача
*/

/*
Для такого функціоналу краще розділити на два класи:

GroupChat: Для загальної інформації про групу (назва, опис, тип чату — публічний/приватний).

GroupChatRequest: Для заявок на вступ до приватних чатів (заявка містить userId, groupId, статус заявки).
*/

import org.example.mainpriject.enum_model.GroupType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "group_chat")
public class GroupChat {

    public static final String SEQUENCE_NAME = "group_id_generator";
    @Id
    private String id;
    private Long groupId;
    private String name;
    private Long creatorId;
    private List<Long> participants;
    private List<Long> administrators;
    private Instant createdAt;
    private String description;
    private boolean isPrivate;
    private GroupType type;
    private String avatarUrl;
    private boolean isActive;
    private int messageCount;
    private String lastMessagePreview;
    private Instant lastActivityTimestamp;


    public GroupChat(String id, Long groupId, String name, Long creatorId, List<Long> participants, List<Long> administrators, Instant createdAt, String description, boolean isPrivate, GroupType type, String avatarUrl, boolean isActive, int messageCount, String lastMessagePreview, Instant lastActivityTimestamp) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.creatorId = creatorId;
        this.participants = participants;
        this.administrators = administrators;
        this.createdAt = createdAt;
        this.description = description;
        this.isPrivate = isPrivate;
        this.type = type;
        this.avatarUrl = avatarUrl;
        this.isActive = isActive;
        this.messageCount = messageCount;
        this.lastMessagePreview = lastMessagePreview;
        this.lastActivityTimestamp = lastActivityTimestamp;
    }

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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

    public List<Long> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<Long> administrators) {
        this.administrators = administrators;
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



    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public String getLastMessagePreview() {
        return lastMessagePreview;
    }

    public void setLastMessagePreview(String lastMessagePreview) {
        this.lastMessagePreview = lastMessagePreview;
    }

    public Instant getLastActivityTimestamp() {
        return lastActivityTimestamp;
    }

    public void setLastActivityTimestamp(Instant lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
}
