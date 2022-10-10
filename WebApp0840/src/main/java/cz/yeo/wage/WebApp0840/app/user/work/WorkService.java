package cz.yeo.wage.WebApp0840.app.user.work;

import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.work.entity.Work;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;

    public Work create(SiteUser siteUser, String workingDate, Integer workingHours, Integer workingMinutes,
                            Integer extendedHours, Integer extendedMinutes, Integer nightHours, Integer nightMinutes, String workType) {
        try {
            Date formatDate = new SimpleDateFormat("yyyy-MM-dd").parse(workingDate);
            Work work = Work.builder()
                    .siteUser(siteUser)
                    .workingDate(formatDate)
                    .workingHours(workingHours)
                    .workingMinutes(workingMinutes)
                    .extendedHours(extendedHours)
                    .extendedMinutes(extendedMinutes)
                    .nightHours(nightHours)
                    .nightMinutes(nightMinutes)
                    .workType(workType)
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

    public List<Work> findBySiteUser(SiteUser siteUser) {
        return workRepository.findBySiteUser(siteUser);
    }


    public int count(SiteUser siteUser) {
        List<Work> works = findBySiteUser(siteUser);
        return works.size();
    }

    public int getHolidayCount(SiteUser siteUser) {
        List<Work> works = workRepository.findBySiteUser(siteUser);
        int hollidayCounts = 0;
        for(Work work : works) {
            if(work.getWorkType().equals("holiday")) {
                hollidayCounts += 1;
            }
        }
        return hollidayCounts;
    }


    public int convertHours(double hours, int minutes) {
        int cHours = minutes / 60;
        return (int)hours + cHours;
    }

    public int convertMinutes(double hours, int minutes) {
        return minutes % 60;
    }

    public HashMap<String, Integer> getAccWorks(SiteUser siteUser) {
        List<Work> works = workRepository.findBySiteUser(siteUser);
        HashMap<String, Integer> map = new HashMap<>();
        int sumHours = 0;
        int sumMinutes = 0;
        int sumRegularHours = 0;
        int sumRegularMinutes = 0;
        int sumExtendedHours = 0;
        int sumExtendedMinutes = 0;
        int sumNightHours = 0;
        int sumNightMinutes = 0;
        for(Work work : works) {
            sumRegularHours += work.getWorkingHours();
            sumRegularMinutes += work.getWorkingMinutes();
            sumExtendedHours += work.getExtendedHours();
            sumExtendedMinutes += work.getExtendedMinutes();
            sumNightHours += work.getNightHours();
            sumNightMinutes += work.getNightMinutes();
        }
        sumHours = sumRegularHours + sumExtendedHours + sumNightHours;
        sumMinutes = sumRegularMinutes + sumExtendedMinutes + sumNightMinutes;
        map.put("accHours", sumHours);
        map.put("accMinutes", sumMinutes);
        map.put("accRegularHours", sumRegularHours);
        map.put("accRegularMinutes", sumRegularMinutes);
        map.put("accExtendedHours", sumExtendedHours);
        map.put("accExtendedMinutes", sumExtendedMinutes);
        map.put("accNightHours", sumNightHours);
        map.put("accNightMinutes", sumNightMinutes);

        return map;
    }
}
