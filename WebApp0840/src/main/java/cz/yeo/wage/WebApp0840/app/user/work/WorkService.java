package cz.yeo.wage.WebApp0840.app.user.work;

import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.work.entity.Work;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;

    public Work create(SiteUser siteUser, String workingDate, Integer workingHours, Integer workingMinutes,
                            Integer extendedHours, Integer extendedMinutes) {
        try {
            Date formatDate = new SimpleDateFormat("yyyy-MM-dd").parse(workingDate);
            Work work = Work.builder()
                    .siteUser(siteUser)
                    .workingDate(formatDate)
                    .workingHours(workingHours)
                    .workingMinutes(workingMinutes)
                    .extendedHours(extendedHours)
                    .extendedMinutes(extendedMinutes)
                    .build();
            workRepository.save(work);
            return work;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
    public Work findById(Integer id) {
        return workRepository.findById(id).orElse(null);
    }
}
