package cz.yeo.wage.WebApp0840.app.user;

import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Integer> {

    boolean existsByUsername(String username);

    Optional<SiteUser> findByUsername(String username);
}
