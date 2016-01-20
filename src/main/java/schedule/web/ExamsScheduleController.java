package schedule.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import schedule.dao.ExamDao;
import schedule.dao.GroupDao;
import schedule.models.Exam;
import schedule.models.Group;
import schedule.models.forms.ExamsScheduleSelectForm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/exams")
public class ExamsScheduleController {
    private GroupDao groupsRepository;
    private ExamDao examsRepository;

    @Autowired
    public ExamsScheduleController(GroupDao groupsRepository, ExamDao examsRepository) {
        this.groupsRepository = groupsRepository;
        this.examsRepository = examsRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showExamsSchedule(@CookieValue(value = "selectedGroup", defaultValue = "-1") long groupId,
                                    Model model) {
        if (groupId != -1) {
            model.addAttribute("selectForm", new ExamsScheduleSelectForm(groupId));

            Group selectedGroup = groupsRepository.getGroupById(groupId);
            model.addAttribute("selectedGroupName", selectedGroup.getName());
            model.addAttribute("exams", examsRepository.getExamsByGroup(selectedGroup));
        } else {
            model.addAttribute("selectForm", new ExamsScheduleSelectForm());
        }

        model.addAttribute("groups", groupsRepository.getAllGroups());

        return "examsSchedule";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String selectGroup(ExamsScheduleSelectForm examsScheduleSelectForm, HttpServletResponse response) {
        Cookie selectedGroupCookie = new Cookie("selectedGroup", examsScheduleSelectForm.getSelectedGroup().toString());
        selectedGroupCookie.setMaxAge(CookieAgeConstants.Year);
        response.addCookie(selectedGroupCookie);

        return "redirect:/exams";
    }
}
