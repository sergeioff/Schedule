package jschedule.web.admin.teachers;

import jschedule.dao.TeacherDao;
import jschedule.models.forms.TeacherForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/teachers")
public class TeachersController {
    @Autowired
    private TeacherDao teachersDao;

    @RequestMapping(method = RequestMethod.GET)
    private String index(Model model) {
        model.addAttribute("teachers", teachersDao.getAllTeachers());
        return "admin/teachers/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String showAddTeacherForm(Model model) {
        model.addAttribute("teacherForm", new TeacherForm());
        return "admin/teachers/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String processTeacherForm(@Valid TeacherForm teacherForm, Errors errors, RedirectAttributes model) {
        if (errors.hasErrors()) {
            return "admin/teachers/add";
        }

        teachersDao.save(teacherForm.toTeacher());
        model.addFlashAttribute("message", "Teacher added successfully");
        return "redirect:/admin/teachers";
    }

    @RequestMapping(value = "/delete/{id}")
    private String deleteTeacher(@PathVariable long id, RedirectAttributes model) {
        teachersDao.delete(id);
        model.addFlashAttribute("message", "Teacher deleted successfully");
        return "redirect:/admin/teachers";
    }
}
