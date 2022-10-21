package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPattern;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPatternItemPrice;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPatternItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyPatternItemService {
    private final DailyPatternItemTypeRepository dailyPatternItemTypeRepository;
    private final DailyPatternItemPriceRepository dailyPatternItemPriceRepository;

    public DailyPatternItemType createType(DailyPattern dailyPattern, String type) {
        DailyPatternItemType dailyPatternItemType = new DailyPatternItemType();
        dailyPatternItemType.setDailyPattern(dailyPattern);
        dailyPatternItemType.setDailyConsumptionType(type);
        dailyPatternItemTypeRepository.save(dailyPatternItemType);
        return dailyPatternItemType;
    }

    public DailyPatternItemPrice createPrice(DailyPattern dailyPattern, String price) {
        DailyPatternItemPrice dailyPatternItemPrice = new DailyPatternItemPrice();
        dailyPatternItemPrice.setDailyPattern(dailyPattern);
        dailyPatternItemPrice.setDailyConsumptionPrice(Integer.parseInt(price));
        dailyPatternItemPriceRepository.save(dailyPatternItemPrice);
        return dailyPatternItemPrice;
    }

    public List<DailyPatternItemType> findTypesByDailyPattern(DailyPattern dailyPattern) {
        return dailyPatternItemTypeRepository.findTypesByDailyPattern(dailyPattern);
    }

    public List<DailyPatternItemPrice> findPricesByDailyPattern(DailyPattern dailyPattern) {
        return dailyPatternItemPriceRepository.findPricesByDailyPattern(dailyPattern);
    }

    public int getTotalPriceBasedOnTimes(DailyPattern dailyPattern) {
        List<DailyPatternItemPrice> dailyPatternItemPrices = dailyPatternItemPriceRepository.findPricesByDailyPattern(dailyPattern);
        int sum = 0;
        for(DailyPatternItemPrice dailyPatternItemPrice : dailyPatternItemPrices) {
            sum += dailyPatternItemPrice.getDailyConsumptionPrice();
        }
        int times = dailyPattern.getTimesPerMonth();
        return sum * times;
    }

    public int getSumPrice(DailyPattern dailyPattern) {
        List<DailyPatternItemPrice> dailyPatternItemPrices = dailyPatternItemPriceRepository.findPricesByDailyPattern(dailyPattern);
        int sum = 0;
        for(DailyPatternItemPrice dailyPatternItemPrice : dailyPatternItemPrices) {
            sum += dailyPatternItemPrice.getDailyConsumptionPrice();
        }
        return sum;
    }
}
