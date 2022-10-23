package cz.yeo.wage.WebApp0840.app.user.work;

import com.querydsl.apt.VisitorConfig;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.work.entity.Work;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;

    private HashMap<String, Integer> map = new HashMap<>();


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

    public int convertMinutes(int minutes) {
        return minutes % 60;
    }

    public HashMap<String, Integer> getAccWorks(SiteUser siteUser) {
        List<Work> works = workRepository.findBySiteUser(siteUser);
        int sumHours = 0;
        int sumMinutes = 0;
        int sumRegularHours = 0;
        int sumRegularMinutes = 0;
        int sumExtendedHours = 0;
        int sumExtendedMinutes = 0;
        int sumNightHours = 0;
        int sumNightMinutes = 0;
        int sumHolidayHours = 0;
        int sumHolidayMinutes = 0;
        for(Work work : works) {
            if(work.getWorkType().equals("regular")) {
                sumRegularHours += work.getWorkingHours();
                sumRegularMinutes += work.getWorkingMinutes();
            } else {
                sumHolidayHours += work.getExtendedHours();
                sumHolidayMinutes += work.getExtendedMinutes();
            }
            sumExtendedHours += work.getExtendedHours() - work.getWorkingHours();
            sumExtendedMinutes += work.getExtendedMinutes() - work.getWorkingMinutes();
            sumNightHours += work.getNightHours();
            sumNightMinutes += work.getNightMinutes();
        }
        sumRegularHours = convertHours(sumRegularHours, sumRegularMinutes);
        sumRegularMinutes = convertMinutes(sumRegularMinutes);

        sumExtendedHours = convertHours(sumExtendedHours, sumExtendedMinutes);
        sumExtendedMinutes = convertMinutes(sumExtendedMinutes);

        sumNightHours = convertHours(sumNightHours, sumNightMinutes);
        sumNightMinutes = convertMinutes(sumNightMinutes);

        sumHolidayHours = convertHours(sumHolidayHours, sumHolidayMinutes);
        sumHolidayMinutes = convertMinutes(sumHolidayMinutes);

        sumHours = sumRegularHours + sumExtendedHours + sumHolidayHours;
        sumMinutes = sumRegularMinutes + sumExtendedMinutes + sumHolidayMinutes;

        sumHours = convertHours(sumHours, sumMinutes);
        sumMinutes = convertMinutes(sumMinutes);

        map.put("accHours", sumHours);
        map.put("accMinutes", sumMinutes);
        map.put("accRegularHours", sumRegularHours);
        map.put("accRegularMinutes", sumRegularMinutes);
        map.put("accExtendedHours", sumExtendedHours);
        map.put("accExtendedMinutes", sumExtendedMinutes);
        map.put("accNightHours", sumNightHours);
        map.put("accNightMinutes", sumNightMinutes);
        map.put("accHolidayHours", sumHolidayHours);
        map.put("accHolidayMinutes", sumHolidayMinutes);

        return map;
    }

    public int getRegularHoursWage(SiteUser siteUser) {
        getAccWorks(siteUser);
        int accRegularHours = map.get("accRegularHours");
        return accRegularHours * siteUser.getBaseWage();
    }

    public double getExtendedHoursWage(SiteUser siteUser) {
        getAccWorks(siteUser);
        int accExtendedHours = map.get("accExtendedHours");
        return accExtendedHours * 1.5 * siteUser.getBaseWage();
    }

    public double getNightHoursWage(SiteUser siteUser) {
        getAccWorks(siteUser);
        int accNightHours = map.get("accNightHours");
        return accNightHours * 0.5 * siteUser.getBaseWage();
    }
    public double getHolidayHoursWage(SiteUser siteUser) {
        getAccWorks(siteUser);
        int accHolidayHours = map.get("accHolidayHours");
        return accHolidayHours * 1.5 * siteUser.getBaseWage();
    }

    public List<Double> getRegularMinutesWage(SiteUser siteUser) {
        List<Double> list = new ArrayList<>();
        int accRegularMinutes = map.get("accRegularMinutes");
        accRegularMinutes = convertMinutes(accRegularMinutes);
        int baseWage = siteUser.getBaseWage();
        double minutesToHours = 0.0;
        if (accRegularMinutes >= 30) {
            minutesToHours += 0.5;
            accRegularMinutes -= 30;
        }

        while (accRegularMinutes >= 10) {
            minutesToHours += 0.17;
            accRegularMinutes -= 10;
        }


        switch (accRegularMinutes) {
            case 9:
                minutesToHours += 0.15;
                break;
            case 8:
                minutesToHours += 0.1333;
                break;
            case 7:
                minutesToHours += 0.1166;
                break;
            case 6:
                minutesToHours += 0.1;
                break;
            case 5:
                minutesToHours += 0.0833;
                break;
        }
        list.add(minutesToHours);
        list.add(minutesToHours * baseWage);
        return list;
    }

    public List<Double> getExtendedMinutesWage(SiteUser siteUser) {
        List<Double> list = new ArrayList<>();
        int accExtendedMinutes = map.get("accExtendedMinutes");
        accExtendedMinutes = convertMinutes(accExtendedMinutes);
        int baseWage = siteUser.getBaseWage();
        double minutesToHours = 0.0;
        if (accExtendedMinutes >= 30) {
            minutesToHours += 0.5;
            accExtendedMinutes -= 30;
        }

        while (accExtendedMinutes >= 10) {
            minutesToHours += 0.17;
            accExtendedMinutes -= 10;
        }


        switch (accExtendedMinutes) {
            case 9:
                minutesToHours += 0.15;
                break;
            case 8:
                minutesToHours += 0.1333;
                break;
            case 7:
                minutesToHours += 0.1166;
                break;
            case 6:
                minutesToHours += 0.1;
                break;
            case 5:
                minutesToHours += 0.0833;
                break;
        }
        list.add(minutesToHours);
        list.add(minutesToHours * 1.5 * baseWage);
        return list;
    }

    public List<Double> getNightMinutesWage(SiteUser siteUser) {
        List<Double> list = new ArrayList<>();
        int accNightMinutes = map.get("accNightMinutes");
        accNightMinutes = convertMinutes(accNightMinutes);
        int baseWage = siteUser.getBaseWage();
        double minutesToHours = 0.0;
        if (accNightMinutes >= 30) {
            minutesToHours += 0.5;
            accNightMinutes -= 30;
        }

        while (accNightMinutes >= 10) {
            minutesToHours += 0.17;
            accNightMinutes -= 10;
        }


        switch (accNightMinutes) {
            case 9:
                minutesToHours += 0.15;
                break;
            case 8:
                minutesToHours += 0.1333;
                break;
            case 7:
                minutesToHours += 0.1166;
                break;
            case 6:
                minutesToHours += 0.1;
                break;
            case 5:
                minutesToHours += 0.0833;
                break;
        }
        list.add(minutesToHours);
        list.add(minutesToHours * 0.5 * baseWage);
        return list;
    }

    public List<Double> getHolidayMinutesWage(SiteUser siteUser) {
        List<Double> list = new ArrayList<>();
        int accHolidayMinutes = map.get("accHolidayMinutes");
        accHolidayMinutes = convertMinutes(accHolidayMinutes);
        int baseWage = siteUser.getBaseWage();
        double minutesToHours = 0.0;
        if (accHolidayMinutes >= 30) {
            System.out.println("30");
            minutesToHours += 0.5;
            accHolidayMinutes -= 30;
        }

        while (accHolidayMinutes >= 10) {
            System.out.println("10");
            minutesToHours += 0.17;
            accHolidayMinutes -= 10;
        }


        switch (accHolidayMinutes) {
            case 9:
                minutesToHours += 0.15;
                break;
            case 8:
                minutesToHours += 0.1333;
                break;
            case 7:
                minutesToHours += 0.1166;
                break;
            case 6:
                minutesToHours += 0.1;
                break;
            case 5:
                minutesToHours += 0.0833;
                break;
        }
        list.add(minutesToHours);
        list.add(minutesToHours * 0.5 * baseWage);
        return list;
    }

    public double getAccRegularWage(SiteUser siteUser) {
        return getRegularHoursWage(siteUser) + getRegularMinutesWage(siteUser).get(1);
    }

    public double getAccExtendedWage(SiteUser siteUser) {
        return getExtendedHoursWage(siteUser) + getExtendedMinutesWage(siteUser).get(1);
    }

    public double getAccNightWage(SiteUser siteUser) {
        return getNightHoursWage(siteUser) + getNightMinutesWage(siteUser).get(1);
    }

    public double getAccHolidayWage(SiteUser siteUser) {
        return getHolidayHoursWage(siteUser) + getHolidayMinutesWage(siteUser).get(1);
    }

    public double getAccTotalWage(SiteUser siteUser) {
        return getAccRegularWage(siteUser) + getAccExtendedWage(siteUser) + getAccNightWage(siteUser) + getAccHolidayWage(siteUser);
    }

    public String formatSecondDecimalPoint(Double d) {
        return String.format("%.2f", d);
    }
    public String formatFloorTenth(Double d) {
        return Integer.toString((int)Math.floor(d/10.0) * 10);
    }

    public String formatIntFloorTenth(Integer i) {
        return Integer.toString((int)Math.floor(i/10.0) * 10);
    }
}
