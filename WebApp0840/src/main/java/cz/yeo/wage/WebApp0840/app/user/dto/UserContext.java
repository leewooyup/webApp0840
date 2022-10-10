package cz.yeo.wage.WebApp0840.app.user.dto;


import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserContext extends User {
    private final Integer id;
    private final String nickname;
    private final LocalDateTime createDate;
    private final Double BaseWage;
    private final Integer annual;
    private final String userImgUrl;


    public UserContext(SiteUser siteUser, List<GrantedAuthority> authorities) {
        super(siteUser.getUsername(), siteUser.getPassword(), authorities);
        this.id = siteUser.getId();
        this.nickname = siteUser.getNickname();
        this.createDate = siteUser.getCreateDate();
        this.BaseWage = siteUser.getBaseWage();
        this.annual = siteUser.getAnnual();
        this.userImgUrl = siteUser.getUserImgUrl();
    }
}
