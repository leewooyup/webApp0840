package cz.yeo.wage.WebApp0840.app.article.Answer.controller;

import cz.yeo.wage.WebApp0840.app.article.Answer.service.AnswerService;
import cz.yeo.wage.WebApp0840.app.article.entity.Article;
import cz.yeo.wage.WebApp0840.app.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final ArticleService articleService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
        Article article = articleService.getArticle(id);
        answerService.create(article, content);
        return String.format("redirect:/article/detail/%s", id);
    }
}
