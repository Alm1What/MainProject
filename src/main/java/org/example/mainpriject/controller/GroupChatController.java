package org.example.mainpriject.controller;

import org.example.mainpriject.dto.messages.CreateGroupChatDto;
import org.example.mainpriject.model.User;
import org.example.mainpriject.service.GroupAndChatService.GroupChatService;
import org.example.mainpriject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/group")
public class GroupChatController {

    private GroupChatService groupChatService;
    private UserService userService;

    public GroupChatController(GroupChatService groupChatService, UserService userService) {
        this.groupChatService = groupChatService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> createGroupChat(@RequestBody CreateGroupChatDto groupChatDto) {
        User currentUser = userService.getCurrentUser();
        Long creatorId = currentUser.getId();

        return ResponseEntity.ok(groupChatService.createGroupChat(groupChatDto, creatorId));
    }




}
