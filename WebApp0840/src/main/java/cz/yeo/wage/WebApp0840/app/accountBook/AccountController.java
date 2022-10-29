package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.*;
import cz.yeo.wage.WebApp0840.app.accountBook.form.DailyPatternForm;
import cz.yeo.wage.WebApp0840.app.accountBook.form.FixedIncomeForm;
import cz.yeo.wage.WebApp0840.app.accountBook.form.FixedSpendingForm;
import cz.yeo.wage.WebApp0840.app.user.UserService;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.work.WorkService;
import cz.yeo.wage.WebApp0840.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.HashMap;
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
    public String showDailyPatternForm(HttpServletResponse response, Principal principal, Model model, DailyPatternForm dailyPatternForm) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        if(!siteUser.isRegisteredFixedIncome()) {
            response.setContentType("text/html; charset=euc-kr");
            try {
                PrintWriter out = response.getWriter();
                out.println("<script>alert('고정 지출/소득을 입력해주세요'); location.href='/account/base/fixed-income';</script>");
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(!siteUser.isRegisteredFixedSpending()) {
            response.setContentType("text/html; charset=euc-kr");
            try {
                PrintWriter out = response.getWriter();
                out.println("<script>alert('고정 지출/소득을 입력해주세요'); location.href='/account/base/fixed-spending';</script>");
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
        int budget = fixedInfoService.getBudget(siteUser);

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
        model.addAttribute("budget", workService.formatIntFloorTenth(budget));
        return "accountBook/baseForm/daily_consumption_pattern_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/daily/pattern")
    public String dailyPattern(HttpServletResponse response, Principal principal, Model model, @Valid DailyPatternForm dailyPatternForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "accountBook/baseForm/daily_consumption_pattern_form";
        }
        SiteUser siteUser = userService.findByUsername(principal.getName());
        try {
            DailyPattern dailyPattern = dailyPatternService.create(siteUser, dailyPatternForm.getDailyPatternName(), dailyPatternForm.getTimesPerMonth());
            Stream.of(dailyPatternForm.getDailyConsumptionTypes())
                    .forEach(type -> {
                        dailyPatternItemService.createType(dailyPattern, String.valueOf(type));
                    });
            Stream.of(dailyPatternForm.getDailyConsumptionPrices())
                    .forEach(price -> {
                        dailyPatternItemService.createPrice(dailyPattern, String.valueOf(price));
                    });
        } catch(DataIntegrityViolationException e) {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter w = null;
            try {
                w = response.getWriter();
            } catch (IOException ex) {
                e.printStackTrace();
            }
            w.write("<script>alert('이미 존재하는 [소비패턴명]입니다.');history.go(-1);</script>");
            w.flush();
            w.close();

        } catch(NumberFormatException e) {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter w = null;
            try {
                w = response.getWriter();
            } catch (IOException ex) {
                e.printStackTrace();
            }
            w.write("<script>alert('모든 항목을 입력해주세요.');history.go(-1);</script>");
            w.flush();
            w.close();
        }
        return "redirect:/account/result";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/result")
    public String showResult(HttpServletResponse response, Principal principal, Model model, @Valid DailyPatternForm dailyPatternForm, BindingResult bindingResult) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        if(!siteUser.isRegisteredFixedIncome()) {
            response.setContentType("text/html; charset=euc-kr");
            try {
                PrintWriter out = response.getWriter();
                out.println("<script>alert('고정 지출/소득을 입력해주세요'); location.href='/account/base/fixed-income';</script>");
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(!siteUser.isRegisteredFixedSpending()) {
            response.setContentType("text/html; charset=euc-kr");
            try {
                PrintWriter out = response.getWriter();
                out.println("<script>alert('고정 지출/소득을 입력해주세요'); location.href='/account/base/fixed-spending';</script>");
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<FixedSpending> fixedSpendings = fixedInfoService.findFixedSpendingAll(siteUser);
        List<FixedIncome> fixedIncomes = fixedInfoService.findFixedIncomeAll(siteUser);
        String dateInfo = Util.date.getCurrentDateFormatted("yyyy MM dd");
        String[] dateInfoBits = dateInfo.split(" ");

        List<DailyPattern> dailyPatterns = dailyPatternService.findBySiteUser(siteUser);

        HashMap<String, List<DailyPatternItemType>> typesMap = accountService.getTypesMap(dailyPatterns);
        HashMap<String, List<DailyPatternItemPrice>> pricesMap = accountService.getPricesMap(dailyPatterns);
        HashMap<String, Integer> totalPriceMap = accountService.getTotalPriceMap(dailyPatterns);
        HashMap<String, Integer> sumPriceMap = accountService.getSumPriceMap(dailyPatterns);
        int monthTotalPrice = accountService.getMonthTotalPrice(dailyPatterns);

        int budget = fixedInfoService.getBudget(siteUser);
        int balance = accountService.getBalance(siteUser);

        model.addAttribute("siteUser", siteUser);
        model.addAttribute("fixedSpendings", fixedSpendings);
        model.addAttribute("fixedIncomes", fixedIncomes);
        model.addAttribute("nextMonth", Integer.parseInt(dateInfoBits[1])+1);
        model.addAttribute("dailyPatterns", dailyPatterns);
        model.addAttribute("typesMap", typesMap);
        model.addAttribute("pricesMap", pricesMap);
        model.addAttribute("totalPriceMap", totalPriceMap);
        model.addAttribute("sumPriceMap", sumPriceMap);
        model.addAttribute("monthTotalPrice", monthTotalPrice);
        model.addAttribute("budget", workService.formatIntFloorTenth(budget));
        model.addAttribute("balance", balance);
        return "accountBook/daily_pattern_result";
    }
}
