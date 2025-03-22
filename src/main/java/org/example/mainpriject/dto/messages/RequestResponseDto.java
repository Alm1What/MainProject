package org.example.mainpriject.dto.messages;

import org.example.mainpriject.model.GroupChatRequest;

public class RequestResponseDto {

    private String requestId;
    private GroupChatRequest.RequestStatus status;

    public RequestResponseDto() {
    }

    public RequestResponseDto(String requestId, GroupChatRequest.RequestStatus status) {
        this.requestId = requestId;
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public GroupChatRequest.RequestStatus getStatus() {
        return status;
    }

    public void setStatus(GroupChatRequest.RequestStatus status) {
        this.status = status;
    }
}
