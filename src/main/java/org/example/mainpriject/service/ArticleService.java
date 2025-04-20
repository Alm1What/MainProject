package org.example.mainpriject.service;

import jakarta.transaction.Transactional;
import org.example.mainpriject.dto.ArticleDto;
import org.example.mainpriject.dto.ArticleRequest;
import org.example.mainpriject.exception.ResourceNotFoundException;
import org.example.mainpriject.model.Article;
import org.example.mainpriject.model.Category;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.ArticleRepository;
import org.example.mainpriject.repository.CategoryRepository;
import org.example.mainpriject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional()
    public List<ArticleDto> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional()
    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));
        return convertToDto(article);
    }

    @Transactional()
    public List<ArticleDto> getArticlesByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        return articleRepository.findByCategory(category).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ArticleDto createArticle(ArticleRequest articleRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Category category = categoryRepository.findById(articleRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + articleRequest.getCategoryId()));

        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setImageUrl(articleRequest.getImageUrl());
        article.setMainText(articleRequest.getMainText());
        article.setAuthor(user);
        article.setCategory(category);

        Article savedArticle = articleRepository.save(article);
        return convertToDto(savedArticle);
    }

    @Transactional
    public ArticleDto updateArticle(Long id, ArticleRequest articleRequest, Long userId) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!article.getAuthor().getId().equals(userId) && !user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("You don't have permission to update this article");
        }

        Category category = categoryRepository.findById(articleRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + articleRequest.getCategoryId()));

        article.setTitle(articleRequest.getTitle());
        article.setImageUrl(articleRequest.getImageUrl());
        article.setMainText(articleRequest.getMainText());
        article.setCategory(category);

        Article updatedArticle = articleRepository.save(article);
        return convertToDto(updatedArticle);
    }

    @Transactional
    public void deleteArticle(Long id, Long userId) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!article.getAuthor().getId().equals(userId) && !user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("You don't have permission to delete this article");
        }

        articleRepository.delete(article);
    }

    private ArticleDto convertToDto(Article article) {
        ArticleDto dto = new ArticleDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setImageUrl(article.getImageUrl());
        dto.setMainText(article.getMainText());
        dto.setCreatedAt(article.getCreatedAt());
        dto.setAuthorId(article.getAuthor().getId());
        dto.setAuthorUsername(article.getAuthor().getName());
        dto.setCategoryId(article.getCategory().getId());
        dto.setCategoryName(article.getCategory().getName());
        dto.setCommentCount(article.getComments().size());
        return dto;
    }
}
