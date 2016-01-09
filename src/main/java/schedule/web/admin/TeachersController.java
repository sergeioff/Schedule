package schedule.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import schedule.dao.TeacherDao;
import schedule.models.Teacher;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/admin/teachers")
public class TeachersController {
    private TeacherDao teachersRepository;
    private MessageSource messageSource;

    @Autowired
    public TeachersController(TeacherDao teachersRepository, MessageSource messageSource) {
        this.teachersRepository = teachersRepository;
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    private String index(Model model) {
        model.addAttribute("teachers", teachersRepository.getAllTeachers());
        model.addAttribute("currentTab", "teachers");
        return "admin/teachers/index";
    }

    @SuppressWarnings("UnusedParameters")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String showForm(Teacher teacher, Model model) {
        model.addAttribute("action", "add");
        return "admin/teachers/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String processForm(@Valid Teacher teacher, BindingResult bindingResult, Model model,
                               RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            return "admin/teachers/form";
        }

        teachersRepository.save(teacher);

        String message = messageSource.getMessage("admin.messages.teacherAdded", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/teachers";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private String deleteTeacher(long deleteId, RedirectAttributes redirectAttributes, Locale locale) {
        Teacher teacher = teachersRepository.getTeacherById(deleteId);
        teachersRepository.delete(teacher);

        String message = messageSource.getMessage("admin.messages.teacherDeleted", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/teachers";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("action", "edit");
        model.addAttribute("teacher", teachersRepository.getTeacherById(id));
        return "admin/teachers/form";
    }

    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEdit(@Valid Teacher teacher, BindingResult bindingResult, Model model,
                               RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            return "admin/teachers/form";
        }

        teachersRepository.save(teacher);

        String message = messageSource.getMessage("admin.messages.teacherUpdated", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/teachers";
    }
}
