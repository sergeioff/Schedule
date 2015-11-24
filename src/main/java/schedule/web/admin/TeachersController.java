package schedule.web.admin;

import schedule.dao.TeacherDao;
import schedule.models.Teacher;
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
    private TeacherDao teachersRepository;

    @RequestMapping(method = RequestMethod.GET)
    private String index(Model model) {
        model.addAttribute("teachers", teachersRepository.getAllTeachers());
        model.addAttribute("currentTab", "teachers");
        return "admin/teachers/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String showForm(Teacher teacher, Model model) {
        model.addAttribute("action", "add");
        return "admin/teachers/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String processForm(@Valid Teacher teacher, BindingResult bindingResult,
                                      Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            return "admin/teachers/form";
        }

        teachersRepository.save(teacher);
        redirectAttributes.addFlashAttribute("message", "teacherAdded");
        return "redirect:/admin/teachers";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private String deleteTeacher(long deleteId, RedirectAttributes redirectAttributes) {
        Teacher teacher = teachersRepository.getTeacherById(deleteId);
        teachersRepository.delete(teacher);
        redirectAttributes.addFlashAttribute("message", "teacherDeleted");
        return "redirect:/admin/teachers";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("action", "edit");
        model.addAttribute("teacher", teachersRepository.getTeacherById(id));
        return "admin/teachers/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEdit(@Valid Teacher teacher, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            return "admin/teachers/form";
        }

        teachersRepository.save(teacher);

        redirectAttributes.addFlashAttribute("message", "teacherUpdated");
        return "redirect:/admin/teachers";
    }
}
