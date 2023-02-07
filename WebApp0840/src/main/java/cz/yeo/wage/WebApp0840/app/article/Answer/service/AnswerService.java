package cz.yeo.wage.WebApp0840.app.article.Answer.service;

import cz.yeo.wage.WebApp0840.app.article.Answer.entity.Answer;
import cz.yeo.wage.WebApp0840.app.article.Answer.repository.AnswerRepository;
import cz.yeo.wage.WebApp0840.app.article.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Article article, String content) {
        Answer answer = Answer.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .article(article)
                .build();
        answerRepository.save(answer);
    }
}
