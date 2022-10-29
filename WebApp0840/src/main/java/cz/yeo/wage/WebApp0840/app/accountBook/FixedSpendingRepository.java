package cz.yeo.wage.WebApp0840.app.accountBook;

import cz.yeo.wage.WebApp0840.app.accountBook.entity.FixedSpending;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixedSpendingRepository extends JpaRepository<FixedSpending, Integer> {
    List<FixedSpending> findBySiteUser(SiteUser siteUser);

    void deleteBySiteUser(SiteUser siteUser);
}
