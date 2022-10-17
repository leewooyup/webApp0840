package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPattern;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPatternItemPrice;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPatternItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
