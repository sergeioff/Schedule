package jschedule.web.admin;

import jschedule.dao.TeacherDao;
import jschedule.models.Teacher;
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
    private String showForm(Teacher teacher, Model model) {
        model.addAttribute("actionName", "Add a new teacher");
        return "admin/teachers/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String processForm(@Valid Teacher teacher, BindingResult bindingResult,
                                      Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Add a new teacher");
            return "admin/teachers/form";
        }

        teacherDao.save(teacher);
        redirectAttributes.addFlashAttribute("message", "Teacher added successfully");
        return "redirect:/admin/teachers";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private String deleteTeacher(long deleteId, RedirectAttributes redirectAttributes) {
        Teacher teacher = teacherDao.getTeacherById(deleteId);
        teacherDao.delete(teacher);
        redirectAttributes.addFlashAttribute("message", "Teacher deleted successfully");
        return "redirect:/admin/teachers";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("actionName", "Edit teacher");
        model.addAttribute("teacher", teacherDao.getTeacherById(id));
        return "admin/teachers/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEdit(@Valid Teacher teacher, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Edit teacher");
            return "admin/teachers/form";
        }

        teacherDao.save(teacher);

        redirectAttributes.addFlashAttribute("message", "Teacher successfully updated");
        return "redirect:/admin/teachers";
    }
}
