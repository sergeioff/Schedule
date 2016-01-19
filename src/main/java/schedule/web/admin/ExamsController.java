package schedule.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import schedule.dao.ExamsDao;
import schedule.dao.GroupDao;
import schedule.models.Exam;
import schedule.models.Group;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("admin/exams")
public class ExamsController {
    private GroupDao groupRepository;
    private MessageSource messageSource;
    private ExamsDao examsRepository;

    @Autowired
    public ExamsController(GroupDao groupRepository, MessageSource messageSource, ExamsDao examsRepository) {
        this.groupRepository = groupRepository;
        this.messageSource = messageSource;
        this.examsRepository = examsRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(@CookieValue(value = "group", defaultValue = "-1") long groupId, Model model) {
        model.addAttribute("groups", groupRepository.getAllGroups());
        model.addAttribute("currentTab", "exams");

        Group selectedGroup = groupRepository.getGroupById(groupId);
        if (selectedGroup != null) {
            model.addAttribute("selectedGroup", selectedGroup);
            model.addAttribute("exams", examsRepository.getExamsByGroup(selectedGroup));
        } else {
            model.addAttribute("selectedGroup", new Group());
            model.addAttribute("selectGroupMessage", true);
        }

        return "admin/exams/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String selectGroup(Long selectedGroup, HttpServletResponse response) {
        response.addCookie(new Cookie("group", selectedGroup.toString()));
        return "redirect:/admin/exams";
    }

    @RequestMapping(value = "/addExamFor{groupId}", method = RequestMethod.GET)
    public String showForm(@PathVariable long groupId, @ModelAttribute Exam exam, Model model) {
        model.addAttribute("action", "add");
        model.addAttribute("currentGroup", groupRepository.getGroupById(groupId));

        return "admin/exams/form";
    }

    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/addExamFor{groupId}", method = RequestMethod.POST)
    public String processForm(@Valid Exam exam, BindingResult bindingResult, Model model,
                              RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            model.addAttribute("currentGroup", exam.getCurrentGroup());

            return "admin/exams/form";
        }

        examsRepository.save(exam);

        String message = messageSource.getMessage("admin.messages.examAdded", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/exams";
    }

    @RequestMapping(value = "/editExam/{examId}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long examId, Model model) {
        model.addAttribute("action", "edit");

        Exam exam = examsRepository.getExamById(examId);
        model.addAttribute("exam", exam);
        model.addAttribute("currentGroup", exam.getCurrentGroup());

        return "admin/exams/form";
    }

    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/editExam/{id}", method = RequestMethod.POST)
    public String processEditForm(@Valid Exam exam, BindingResult bindingResult, Model model,
                                  RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            model.addAttribute("currentGroup", exam.getCurrentGroup());

            return "admin/exams/form";
        }

        examsRepository.save(exam);

        String message = messageSource.getMessage("admin.messages.examUpdated", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/exams/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteExam(long deleteId, RedirectAttributes redirectAttributes, Locale locale) {
        Exam exam = examsRepository.getExamById(deleteId);
        examsRepository.delete(exam);

        String message = messageSource.getMessage("admin.messages.examDeleted", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/exams";
    }
}
