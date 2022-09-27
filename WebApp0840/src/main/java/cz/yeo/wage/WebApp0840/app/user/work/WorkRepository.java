package cz.yeo.wage.WebApp0840.app.user.work;


import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.work.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Integer> {

    List<Work> findBySiteUser(SiteUser siteUser);
}
