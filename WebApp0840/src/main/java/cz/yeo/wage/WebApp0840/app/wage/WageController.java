package cz.yeo.wage.WebApp0840.app.wage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wage")
public class WageController {
    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public String wage_form() {
        return "wage/wage_form";
    }
}
