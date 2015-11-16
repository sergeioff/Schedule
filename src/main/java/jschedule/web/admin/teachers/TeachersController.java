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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/teachers")
public class TeachersController {
    @Autowired
    private TeacherDao teacherDao;

    @RequestMapping(method = RequestMethod.GET)
    private String index(Model model) {
        model.addAttribute("teachers", teacherDao.getAllTeachers());
        return "admin/teachers/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String showAddTeacherForm(Model model) {
        model.addAttribute("teacherForm", new TeacherForm());
        return "admin/teachers/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String processTeacherForm(@Valid TeacherForm teacherForm, Errors errors,
                                      RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return "admin/teachers/add";
        }

        teacherDao.save(teacherForm.toTeacher());
        redirectAttributes.addFlashAttribute("message", "Teacher added successfully");
        return "redirect:/admin/teachers";
    }

    @RequestMapping(value = "/delete/{id}")
    private String deleteTeacher(@PathVariable long id, RedirectAttributes redirectAttributes) {
        teacherDao.delete(id);
        redirectAttributes.addFlashAttribute("message", "Teacher deleted successfully");
        return "redirect:/admin/teachers";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        TeacherForm form = new TeacherForm();
        form.setName(teacherDao.getTeacherById(id).getName());

        model.addAttribute("teacherForm", form);
        model.addAttribute("id", id);
        return "admin/teachers/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEdit(@PathVariable long id, @Valid TeacherForm form,
                                     Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addAttribute("id", id);
            return "admin/teachers/edit";
        }

        teacherDao.update(id, form.getName());
        redirectAttributes.addFlashAttribute("message", "Teacher successfully updated");
        return "redirect:/admin/teachers";
    }
}
