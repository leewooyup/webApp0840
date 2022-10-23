package cz.yeo.wage.WebApp0840.app.wage;

import cz.yeo.wage.WebApp0840.app.user.UserRepository;
import cz.yeo.wage.WebApp0840.app.user.UserService;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wage")
public class WageController {
    private final UserRepository userRepository;
    private final WorkService workService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/base")
    public String showWageBase(Principal principal, Model model, WageBaseForm wageBaseForm) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        String createDateFormat = siteUser.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("createDateFormat", createDateFormat);
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
                siteUser.setWorkPlaceName(wageBaseForm.getWorkPlaceName());
                siteUser.setBaseWage(wageBaseForm.getBaseWage());
                siteUser.setAnnual(wageBaseForm.getAnnual());
                siteUser.setPayday(wageBaseForm.getPayday());
                siteUser.setWorkStartDate(formatDate);
                siteUser.setRegistered(true);
                userRepository.save(siteUser);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return "redirect:/wage/working-time";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/working-time")
    public String showWorkingTime(HttpServletResponse response, Principal principal, Model model, WorkingTimeForm workingTimeForm) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        String createDateFormat = siteUser.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        if(!siteUser.isRegistered()) {
            response.setContentType("text/html; charset=euc-kr");
            try {
                PrintWriter out = response.getWriter();
                out.println("<script>alert('기본 급여정보를 입력해주세요'); location.href='/wage/base';</script>");
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<Work> works = workService.findBySiteUser(siteUser);
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("works", works);
        model.addAttribute("createDateFormat", createDateFormat);
        return "wage/working_time_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/working-time")
    public String workingTime(Principal principal, @Valid WorkingTimeForm workingTimeForm, BindingResult bindingResult) {
        Optional<SiteUser> ou = userRepository.findByUsername(principal.getName());
        if (ou.isPresent()) {
            SiteUser siteUser = ou.get();
            Work work = workService.create(siteUser, workingTimeForm.getWorkingDate(), workingTimeForm.getWorkingHours(),
                    workingTimeForm.getWorkingMinutes(), workingTimeForm.getExtendedHours(), workingTimeForm.getExtendedMinutes(),
                    workingTimeForm.getNightHours(), workingTimeForm.getNightMinutes(), workingTimeForm.getWorkType());
        }
        return "redirect:/wage/working-time";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/result")
    public String showResult(Principal principal, Model model) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        String createDateFormat = siteUser.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        List<Work> works = workService.findBySiteUser(siteUser);
        HashMap<String, Integer> accWorksMap = workService.getAccWorks(siteUser);

        int workCounts = workService.count(siteUser);
        int holidayCounts = workService.getHolidayCount(siteUser);
        int accHours = accWorksMap.get("accHours");
        int accMinutes = accWorksMap.get("accMinutes");
        int accRegularHours = accWorksMap.get("accRegularHours");
        int accRegularMinutes = accWorksMap.get("accRegularMinutes");
        int accExtendedHours = accWorksMap.get("accExtendedHours");
        int accExtendedMinutes = accWorksMap.get("accExtendedMinutes");
        int accNightHours = accWorksMap.get("accNightHours");
        int accNightMinutes = accWorksMap.get("accNightMinutes");
        int accHolidayHours = accWorksMap.get("accHolidayHours");
        int accHolidayMinutes = accWorksMap.get("accHolidayMinutes");
        double accTotalWage = workService.getAccTotalWage(siteUser);

        model.addAttribute("accTotalWage", workService.formatFloorTenth(accTotalWage));
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("works", works);
        model.addAttribute("createDateFormat", createDateFormat);
        model.addAttribute("workCounts", workCounts);
        model.addAttribute("accHours", accHours);
        model.addAttribute("accMinutes", accMinutes);
        model.addAttribute("accRegularHours", accRegularHours);
        model.addAttribute("accRegularMinutes", accRegularMinutes);
        model.addAttribute("accExtendedHours", accExtendedHours);
        model.addAttribute("accExtendedMinutes", accExtendedMinutes);
        model.addAttribute("accNightHours", accNightHours);
        model.addAttribute("accNightMinutes", accNightMinutes);
        model.addAttribute("accHolidayHours", accHolidayHours);
        model.addAttribute("accHolidayMinutes", accHolidayMinutes);

        model.addAttribute("holidayCounts", holidayCounts);

        model.addAttribute("accRegularHoursWage", workService.formatIntFloorTenth(workService.getRegularHoursWage(siteUser)));
        model.addAttribute("minutesToHours", workService.formatSecondDecimalPoint(workService.getRegularMinutesWage(siteUser).get(0)));
        model.addAttribute("accRegularMinutesWage", workService.formatFloorTenth(workService.getRegularMinutesWage(siteUser).get(1)));
        model.addAttribute("accRegularWage", workService.formatFloorTenth(workService.getAccRegularWage(siteUser)));

        model.addAttribute("accExtendedHoursWage", workService.formatFloorTenth(workService.getExtendedHoursWage(siteUser)));
        model.addAttribute("minutesToHours_E", workService.formatSecondDecimalPoint(workService.getExtendedMinutesWage(siteUser).get(0)));
        model.addAttribute("accExtendedMinutesWage", workService.formatFloorTenth(workService.getExtendedMinutesWage(siteUser).get(1)));
        model.addAttribute("accExtendedWage", workService.formatFloorTenth(workService.getAccExtendedWage(siteUser)));

        model.addAttribute("accNightHoursWage", workService.formatFloorTenth(workService.getNightHoursWage(siteUser)));
        model.addAttribute("minutesToHours_N", workService.formatSecondDecimalPoint(workService.getNightMinutesWage(siteUser).get(0)));
        model.addAttribute("accNightMinutesWage", workService.formatFloorTenth(workService.getNightMinutesWage(siteUser).get(1)));
        model.addAttribute("accNightWage", workService.formatFloorTenth(workService.getAccNightWage(siteUser)));

        model.addAttribute("accHolidayHoursWage", workService.formatFloorTenth(workService.getHolidayHoursWage(siteUser)));
        model.addAttribute("accHolidayMinutesWage", workService.formatFloorTenth(workService.getHolidayMinutesWage(siteUser).get(1)));
        model.addAttribute("accHolidayWage", workService.formatFloorTenth(workService.getAccHolidayWage(siteUser)));

        model.addAttribute("accMinutes", accMinutes);
        return "wage/wage_result";
    }
}
