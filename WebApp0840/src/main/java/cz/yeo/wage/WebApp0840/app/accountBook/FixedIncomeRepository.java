package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedIncome;
import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedSpending;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedIncomeRepository extends JpaRepository<FixedIncome, Integer> {
}
