package org.example.mainpriject.controller;


import jakarta.validation.Valid;
import org.example.mainpriject.dto.CommentDto;
import org.example.mainpriject.dto.CommentRequest;
import org.example.mainpriject.service.CommentService;
import org.example.mainpriject.service.UserUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserUtilityService userUtilityService;

    @Autowired
    public CommentController(CommentService commentService, UserUtilityService userUtilityService) {
        this.commentService = commentService;
        this.userUtilityService = userUtilityService;
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentDto>> getCommentsByArticleId(@PathVariable Long articleId) {
        return ResponseEntity.ok(commentService.getCommentsByArticleId(articleId));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CommentDto> createComment(
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userUtilityService.getUserIdFromUsername(userDetails.getUsername());
        return new ResponseEntity<>(commentService.createComment(commentRequest, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userUtilityService.getUserIdFromUsername(userDetails.getUsername());
        return ResponseEntity.ok(commentService.updateComment(id, commentRequest, userId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userUtilityService.getUserIdFromUsername(userDetails.getUsername());
        commentService.deleteComment(id, userId);
        return ResponseEntity.noContent().build();
    }
}