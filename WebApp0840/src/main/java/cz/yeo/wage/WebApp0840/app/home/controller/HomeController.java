package cz.yeo.wage.WebApp0840.app.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
public class HomeController {

    // # 루트로 접근 (return: home/main)
    @RequestMapping("/")
    public String main() {
        return "home/main";
    }
}