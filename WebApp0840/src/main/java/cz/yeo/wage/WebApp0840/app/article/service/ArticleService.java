package cz.yeo.wage.WebApp0840.app.article.service;

import cz.yeo.wage.WebApp0840.app.article.entity.Article;
import cz.yeo.wage.WebApp0840.app.article.repository.ArticleRepository;
import cz.yeo.wage.WebApp0840.app.exception.DataNotFoundException;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article create(SiteUser siteUser1, String subject, String subSubject, String content) {
        Article article = Article.builder()
                .subject(subject)
                .subSubject(subSubject)
                .content(content)
                .author(siteUser1)
                .build();
        articleRepository.save(article);
        return article;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article getArticle(Integer id) {
        Optional<Article> oa = this.articleRepository.findById(id);
        if(oa.isPresent()) {
            return oa.get();
        } else {
            throw new DataNotFoundException("article not found");
        }
    }

    public void modify(Article article, String subject, String subSubject, String content) {
        article.setSubject(subject);
        article.setSubSubject(subSubject);
        article.setContent(content);
        articleRepository.save(article);
    }
}
