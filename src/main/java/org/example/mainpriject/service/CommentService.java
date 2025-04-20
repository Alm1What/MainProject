package org.example.mainpriject.service;


import jakarta.transaction.Transactional;
import org.example.mainpriject.dto.CommentDto;
import org.example.mainpriject.dto.CommentRequest;
import org.example.mainpriject.exception.ResourceNotFoundException;
import org.example.mainpriject.model.Article;
import org.example.mainpriject.model.Comment;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.ArticleRepository;
import org.example.mainpriject.repository.CommentRepository;
import org.example.mainpriject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;


    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Transactional()
    public List<CommentDto> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto createComment(CommentRequest commentRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Article article = articleRepository.findById(commentRequest.getArticleId())
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + commentRequest.getArticleId()));

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setAuthor(user);
        comment.setArticle(article);

        Comment savedComment = commentRepository.save(comment);
        return convertToDto(savedComment);
    }

    @Transactional
    public CommentDto updateComment(Long id, CommentRequest commentRequest, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        // Check if user is the author or admin
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!comment.getAuthor().getId().equals(userId) && !user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("You don't have permission to update this comment");
        }

        comment.setContent(commentRequest.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return convertToDto(updatedComment);
    }

    @Transactional
    public void deleteComment(Long id, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!comment.getAuthor().getId().equals(userId) && !user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("You don't have permission to delete this comment");
        }

        commentRepository.delete(comment);
    }

    private CommentDto convertToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setAuthorId(comment.getAuthor().getId());
        dto.setAuthorUsername(comment.getAuthor().getName());
        dto.setArticleId(comment.getArticle().getId());
        dto.setArticleTitle(comment.getArticle().getTitle());
        return dto;
    }
}