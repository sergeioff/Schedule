package jschedule.web.admin;

import jschedule.dao.SubjectDao;
import jschedule.dao.TeacherDao;
import jschedule.models.Subject;
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
        model.addAttribute("actionName", "Add a new subject");
        model.addAttribute("teachers", teachersRepository.getAllTeachers());
        return "admin/subjects/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processForm(@Valid Subject subject, BindingResult bindingResult,
                                    Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Add a new subject");
            model.addAttribute("teachers", teachersRepository.getAllTeachers());
            return "admin/subjects/form";
        }

        subjectsRepository.save(subject);
        redirectAttributes.addFlashAttribute("message", "Subject added successfully");

        return "redirect:/admin/subjects";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("actionName", "Edit subject");
        model.addAttribute("subject", subjectsRepository.getSubjectById(id));
        model.addAttribute("teachers", teachersRepository.getAllTeachers());
        return "admin/subjects/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEditForm(@Valid Subject subject, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Edit subject");
            model.addAttribute("teachers", teachersRepository.getAllTeachers());
            return "admin/subjects/form";
        }

        subjectsRepository.save(subject);

        redirectAttributes.addFlashAttribute("message", "Subject successfully updated");
        return "redirect:/admin/subjects";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteSubject(long deleteId, RedirectAttributes redirectAttributes) {
        Subject subject = subjectsRepository.getSubjectById(deleteId);
        subjectsRepository.delete(subject);
        redirectAttributes.addFlashAttribute("message", "Subject deleted successfully");
        return "redirect:/admin/subjects";
    }
}