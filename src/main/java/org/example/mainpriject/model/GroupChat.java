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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "group_chat")
public class GroupChat {

    @Id
    private String groupId;

    @TextIndexed
    private String name;
    private Long creatorId;
    private List<Long> participants;
    private Instant createdAt;
    private String description;
    private boolean isPrivate;
    // Як варіант додати поле чи це "ЧАТ" або "КАНАЛ" -> типу як в телеграм можна це через Enum

    public GroupChat() {
    }

    public GroupChat(String name, Long creatorId, List<Long> participants, Instant createdAt, String description, boolean isPrivate, String groupId) {
        this.name = name;
        this.creatorId = creatorId;
        this.participants = participants;
        this.createdAt = createdAt;
        this.description = description;
        this.isPrivate = isPrivate;
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
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
