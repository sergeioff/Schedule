package schedule.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import schedule.dao.BuzzerDao;
import schedule.dao.GroupDao;
import schedule.dao.PairDao;
import schedule.models.Pair;
import schedule.models.Settings;
import schedule.models.forms.StudentScheduleSelectForm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/forStudents")
public class StudentsScheduleController {
    private PairDao pairsRepository;
    private GroupDao groupsRepository;
    private BuzzerDao buzzersRepository;

    @Autowired
    public StudentsScheduleController(PairDao pairsRepository, GroupDao groupsRepository, BuzzerDao buzzersRepository) {
        this.pairsRepository = pairsRepository;
        this.groupsRepository = groupsRepository;
        this.buzzersRepository = buzzersRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String selectGroupSubgroupAndDay(StudentScheduleSelectForm selectForm,
                                            HttpServletResponse response) {
        ArrayList<Cookie> cookies = new ArrayList<>();

        cookies.add(new Cookie("selectedGroup", selectForm.getSelectedGroup().toString()));
        cookies.add(new Cookie("selectedSubgroup", selectForm.getSelectedSubgroup().toString()));
        cookies.add(new Cookie("selectedWeek", selectForm.getSelectedWeek().toString()));

        cookies.forEach(cookie -> {
            cookie.setMaxAge(CookieAgeConstants.Year);
            response.addCookie(cookie);
        });

        return "redirect:/forStudents";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showSchedule(@CookieValue(value = "selectedGroup", defaultValue = "-1") long groupId,
                               @CookieValue(value = "selectedSubgroup", defaultValue = "1") long subgroup,
                               @CookieValue(value = "selectedWeek", defaultValue = "1") long week, Model model) {

        if (groupId != -1) {
            long days = Settings.getDaysCount();
            HashMap<Long, List<Pair>> schedule = new HashMap<>();

            for (long day = 1; day <= days; day++) {
                schedule.put(day, pairsRepository.getPairsByGroupSubgroupWeekAndDay(
                        groupsRepository.getGroupById(groupId), subgroup, week, day));
            }

            model.addAttribute("schedule", schedule.entrySet());
            model.addAttribute("buzzers", buzzersRepository.getAllBuzzers());
            model.addAttribute("selectForm", new StudentScheduleSelectForm(groupId, subgroup, week));
        } else {
            model.addAttribute("selectForm", new StudentScheduleSelectForm());
        }

        model.addAttribute("groups", groupsRepository.getAllGroups());
        model.addAttribute("subgroupsCount", Settings.getSubgroupsInGroup());
        model.addAttribute("startWeek", Settings.getStartWeek());
        model.addAttribute("finalWeek", Settings.getFinalWeek());

        return "scheduleForStudents";
    }
}
