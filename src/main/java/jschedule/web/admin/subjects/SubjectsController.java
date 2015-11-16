package jschedule.web.admin.subjects;

import jschedule.dao.SubjectDao;
import jschedule.dao.TeacherDao;
import jschedule.models.domain.Subject;
import jschedule.models.forms.SubjectForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/subjects")
public class SubjectsController {
    private SubjectDao subjectDao;
    private TeacherDao teacherDao;

    @Autowired
    public SubjectsController(SubjectDao subjectDao, TeacherDao teacherDao) {
        this.subjectDao = subjectDao;
        this.teacherDao = teacherDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("subjects", subjectDao.getAllSubjects());
        return "admin/subjects/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddSubjectForm(Model model) {
        model.addAttribute("subjectForm", new SubjectForm());
        model.addAttribute("teachers", teacherDao.getAllTeachers());
        return "admin/subjects/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processSubjectForm(@Valid SubjectForm subjectForm, Errors errors,
                                     Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("teachers", teacherDao.getAllTeachers());
            return "admin/subjects/add";
        }

        subjectDao.save(new Subject(subjectForm.getName(), teacherDao.getTeacherById(subjectForm.getTeacherId())));
        redirectAttributes.addFlashAttribute("message", "Subject added successfully");
        return "redirect:/admin/subjects";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteSubject(@PathVariable long id, RedirectAttributes redirectAttributes) {
        subjectDao.delete(id);
        redirectAttributes.addFlashAttribute("message", "Subject deleted successfully");
        return "redirect:/admin/subjects";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        SubjectForm form = new SubjectForm();
        Subject subject = subjectDao.getSubjectById(id);

        form.setName(subject.getName());
        form.setTeacherId(subject.getTeacher().getId());

        model.addAttribute("subjectForm", form);
        model.addAttribute("teachers", teacherDao.getAllTeachers());
        model.addAttribute("id", id);
        return "admin/subjects/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEdit(@PathVariable long id, @Valid SubjectForm form,
                                Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("teachers", teacherDao.getAllTeachers());
            return "admin/subjects/edit";
        }

        subjectDao.update(id, form.getName(), teacherDao.getTeacherById(form.getTeacherId()));
        redirectAttributes.addFlashAttribute("message", "Subject successfully updated");
        return "redirect:/admin/subjects";
    }
}
