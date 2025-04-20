package org.example.mainpriject.repository;

import org.example.mainpriject.model.Article;
import org.example.mainpriject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByCategory(Category category);
    List<Article> findByAuthorId(Long authorId);
}
