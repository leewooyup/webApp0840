package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPattern;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedIncome;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedSpending;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.work.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyPatternService {
    private final DailyPatternRepository dailyPatternRepository;
    public DailyPattern create(String dailyPatternName) {
        DailyPattern dailyPattern = DailyPattern.builder()
                .dailyPatternName(dailyPatternName)
                .build();
        return dailyPatternRepository.save(dailyPattern);
    }
}
