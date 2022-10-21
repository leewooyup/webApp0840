package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPattern;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedIncome;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedSpending;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.work.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyPatternService {
    private final DailyPatternRepository dailyPatternRepository;
    public DailyPattern create(SiteUser siteUser, String dailyPatternName) {
        DailyPattern dailyPattern = DailyPattern.builder()
                .siteUser(siteUser)
                .dailyPatternName(dailyPatternName)
                .build();
        return dailyPatternRepository.save(dailyPattern);
    }

    public List<DailyPattern> findBySiteUser(SiteUser siteUser) {
        return dailyPatternRepository.findBySiteUser(siteUser);
    }
}
