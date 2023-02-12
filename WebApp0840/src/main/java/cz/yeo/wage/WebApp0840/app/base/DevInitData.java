package cz.yeo.wage.WebApp0840.app.base;

import cz.yeo.wage.WebApp0840.app.accountBook.FixedInfoService;
import cz.yeo.wage.WebApp0840.app.article.entity.Article;
import cz.yeo.wage.WebApp0840.app.article.service.ArticleService;
import cz.yeo.wage.WebApp0840.app.user.UserService;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.work.WorkService;
import cz.yeo.wage.WebApp0840.app.user.work.entity.Work;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@Profile("dev")
public class DevInitData {
    @Bean
    CommandLineRunner init(UserService userService, WorkService workService, FixedInfoService fixedInfoService,
                           ArticleService articleService, PasswordEncoder passwordEncoder) {
        return args -> {
            String password = passwordEncoder.encode("1234");

            SiteUser siteUser1 = userService.join("user1", password, "admin", "user1@test.com",
                    "아웃백", 11200, true, true, true, 23, 5, new Date(2019,7,18));

            Work work1 = workService.create(siteUser1, "2022-12-03", 5, 30, 5, 31, 0, 31, "근무일");
            Work work2 = workService.create(siteUser1, "2022-12-17", 5, 30, 5, 31, 0, 31, "근무일");
            Work work3 = workService.create(siteUser1, "2022-12-18", 5, 30, 5, 38, 0, 38, "근무일");
            Work work4 = workService.create(siteUser1, "2022-12-25", 6, 0, 6, 49, 0, 49, "근무일");
            Work work5 = workService.create(siteUser1, "2022-12-31", 6, 0, 6, 41, 0, 41, "근무일");

            fixedInfoService.createFixedSpending(siteUser1, "보험료", 7700, "자동차보험료");
            fixedInfoService.createFixedSpending(siteUser1, "통신비", 88000, "다음달 요금제 변경 필요");

            fixedInfoService.createFixedIncome(siteUser1, "용돈", 300000, "취업할때까지만");
            SiteUser siteUser2 = userService.join("user2", password, "nick", "user2@test.com",
                    "카페", 9620, true, true, true, 0, 7, new Date(2023,1,2));

            Article article1 = articleService.create(siteUser1, "실업급여란?", "대한민국 4대 보험 中 1","직장을 잃게된 경우...");
            Article article2 = articleService.create(siteUser1, "연차란?", "근로기준법 제 60조", "1년동안 80%이상을...");
            Article article3 = articleService.create(siteUser1, "연말정산이란?", "13월의 월급", "직장인이 매달 월급애서 떼인...");
            Article article4 = articleService.create(siteUser1, "주휴수당이란?", "근로기준법 제 55조", "사업장 규모에 관계없이....");
            Article article5 = articleService.create(siteUser1, "주휴수당이란?", "근로기준법 제 55조", "사업장 규모에 관계없이....");
            Article article6 = articleService.create(siteUser1, "주휴수당이란?", "근로기준법 제 55조", "사업장 규모에 관계없이....");
            Article article7 = articleService.create(siteUser1, "주휴수당이란?", "근로기준법 제 55조", "사업장 규모에 관계없이....");
            Article article8 = articleService.create(siteUser1, "주휴수당이란?", "근로기준법 제 55조", "사업장 규모에 관계없이....");
            Article article9 = articleService.create(siteUser1, "주휴수당이란?", "근로기준법 제 55조", "사업장 규모에 관계없이....");
            Article article10 = articleService.create(siteUser1, "주휴수당이란?", "근로기준법 제 55조", "사업장 규모에 관계없이....");
            Article article11 = articleService.create(siteUser1, "주휴수당이란?", "근로기준법 제 55조", "사업장 규모에 관계없이....");
            Article article12 = articleService.create(siteUser1, "주휴수당이란?", "근로기준법 제 55조", "사업장 규모에 관계없이....");
        };
    }

}