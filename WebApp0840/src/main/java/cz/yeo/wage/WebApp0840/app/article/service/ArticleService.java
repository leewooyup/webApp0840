package cz.yeo.wage.WebApp0840.app.article.service;

import cz.yeo.wage.WebApp0840.app.article.Answer.entity.Answer;
import cz.yeo.wage.WebApp0840.app.article.entity.Article;
import cz.yeo.wage.WebApp0840.app.article.repository.ArticleRepository;
import cz.yeo.wage.WebApp0840.app.exception.DataNotFoundException;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleService {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    private final ArticleRepository articleRepository;

    public Article create(SiteUser siteUser1, String subject, String subSubject, String content, MultipartFile proposedImg) {
        String proposedImgRelPath = saveProposedImg(proposedImg);
        Article article = Article.builder()
                .subject(subject)
                .subSubject(subSubject)
                .content(content)
                .author(siteUser1)
                .proposedImg(proposedImgRelPath)
                .build();
        articleRepository.save(article);
        return article;
    }

    private String saveProposedImg(MultipartFile proposedImg) {
        if (proposedImg == null || proposedImg.isEmpty()) {
            return null;
        }
        String proposedImgDirName = "article";
        String ext = Util.file.getExt(proposedImg.getOriginalFilename());
        String fileName= UUID.randomUUID() + "." + ext;
        String proposedImgDirPath = genFileDirPath + "/" + proposedImgDirName;
        String proposedImgFilePath = proposedImgDirPath + "/" + fileName;

        new File(proposedImgDirPath).mkdirs();

        try {
            proposedImg.transferTo(new File(proposedImgFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return proposedImgDirName + "/" + fileName;
    }

    // 개발용 샘플데이터
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

    public void delete(Article article) {
        articleRepository.delete(article);
    }
}
