package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.user.UserRepository;
import cz.yeo.wage.WebApp0840.app.user.UserService;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserService userService;

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


}
