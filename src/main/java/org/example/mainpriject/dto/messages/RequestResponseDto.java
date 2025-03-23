package org.example.mainpriject.dto.messages;

import org.example.mainpriject.enum_model.RequestStatus;
import org.example.mainpriject.model.GroupChatRequest;

public class RequestResponseDto {

    private String requestId;
    private RequestStatus status;

    public RequestResponseDto() {
    }

    public RequestResponseDto(String requestId, RequestStatus status) {
        this.requestId = requestId;
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
