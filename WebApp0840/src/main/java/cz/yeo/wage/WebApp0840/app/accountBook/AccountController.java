package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPattern;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedIncome;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedSpending;
import cz.yeo.wage.WebApp0840.app.accountBook.form.DailyPatternForm;
import cz.yeo.wage.WebApp0840.app.accountBook.form.FixedIncomeForm;
import cz.yeo.wage.WebApp0840.app.accountBook.form.FixedSpendingForm;
import cz.yeo.wage.WebApp0840.app.user.UserService;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.work.WorkService;
import cz.yeo.wage.WebApp0840.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;
    private final WorkService workService;
    private final FixedInfoService fixedInfoService;
    private final DailyPatternService dailyPatternService;
    private final DailyPatternItemService dailyPatternItemService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/base/fixed-spending")
    public String showBaseForm(Principal principal, Model model, FixedSpendingForm fixedSpendingForm) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        String dateInfo = Util.date.getCurrentDateFormatted("yyyy MM dd");
        String[] dateInfoBits = dateInfo.split(" ");
        List<FixedSpending> fixedSpendings = fixedInfoService.findFixedSpendingAll(siteUser);
        List<FixedIncome> fixedIncomes = fixedInfoService.findFixedIncomeAll(siteUser);

        int dDayTo = accountService.getDdayCalTo(siteUser);
        int dDayFrom = accountService.getDdayCalFrom(siteUser);
        int nextMonthLastDate = accountService.getLastDateByMonth(Integer.parseInt(dateInfoBits[1])+1);

        double accTotalWage = workService.getAccTotalWage(siteUser);


        model.addAttribute("accTotalWage", workService.formatFloorTenth(accTotalWage));
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("month", dateInfoBits[1]);
        model.addAttribute("nextMonth", Integer.parseInt(dateInfoBits[1])+1);
        model.addAttribute("nextMonthLastDate", nextMonthLastDate);
        model.addAttribute("date", dateInfoBits[2]);
        model.addAttribute("dDayTo", dDayTo);
        model.addAttribute("dDayFrom", dDayFrom);
        model.addAttribute("fixedSpendings", fixedSpendings);
        model.addAttribute("fixedIncomes", fixedIncomes);
        return "accountBook/baseForm/account_fixed_spending_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/base/fixed-spending")
    public String setFixedSpending(Principal principal, @Valid FixedSpendingForm fixedSpendingForm, BindingResult bindingResult) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        fixedInfoService.createFixedSpending(siteUser, fixedSpendingForm.getFixedSpendingType(), fixedSpendingForm.getFixedSpending(), fixedSpendingForm.getFixedSpendingMemo());
        return "redirect:/account/base/fixed-spending";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/base/fixed-income")
    public String showFixedIncomeForm(Principal principal, Model model, FixedIncomeForm fixedIncomeForm) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        String dateInfo = Util.date.getCurrentDateFormatted("yyyy MM dd");
        String[] dateInfoBits = dateInfo.split(" ");
        List<FixedSpending> fixedSpendings = fixedInfoService.findFixedSpendingAll(siteUser);
        List<FixedIncome> fixedIncomes = fixedInfoService.findFixedIncomeAll(siteUser);

        int dDayTo = accountService.getDdayCalTo(siteUser);
        int dDayFrom = accountService.getDdayCalFrom(siteUser);
        int nextMonthLastDate = accountService.getLastDateByMonth(Integer.parseInt(dateInfoBits[1])+1);

        double accTotalWage = workService.getAccTotalWage(siteUser);
        model.addAttribute("accTotalWage", workService.formatFloorTenth(accTotalWage));
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("month", dateInfoBits[1]);
        model.addAttribute("nextMonth", Integer.parseInt(dateInfoBits[1])+1);
        model.addAttribute("nextMonthLastDate", nextMonthLastDate);
        model.addAttribute("date", dateInfoBits[2]);
        model.addAttribute("dDayTo", dDayTo);
        model.addAttribute("dDayFrom", dDayFrom);
        model.addAttribute("fixedSpendings", fixedSpendings);
        model.addAttribute("fixedIncomes", fixedIncomes);
        return "accountBook/baseForm/account_fixed_income_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/base/fixed-income")
    public String setFixedIncome(Principal principal, @Valid FixedIncomeForm fixedSpendingForm, BindingResult bindingResult) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        fixedInfoService.createFixedIncome(siteUser, fixedSpendingForm.getFixedIncomeType(), fixedSpendingForm.getFixedIncome(), fixedSpendingForm.getFixedIncomeMemo());
        return "redirect:/account/base/fixed-income";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/daily/pattern")
    public String showDailyPatternForm(Principal principal, Model model, DailyPatternForm dailyPatternForm) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        String dateInfo = Util.date.getCurrentDateFormatted("yyyy MM dd");
        String[] dateInfoBits = dateInfo.split(" ");
        List<FixedSpending> fixedSpendings = fixedInfoService.findFixedSpendingAll(siteUser);
        List<FixedIncome> fixedIncomes = fixedInfoService.findFixedIncomeAll(siteUser);

        int dDayTo = accountService.getDdayCalTo(siteUser);
        int dDayFrom = accountService.getDdayCalFrom(siteUser);
        int nextMonthLastDate = accountService.getLastDateByMonth(Integer.parseInt(dateInfoBits[1])+1);
        int accTotalWage = (int)workService.getAccTotalWage(siteUser);
        int fixedSpendingSum = fixedInfoService.getFixedSpendingSum(siteUser);
        int fixedIncomeSum = fixedInfoService.getFixedIncomeSum(siteUser);
        int balance = fixedInfoService.getBalance(siteUser);

        model.addAttribute("accTotalWage", workService.formatIntFloorTenth(accTotalWage));
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("month", dateInfoBits[1]);
        model.addAttribute("nextMonth", Integer.parseInt(dateInfoBits[1])+1);
        model.addAttribute("nextMonthLastDate", nextMonthLastDate);
        model.addAttribute("date", dateInfoBits[2]);
        model.addAttribute("dDayTo", dDayTo);
        model.addAttribute("dDayFrom", dDayFrom);
        model.addAttribute("fixedSpendings", fixedSpendings);
        model.addAttribute("fixedIncomes", fixedIncomes);
        model.addAttribute("fixedSpendingSum", fixedSpendingSum);
        model.addAttribute("fixedIncomeSum", fixedIncomeSum);
        model.addAttribute("balance", workService.formatIntFloorTenth(balance));
        return "accountBook/baseForm/daily_consumption_pattern";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/daily/pattern")
    @ResponseBody
    public String dailyPattern(Principal principal, Model model, @Valid DailyPatternForm dailyPatternForm, BindingResult bindingResult) {
        DailyPattern dailyPattern = dailyPatternService.create(dailyPatternForm.getDailyPatternName());
        Stream.of(dailyPatternForm.getDailyConsumptionTypes())
                .forEach(type -> {
                    dailyPatternItemService.createType(dailyPattern, String.valueOf(type));
                });
//        Stream.of(dailyPatternForm.getDailyConsumptionTypes())
//                .forEach(type -> {
//                    dailyPatternItemService.create(dailyPattern, String.valueOf(type));
//                });
        Stream.of(dailyPatternForm.getDailyConsumptionPrices())
                .forEach(price -> {
                    dailyPatternItemService.createPrice(dailyPattern, String.valueOf(price));
                });
        return "작업중";
    }
}
