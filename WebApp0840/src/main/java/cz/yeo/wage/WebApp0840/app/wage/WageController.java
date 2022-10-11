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
        String createDateFormat = siteUser.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
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
        String createDateFormat = siteUser.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
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
        String createDateFormat = siteUser.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        List<Work> works = workService.findBySiteUser(siteUser);
        int workCounts = workService.count(siteUser);
        int hollidayCounts = workService.getHolidayCount(siteUser);
        HashMap<String, Integer> accWorksMap = workService.getAccWorks(siteUser);
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
        int sumHours = 0;
        double sumBasePay = 0;
        int sumMinutes = 0;
        double accHourssumHours = 0;
        for (Work work : works) {
            double workingHours = work.getWorkingHours();
            double workingMinutes = work.getWorkingMinutes();
            sumMinutes += workingMinutes;
            sumHours += workingHours;
        }

        accHours = workService.convertHours(accHours, accMinutes);
        accMinutes = workService.convertMinutes(accMinutes);
        accRegularHours = workService.convertHours(accRegularHours, accRegularMinutes);
        accRegularMinutes = workService.convertMinutes(accRegularMinutes);
        accExtendedHours = workService.convertHours(accExtendedHours, accExtendedMinutes);
        accExtendedMinutes = workService.convertMinutes(accExtendedMinutes);
        accNightHours = workService.convertHours(accNightHours, accNightMinutes);
        accNightMinutes = workService.convertMinutes(accNightMinutes);
        accHolidayHours = workService.convertHours(accHolidayHours, accHolidayMinutes);
        accHolidayMinutes = workService.convertMinutes(accHolidayMinutes);



        while (sumMinutes >= 60) {
            System.out.println("60");
            sumHours += 1;
            sumMinutes -= 60;
        }

        while (sumMinutes >= 30) {
            System.out.println("30");
            sumHours += 0.5;
            sumMinutes -= 30;
        }

        while (sumMinutes >= 10) {
            System.out.println("10");
            sumHours += 0.17;
            sumMinutes -= 10;
        }


        switch (sumMinutes) {
            case 9:
                sumHours += 0.15;
                break;
            case 8:
                sumHours += 0.1333;
                break;
            case 7:
                sumHours += 0.1166;
                break;
            case 6:
                sumHours += 0.1;
                break;
            case 5:
                sumMinutes += 0.0833;
                break;
        }


        sumBasePay = sumHours * siteUser.getBaseWage();
        System.out.println("accHours: " + accHours);
        System.out.println("accMinutes: " + accMinutes);
        model.addAttribute("accTotalWage", formatFloorTenth(accTotalWage));
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

        model.addAttribute("hollidayCounts", hollidayCounts);

        model.addAttribute("accRegularHoursWage", formatIntFloorTenth(workService.getRegularHoursWage(siteUser)));
        model.addAttribute("minutesToHours", formatSecondDecimalPoint(workService.getRegularMinutesWage(siteUser).get(0)));
        model.addAttribute("accRegularMinutesWage", formatFloorTenth(workService.getRegularMinutesWage(siteUser).get(1)));
        model.addAttribute("accRegularWage", formatFloorTenth(workService.getAccRegularWage(siteUser)));

        model.addAttribute("accExtendedHoursWage", formatFloorTenth(workService.getExtendedHoursWage(siteUser)));
        model.addAttribute("minutesToHours_E", formatSecondDecimalPoint(workService.getExtendedMinutesWage(siteUser).get(0)));
        model.addAttribute("accExtendedMinutesWage", formatFloorTenth(workService.getExtendedMinutesWage(siteUser).get(1)));
        model.addAttribute("accExtendedWage", formatFloorTenth(workService.getAccExtendedWage(siteUser)));

        model.addAttribute("accNightHoursWage", formatFloorTenth(workService.getNightHoursWage(siteUser)));
        model.addAttribute("minutesToHours_N", formatSecondDecimalPoint(workService.getNightMinutesWage(siteUser).get(0)));
        model.addAttribute("accNightMinutesWage", formatFloorTenth(workService.getNightMinutesWage(siteUser).get(1)));
        model.addAttribute("accNightWage", formatFloorTenth(workService.getAccNightWage(siteUser)));

        model.addAttribute("accHolidayHoursWage", formatFloorTenth(workService.getHolidayHoursWage(siteUser)));
        model.addAttribute("accHolidayMinutesWage", formatFloorTenth(workService.getHolidayMinutesWage(siteUser).get(1)));
        model.addAttribute("accHolidayWage", formatFloorTenth(workService.getAccHolidayWage(siteUser)));

        model.addAttribute("accMinutes", accMinutes);
        model.addAttribute("sumBasePay", formatFloorTenth(sumBasePay));
        return "wage/wage_result";
    }

    public String formatSecondDecimalPoint(Double d) {
        return String.format("%.2f", d);
    }
    public String formatFloorTenth(Double d) {
        return Integer.toString((int)Math.floor(d/10.0) * 10);
    }

    public String formatIntFloorTenth(Integer i) {
        return Integer.toString((int)Math.floor(i/10.0) * 10);
    }
}
