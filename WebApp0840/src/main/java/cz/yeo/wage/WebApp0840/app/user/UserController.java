package cz.yeo.wage.WebApp0840.app.user;

import cz.yeo.wage.WebApp0840.app.user.form.UserCreateForm;
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

    @GetMapping("/join")
    public String showJoin(UserCreateForm userCreateForm) {
        return "user/join";
    }
    @PostMapping("/join")
    public String join(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user/join";
        }

        if(!userCreateForm.getPassword().equals(userCreateForm.getPasswordCheck())) {
            bindingResult.rejectValue("loginPw", "passwordIncorrect",
                    "패스워드가 일치하지 않습니다.");
            return "user/join";
        }
        userService.join(userCreateForm.getUsername(), userCreateForm.getPassword(),
                            userCreateForm.getNickname(), userCreateForm.getEmail());
        return "redirect:/user/login";
    }
}
