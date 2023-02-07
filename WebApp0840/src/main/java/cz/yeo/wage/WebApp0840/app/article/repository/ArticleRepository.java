package cz.yeo.wage.WebApp0840.app.article.repository;

import cz.yeo.wage.WebApp0840.app.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
