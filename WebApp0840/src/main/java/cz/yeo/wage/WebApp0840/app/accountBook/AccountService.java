package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPattern;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPatternItemPrice;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPatternItemType;
import cz.yeo.wage.WebApp0840.app.user.UserRepository;
import cz.yeo.wage.WebApp0840.app.user.UserService;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserService userService;
    private final FixedInfoService fixedInfoService;
    private final DailyPatternItemService dailyPatternItemService;
    private final DailyPatternService dailyPatternService;

    public int getDdayCalTo(SiteUser siteUser) {
        int payday = siteUser.getPayday(); //5
        String dateInfo = Util.date.getCurrentDateFormatted("yyyy MM dd");
        String[] dateInfoBits = dateInfo.split(" ");
        int currentMonth = Integer.parseInt(dateInfoBits[1]);
        int currentDate = Integer.parseInt(dateInfoBits[2]); //16
        int[] lastDates = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int dDay = 0;
        if(currentDate > payday) {
            dDay = lastDates[currentMonth] - currentDate + payday;
        } else if(currentDate < payday) {
            dDay = payday - currentDate;
        } else {

        }
        return dDay;
    }

    public int getLastDateByMonth(int monthIndex) {
        int[] lastDates = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return lastDates[monthIndex];
    }
    public int getDdayCalFrom(SiteUser siteUser) {
        int payday = siteUser.getPayday(); //5
        boolean isNextMonth = false;
        String dateInfo = Util.date.getCurrentDateFormatted("yyyy MM dd");
        String[] dateInfoBits = dateInfo.split(" ");
        int currentMonth = Integer.parseInt(dateInfoBits[1]);
        int currentDate = Integer.parseInt(dateInfoBits[2]); //16
        int[] lastDates = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int dDay = 0;

        if(currentDate < payday) {
            return lastDates[currentMonth-1] - payday + currentDate;
        }

        dDay = currentDate - payday;
        return dDay;
    }

    public int getBalance(SiteUser siteUser) {
        List<DailyPattern> dailyPatterns = dailyPatternService.findBySiteUser(siteUser);
        int budget = fixedInfoService.getBudget(siteUser);
        int monthTotalPrice = getMonthTotalPrice(dailyPatterns);
        return budget - monthTotalPrice;
    }

    public HashMap<String, List<DailyPatternItemType>> getTypesMap(List<DailyPattern> dailyPatterns) {
        HashMap<String, List<DailyPatternItemType>> typesMap = new HashMap<>();
        for(DailyPattern dailyPattern : dailyPatterns) {
            String dailyPatternName = dailyPattern.getDailyPatternName();
            List<DailyPatternItemType> dailyPatternItemTypes = dailyPatternItemService.findTypesByDailyPattern(dailyPattern);
            typesMap.put(dailyPatternName, dailyPatternItemTypes);
        }
        return typesMap;
    }

    public HashMap<String, List<DailyPatternItemPrice>> getPricesMap(List<DailyPattern> dailyPatterns) {
        HashMap<String, List<DailyPatternItemPrice>> pricesMap = new HashMap<>();
        for(DailyPattern dailyPattern : dailyPatterns) {
            String dailyPatternName = dailyPattern.getDailyPatternName();
            List<DailyPatternItemPrice> dailyPatternItemPrices = dailyPatternItemService.findPricesByDailyPattern(dailyPattern);
            pricesMap.put(dailyPatternName, dailyPatternItemPrices);
        }
        return pricesMap;
    }

    public HashMap<String, Integer> getTotalPriceMap(List<DailyPattern> dailyPatterns) {
        HashMap<String, Integer> totalPriceMap = new HashMap<>();
        for(DailyPattern dailyPattern : dailyPatterns) {
            String dailyPatternName = dailyPattern.getDailyPatternName();
            int totalPriceBasedOnTimes = dailyPatternItemService.getTotalPriceBasedOnTimes(dailyPattern);
            totalPriceMap.put(dailyPatternName, totalPriceBasedOnTimes);
        }
        return totalPriceMap;
    }

    public HashMap<String, Integer> getSumPriceMap(List<DailyPattern> dailyPatterns) {
        HashMap<String, Integer> sumPriceMap = new HashMap<>();
        for(DailyPattern dailyPattern : dailyPatterns) {
            String dailyPatternName = dailyPattern.getDailyPatternName();
            int sumPrice = dailyPatternItemService.getSumPrice(dailyPattern);
            sumPriceMap.put(dailyPatternName, sumPrice);
        }
        return sumPriceMap;
    }

    public int getMonthTotalPrice(List<DailyPattern> dailyPatterns) {
        int monthTotalPrice = 0;
        for(DailyPattern dailyPattern : dailyPatterns) {
            int totalPriceBasedOnTimes = dailyPatternItemService.getTotalPriceBasedOnTimes(dailyPattern);
            monthTotalPrice += totalPriceBasedOnTimes;
        }
        return monthTotalPrice;
    }
}
