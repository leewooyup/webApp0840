package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPatternItemPrice;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPatternItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyPatternItemPriceRepository extends JpaRepository<DailyPatternItemPrice, Integer> {
}
