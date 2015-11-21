package schedule.web.admin;

import schedule.dao.SubjectDao;
import schedule.dao.TeacherDao;
import schedule.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/subjects")
public class SubjectsController {
    private SubjectDao subjectsRepository;
    private TeacherDao teachersRepository;

    @Autowired
    public SubjectsController(SubjectDao subjectsRepository, TeacherDao teachersRepository) {
        this.subjectsRepository = subjectsRepository;
        this.teachersRepository = teachersRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("subjects", subjectsRepository.getAllSubjects());
        return "admin/subjects/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showForm(Subject subject, Model model) {
        model.addAttribute("action", "add");
        model.addAttribute("teachers", teachersRepository.getAllTeachers());
        return "admin/subjects/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processForm(@Valid Subject subject, BindingResult bindingResult,
                                    Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            model.addAttribute("teachers", teachersRepository.getAllTeachers());
            return "admin/subjects/form";
        }

        subjectsRepository.save(subject);
        redirectAttributes.addFlashAttribute("message", "subjectAdded");

        return "redirect:/admin/subjects";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("action", "edit");
        model.addAttribute("subject", subjectsRepository.getSubjectById(id));
        model.addAttribute("teachers", teachersRepository.getAllTeachers());
        return "admin/subjects/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEditForm(@Valid Subject subject, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            model.addAttribute("teachers", teachersRepository.getAllTeachers());
            return "admin/subjects/form";
        }

        subjectsRepository.save(subject);

        redirectAttributes.addFlashAttribute("message", "subjectUpdated");
        return "redirect:/admin/subjects";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteSubject(long deleteId, RedirectAttributes redirectAttributes) {
        Subject subject = subjectsRepository.getSubjectById(deleteId);
        subjectsRepository.delete(subject);
        redirectAttributes.addFlashAttribute("message", "subjectDeleted");
        return "redirect:/admin/subjects";
    }
}