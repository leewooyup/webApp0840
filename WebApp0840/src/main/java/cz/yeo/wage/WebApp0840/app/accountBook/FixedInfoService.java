package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedIncome;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedSpending;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.work.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FixedInfoService {
    private final FixedSpendingRepository fixedSpendingRepository;
    private final FixedIncomeRepository fixedIncomeRepository;
    private final WorkService workService;

    public void createFixedSpending(SiteUser siteUser, String fixedSpendingType, Integer fixedSpending, String fixedSpendingMemo) {
        siteUser.setRegisteredFixedSpending(true);
        FixedSpending fixedInfo = FixedSpending.builder()
                .siteUser(siteUser)
                .fixedSpendingType(fixedSpendingType)
                .fixedSpending(fixedSpending)
                .fixedSpendingMemo(fixedSpendingMemo)
                .build();
        fixedSpendingRepository.save(fixedInfo);
    }

    public void createFixedIncome(SiteUser siteUser, String fixedIncomeType, Integer fixedIncome, String fixedIncomeMemo) {
        siteUser.setRegisteredFixedIncome(true);
        FixedIncome fixedInfo = FixedIncome.builder()
                .siteUser(siteUser)
                .fixedIncomeType(fixedIncomeType)
                .fixedIncome(fixedIncome)
                .fixedIncomeMemo(fixedIncomeMemo)
                .build();
        fixedIncomeRepository.save(fixedInfo);
    }

    public List<FixedSpending> findFixedSpendingAll(SiteUser siteUser) {
        return fixedSpendingRepository.findAll();
    }

    public List<FixedIncome> findFixedIncomeAll(SiteUser siteUser) {
        return fixedIncomeRepository.findAll();
    }

    public int getFixedSpendingSum(SiteUser siteUser) {
        int sum = 0;
        List<FixedSpending> fixedSpendings = findFixedSpendingAll(siteUser);
        for(FixedSpending fixedSpending : fixedSpendings) {
            sum += fixedSpending.getFixedSpending();
        }
        return sum;
    }

    public int getFixedIncomeSum(SiteUser siteUser) {
        int sum = 0;
        List<FixedIncome> fixedIncomes = findFixedIncomeAll(siteUser);
        for(FixedIncome fixedIncome : fixedIncomes) {
            sum += fixedIncome.getFixedIncome();
        }
        return sum;

    }

    public int getBudget(SiteUser siteUser) {
        return (int)workService.getAccTotalWage(siteUser) + getFixedIncomeSum(siteUser) - getFixedSpendingSum(siteUser);
    }

    public List<FixedSpending> findFixedSpendingBySiteUser(SiteUser siteUser) {
        return fixedSpendingRepository.findBySiteUser(siteUser);
    }

    public List<FixedIncome> findFixedIncomeBySiteUser(SiteUser siteUser) {
        return fixedIncomeRepository.findBySiteUser(siteUser);
    }

    public void deleteAll(SiteUser siteUser) {
        fixedSpendingRepository.deleteBySiteUser(siteUser);
        fixedIncomeRepository.deleteBySiteUser(siteUser);
    }
}
