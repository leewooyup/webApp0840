package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedSpending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedSpendingRepository extends JpaRepository<FixedSpending, Integer> {
}
