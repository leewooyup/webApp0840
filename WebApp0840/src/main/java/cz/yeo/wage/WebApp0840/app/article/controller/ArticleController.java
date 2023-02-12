package cz.yeo.wage.WebApp0840.app.article.controller;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedIncome;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedSpending;
import cz.yeo.wage.WebApp0840.app.article.Answer.entity.Answer;
import cz.yeo.wage.WebApp0840.app.article.entity.Article;
import cz.yeo.wage.WebApp0840.app.article.form.ArticleForm;
import cz.yeo.wage.WebApp0840.app.article.service.ArticleService;
import cz.yeo.wage.WebApp0840.app.user.UserService;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    public String list(Principal principal, Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        List<Article> articles = articleService.findAll();
        Page<Article> paging = articleService.getList(page);
        model.addAttribute("paging", paging);
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("articles", articles);
        return "article/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Principal principal, Model model, @PathVariable("id") Integer id) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        Article article = articleService.getArticle(id);
        String createDateFormat = article.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String ModifyDateFormat = article.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("article", article);
        model.addAttribute("createDateFormat", createDateFormat);
        model.addAttribute("ModifyDateFormat", ModifyDateFormat);
        return "article/detail";
    }

    @GetMapping("/create")
    public String articleCreateForm(ArticleForm articleForm, Principal principal, Model model) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        model.addAttribute("siteUser", siteUser);
        return "article/create";
    }

    @PostMapping("/create")
    public String articleCreate(@Valid ArticleForm articleForm, @RequestParam MultipartFile proposedImg, BindingResult bindingResult, Principal principal, Model model,
                                @RequestParam String subject, @RequestParam String subSubject, @RequestParam String content) {
        if(bindingResult.hasErrors()) {
            SiteUser siteUser = userService.findByUsername(principal.getName());
            model.addAttribute("siteUser", siteUser);
            return "article/create";
        }
        SiteUser siteUser = userService.findByUsername(principal.getName());
        articleService.create(siteUser, articleForm.getSubject(), articleForm.getSubSubject(), articleForm.getContent(), proposedImg);
        model.addAttribute("siteUser", siteUser);
        return "redirect:/article/list";
    }

    @GetMapping("/img/{id}")
    public String showProposedImg(@PathVariable("id") Integer id) {
        return "redirect:/gen/" + articleService.getArticle(id).getProposedImg();
    }

    @GetMapping("/modify/{id}")
    public String articleModify(ArticleForm articleForm, @PathVariable("id") Integer id, Principal principal, Model model) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        Article article = articleService.getArticle(id);
        if(!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        articleForm.setSubject(article.getSubject());
        articleForm.setSubSubject(article.getSubSubject());
        articleForm.setContent(article.getContent());
        model.addAttribute("siteUser", siteUser);
        return "article/create";
    }

    @PostMapping("/modify/{id}")
    public String articleModify(@Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal, Model model, @PathVariable("id") Integer id) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        if(bindingResult.hasErrors()) {
            model.addAttribute("siteUser", siteUser);
            return "article/create";
        }
        Article article = articleService.getArticle(id);
        if(!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        articleService.modify(article, articleForm.getSubject(), articleForm.getSubSubject(), articleForm.getContent());
        return String.format("redirect:/article/detail/%s", id);

    }

    @GetMapping("/delete/{id}")
    public String articleDelete(@PathVariable("id") Integer id, Principal principal, Model model) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        Article article = articleService.getArticle(id);
        if(!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        articleService.delete(article);
        model.addAttribute("siteUser", siteUser);
        return "redirect:/article/list";
    }
}
