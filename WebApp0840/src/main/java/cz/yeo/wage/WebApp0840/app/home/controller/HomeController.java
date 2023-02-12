package cz.yeo.wage.WebApp0840.app.home.controller;

import cz.yeo.wage.WebApp0840.app.accountBook.FixedInfoService;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedIncome;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedSpending;
import cz.yeo.wage.WebApp0840.app.article.entity.Article;
import cz.yeo.wage.WebApp0840.app.article.service.ArticleService;
import cz.yeo.wage.WebApp0840.app.user.UserService;
import cz.yeo.wage.WebApp0840.app.user.dto.UserContext;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.form.UserModifyForm;
import cz.yeo.wage.WebApp0840.app.wage.form.WageBaseForm;
import cz.yeo.wage.WebApp0840.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final FixedInfoService fixedInfoService;
    private final ArticleService articleService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/")
    public String main(Principal principal, Model model, WageBaseForm wageBaseForm) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        List<FixedSpending> fixedSpendings = fixedInfoService.findFixedSpendingBySiteUser(siteUser);
        List<FixedIncome> fixedIncomes =  fixedInfoService.findFixedIncomeBySiteUser(siteUser);
        List<Article> articles = articleService.findAll();
        List<Article> articlesTop4 = articleService.getArticleTop4();
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("fixedSpendings", fixedSpendings);
        model.addAttribute("fixedIncomes", fixedIncomes);
        model.addAttribute("now", Util.date.getCurrentDateFormatted("yyyy.MM.dd"));
        model.addAttribute("articles", articles);
        model.addAttribute("articlesTop4", articlesTop4);
        return "home/main";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/wageInfo")
    public String modifyWageInfo(Principal principal, String workPlaceName, Integer baseWage, Integer annual, String workStartDate, Integer payday) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        userService.modify(siteUser, workPlaceName, baseWage, annual,
                                    workStartDate, payday);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reset/fixedInfo")
    @Transactional
    public String reset(Principal principal) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        userService.clearRegisteredFixedInfo(siteUser);
        fixedInfoService.deleteAll(siteUser);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/userInfo")
    public String modifyUserInfo(Principal principal, String username, String nickname, String email) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        userService.modifyUserInfo(siteUser, nickname, email);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/megazine/unemployment")
    public String showMegazine1(Principal principal, Model model) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        model.addAttribute("siteUser", siteUser);
        return "home/unemployment";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/megazine/annual")
    public String showMegazine2(Principal principal, Model model) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        model.addAttribute("siteUser", siteUser);
        return "home/annual";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/megazine/year-end-tax")
    public String showMegazine3(Principal principal, Model model) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        model.addAttribute("siteUser", siteUser);
        return "home/year_end";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/megazine/weekly-allowance")
    public String showMegazine4(Principal principal, Model model) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        model.addAttribute("siteUser", siteUser);
        return "home/weekly_allowance";
    }

    @GetMapping("/currentUserOrigin")
    @ResponseBody
    public Principal currentUserOrigin(Principal principal) {
        return principal;
    }
}