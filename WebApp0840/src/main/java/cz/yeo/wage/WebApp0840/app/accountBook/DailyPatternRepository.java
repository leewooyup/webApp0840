package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.DailyPattern;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedIncome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyPatternRepository extends JpaRepository<DailyPattern, Integer> {
}
