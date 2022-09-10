package cz.yeo.wage.WebApp0840.app.user.controller;

import cz.yeo.wage.WebApp0840.app.user.form.UserCreateForm;
import cz.yeo.wage.WebApp0840.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/login")
    public String user_login() {
        return "user/login";
    }

    @GetMapping("/signup")
    public String user_signup_form(UserCreateForm userCreateForm) {
        return "user/signup";
    }
    @PostMapping("/signup")
    public String user_signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user/signup";
        }

        if(!userCreateForm.getLoginPw().equals(userCreateForm.getLoginPwCheck())) {
            bindingResult.rejectValue("loginPw", "passwordIncorrect",
                    "패스워드가 일치하지 않습니다.");
            return "user/signup";
        }
        userService.create(userCreateForm.getLoginId(), userCreateForm.getLoginPw(),
                            userCreateForm.getNickname(), userCreateForm.getEmail(), userCreateForm.getUserImg());
        return "redirect:/user/login";
    }
}
