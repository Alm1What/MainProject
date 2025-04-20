package org.example.mainpriject.controller;


import jakarta.validation.Valid;
import org.example.mainpriject.dto.ArticleDto;
import org.example.mainpriject.dto.ArticleRequest;
import org.example.mainpriject.exception.ResourceNotFoundException;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.UserRepository;
import org.example.mainpriject.service.ArticleService;
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
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final UserUtilityService userUtilityService;

    @Autowired
    public ArticleController(ArticleService articleService, UserUtilityService userUtilityService) {
        this.articleService = articleService;
        this.userUtilityService = userUtilityService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ArticleDto>> getArticlesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(articleService.getArticlesByCategory(categoryId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleDto> createArticle(
            @Valid @RequestBody ArticleRequest articleRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userUtilityService.getUserIdFromUsername(userDetails.getUsername());
        return new ResponseEntity<>(articleService.createArticle(articleRequest, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleDto> updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest articleRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userUtilityService.getUserIdFromUsername(userDetails.getUsername());
        return ResponseEntity.ok(articleService.updateArticle(id, articleRequest, userId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteArticle(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userUtilityService.getUserIdFromUsername(userDetails.getUsername());
        articleService.deleteArticle(id, userId);
        return ResponseEntity.noContent().build();
    }
}