package cz.yeo.wage.WebApp0840.app.home.controller;

import cz.yeo.wage.WebApp0840.app.user.dto.UserContext;
import cz.yeo.wage.WebApp0840.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class HomeController {

    // # 루트로 접근 (return: home/main)
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/")
    public String main(Model model) {
        model.addAttribute("now", Util.date.getCurrentDateFormatted("yyyy.MM.dd"));
        return "home/main";
    }

    @GetMapping("/currentUserOrigin")
    @ResponseBody
    public Principal currentUserOrigin(Principal principal) {
        return principal;
    }
}