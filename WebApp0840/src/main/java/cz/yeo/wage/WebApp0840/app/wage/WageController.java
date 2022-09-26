package cz.yeo.wage.WebApp0840.app.wage;

import cz.yeo.wage.WebApp0840.app.user.UserRepository;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.wage.form.WageBaseForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wage")
public class WageController {
    private final UserRepository userRepository;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/base")
    public String showWageBase(WageBaseForm wageBaseForm) {
        return "wage/wage_base_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/base")
    public String wageBase(Principal principal, Model model, @Valid WageBaseForm wageBaseForm, BindingResult bindingResult) {
        Optional<SiteUser> ou = userRepository.findByUsername(principal.getName());
        if(ou.isPresent()) {
            SiteUser siteUser = ou.get();
            siteUser.setBaseWage(wageBaseForm.getBaseWage());
            siteUser.setAnnual(wageBaseForm.getAnnual());
            siteUser.setPayday(wageBaseForm.getPayday());
            userRepository.save(siteUser);
        }
        return "wage/working_hours_form";
    }
}
