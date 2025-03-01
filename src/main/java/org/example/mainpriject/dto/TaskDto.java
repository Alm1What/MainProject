package org.example.mainpriject.dto;

import org.example.mainpriject.model.Task;

public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private String ownerEmail;
    private String ownerName;

    public TaskDto() {}

    // Конструктор для створення з сутності Task
    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.completed = task.isCompleted();

        if (task.getOwner() != null) {
            this.ownerEmail = task.getOwner().getEmail();
            this.ownerName = task.getOwner().getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
