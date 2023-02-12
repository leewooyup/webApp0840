package cz.yeo.wage.WebApp0840.app.article.repository;

import cz.yeo.wage.WebApp0840.app.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Page<Article> findAll(Pageable pageable);

    List<Article> findFirst4ByOrderByIdDesc();
}
