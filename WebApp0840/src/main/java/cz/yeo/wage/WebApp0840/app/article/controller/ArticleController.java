package cz.yeo.wage.WebApp0840.app.article.controller;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedIncome;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedSpending;
import cz.yeo.wage.WebApp0840.app.article.entity.Article;
import cz.yeo.wage.WebApp0840.app.article.service.ArticleService;
import cz.yeo.wage.WebApp0840.app.user.UserService;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;


    @GetMapping("/list")
    public String list(Principal principal, Model model) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        List<Article> articles = articleService.findAll();
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("articles", articles);
        return "article/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Principal principal, Model model, @PathVariable("id") Integer id) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        Article article = articleService.getArticle(id);
        String createDateFormat = article.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("article", article);
        model.addAttribute("createDateFormat", createDateFormat);
        return "article/detail";
    }
}
