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
import schedule.dao.SubjectDao;
import schedule.dao.TeacherDao;
import schedule.models.Subject;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/admin/subjects")
public class SubjectsController {
    private SubjectDao subjectsRepository;
    private TeacherDao teachersRepository;
    private MessageSource messageSource;

    @Autowired
    public SubjectsController(SubjectDao subjectsRepository, TeacherDao teachersRepository,
                              MessageSource messageSource) {
        this.subjectsRepository = subjectsRepository;
        this.teachersRepository = teachersRepository;
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("subjects", subjectsRepository.getAllSubjects());
        model.addAttribute("currentTab", "subjects");
        return "admin/subjects/index";
    }

    @SuppressWarnings("UnusedParameters")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showForm(Subject subject, Model model) {
        model.addAttribute("action", "add");
        model.addAttribute("teachers", teachersRepository.getAllTeachers());
        return "admin/subjects/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processForm(@Valid Subject subject, BindingResult bindingResult, Model model,
                              RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            model.addAttribute("teachers", teachersRepository.getAllTeachers());
            return "admin/subjects/form";
        }

        subjectsRepository.save(subject);

        String message = messageSource.getMessage("admin.messages.subjectAdded", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/subjects";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("action", "edit");
        model.addAttribute("subject", subjectsRepository.getSubjectById(id));
        model.addAttribute("teachers", teachersRepository.getAllTeachers());
        return "admin/subjects/form";
    }

    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEditForm(@Valid Subject subject, BindingResult bindingResult, Model model,
                                   RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            model.addAttribute("teachers", teachersRepository.getAllTeachers());
            return "admin/subjects/form";
        }

        subjectsRepository.save(subject);

        String message = messageSource.getMessage("admin.messages.subjectUpdated", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/subjects";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteSubject(long deleteId, RedirectAttributes redirectAttributes, Locale locale) {
        Subject subject = subjectsRepository.getSubjectById(deleteId);

        try {
            subjectsRepository.delete(subject);
            redirectAttributes.addFlashAttribute("message",
                    messageSource.getMessage("admin.messages.subjectDeleted", null, locale));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("admin.messages.subjectDeletionError", null, locale));
        }

        return "redirect:/admin/subjects";
    }
}
