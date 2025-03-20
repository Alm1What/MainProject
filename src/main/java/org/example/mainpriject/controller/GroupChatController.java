package org.example.mainpriject.controller;


import org.example.mainpriject.model.GroupChat;
import org.example.mainpriject.service.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/group")
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;

    @PostMapping()
    public ResponseEntity<GroupChat> createGroupChat(@RequestBody GroupChat groupChat) {
        return ResponseEntity.ok(groupChatService.createGroupChat(groupChat));
    }

    @GetMapping("/search")
    public List<GroupChat> searchByName(@RequestParam String name) {
        return groupChatService.searchByName(name);
    }

}
