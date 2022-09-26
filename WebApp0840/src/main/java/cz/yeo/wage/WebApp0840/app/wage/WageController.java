package cz.yeo.wage.WebApp0840.app.wage;

import cz.yeo.wage.WebApp0840.app.user.UserRepository;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.work.WorkService;
import cz.yeo.wage.WebApp0840.app.user.work.entity.Work;
import cz.yeo.wage.WebApp0840.app.wage.form.WageBaseForm;
import cz.yeo.wage.WebApp0840.app.wage.form.WorkingTimeForm;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wage")
public class WageController {
    private final UserRepository userRepository;
    private final WorkService workService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/base")
    public String showWageBase(WageBaseForm wageBaseForm) {
        return "wage/wage_base_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/base")
    public String wageBase(Principal principal, Model model, @Valid WageBaseForm wageBaseForm, BindingResult bindingResult) {
        Optional<SiteUser> ou = userRepository.findByUsername(principal.getName());
        if (ou.isPresent()) {
            try {
                Date formatDate = new SimpleDateFormat("yyyy-MM-dd").parse(wageBaseForm.getWorkStartDate());
                SiteUser siteUser = ou.get();
                siteUser.setBaseWage(wageBaseForm.getBaseWage());
                siteUser.setAnnual(wageBaseForm.getAnnual());
                siteUser.setPayday(wageBaseForm.getPayday());
                siteUser.setWorkStartDate(formatDate);
                userRepository.save(siteUser);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return "redirect:/wage/working-time";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/working-time")
    public String showWorkingTime(WorkingTimeForm workingTimeForm) {
        return "wage/working_time_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/working-time")
    public String workingTime(Principal principal, @Valid WorkingTimeForm workingTimeForm, BindingResult bindingResult) {
        Optional<SiteUser> ou = userRepository.findByUsername(principal.getName());
        if (ou.isPresent()) {
            SiteUser siteUser = ou.get();
            Work work = workService.create(siteUser, workingTimeForm.getWorkingDate(), workingTimeForm.getWorkingHours(),
                    workingTimeForm.getWorkingMinutes(), workingTimeForm.getExtendedHours(), workingTimeForm.getExtendedMinutes());
        }
        return "wage/wage_result";
    }
}
