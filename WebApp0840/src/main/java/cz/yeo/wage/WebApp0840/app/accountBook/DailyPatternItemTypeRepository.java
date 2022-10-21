package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPattern;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPatternItemPrice;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPatternItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DailyPatternItemTypeRepository extends JpaRepository<DailyPatternItemType, Integer> {

    List<DailyPatternItemType> findTypesByDailyPattern(DailyPattern dailyPattern);

}
