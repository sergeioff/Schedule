package schedule.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import schedule.dao.WeeksDao;
import schedule.models.Week;

import java.time.LocalDate;
import java.util.List;

@Component
public class CurrentWeekService {
    @Autowired
    private WeeksDao weeksRepository;

    public String getCurrentWeek() {
        List<Week> weeks = weeksRepository.getAllWeeks();

        LocalDate currentDate = LocalDate.now();

        for (Week week : weeks) {
            LocalDate startOfWeek = week.getStart();
            LocalDate endOfWeek = week.getEnd();

            if (currentDate.isAfter(endOfWeek)) {
                continue;
            }

            if (currentDate.isEqual(startOfWeek) || currentDate.isEqual(endOfWeek) ||
                    (currentDate.isAfter(startOfWeek) && currentDate.isBefore(endOfWeek)))
                return week.getNumber().toString();
        }

        return "N/A";
    }
}
