package schedule.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import schedule.dao.BuzzerDao;
import schedule.dao.PairDao;
import schedule.dao.TeacherDao;
import schedule.models.Pair;
import schedule.models.Settings;
import schedule.models.forms.TeacherScheduleSelectForm;
import schedule.utils.CurrentWeekService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("forTeachers")
public class TeachersScheduleController {
    private PairDao pairsRepository;
    private BuzzerDao buzzersRepository;
    private TeacherDao teachersRepository;
    private CurrentWeekService currentWeekService;

    @Autowired
    public TeachersScheduleController(PairDao pairsRepository, BuzzerDao buzzersRepository,
                                      TeacherDao teacherDao, CurrentWeekService currentWeekService) {
        this.pairsRepository = pairsRepository;
        this.buzzersRepository = buzzersRepository;
        this.teachersRepository = teacherDao;
        this.currentWeekService = currentWeekService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showSchedule(@CookieValue(value = "selectedTeacher", defaultValue = "-1") long teacherId,
                               @CookieValue(value = "selectedWeek", defaultValue = "-1") long week,
                               Model model) {
        if (teacherId != -1) {
            long days = Settings.getDaysCount();
            HashMap<Long, List<Pair>> schedule = new HashMap<>();

            for (long day = 1; day <= days; day++) {
                schedule.put(day, pairsRepository.getPairsByTeacherWeekAndDay(
                        teachersRepository.getTeacherById(teacherId), week, day));

                model.addAttribute("schedule", schedule.entrySet());
                model.addAttribute("buzzers", buzzersRepository.getAllBuzzers());
                model.addAttribute("selectForm", new TeacherScheduleSelectForm(teacherId, week));
            }
        } else {
            model.addAttribute("selectForm", new TeacherScheduleSelectForm());
        }

        model.addAttribute("teachers", teachersRepository.getAllTeachers());
        model.addAttribute("startWeek", Settings.getStartWeek());
        model.addAttribute("finalWeek", Settings.getFinalWeek());
        model.addAttribute("currentWeek", currentWeekService.getCurrentWeek());

        return "scheduleForTeachers";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String selectTeacherAndWeek(TeacherScheduleSelectForm selectForm, HttpServletResponse response) {
        ArrayList<Cookie> cookies = new ArrayList<>();

        cookies.add(new Cookie("selectedTeacher", selectForm.getSelectedTeacher().toString()));
        cookies.add(new Cookie("selectedWeek", selectForm.getSelectedWeek().toString()));

        cookies.forEach(cookie -> {
            cookie.setMaxAge(CookieAgeConstants.Year);
            response.addCookie(cookie);
        });

        return "redirect:/forTeachers";
    }
}

