package jschedule.web.admin;

import jschedule.dao.GroupDao;
import jschedule.dao.SubjectDao;
import jschedule.models.Group;
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
@RequestMapping("/admin/groups")
public class GroupsController {
    private GroupDao groupDao;
    private SubjectDao subjectDao;

    @Autowired
    public GroupsController(GroupDao groupDao, SubjectDao subjectDao) {
        this.groupDao = groupDao;
        this.subjectDao = subjectDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("groups", groupDao.getAllGroups());
        return "admin/groups/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddGroupForm(Group group, Model model) {
        model.addAttribute("subjectsList", subjectDao.getAllSubjects());
        return "admin/groups/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddGroup(@Valid Group group, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("subjectsList", subjectDao.getAllSubjects());
            return "admin/groups/add";
        }

        groupDao.save(group);
        redirectAttributes.addFlashAttribute("message", "Group added successfully");
        return "redirect:/admin/groups";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("group", groupDao.getGroupById(id));
        model.addAttribute("subjectsList", subjectDao.getAllSubjects());
        return "admin/groups/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String updateGroup(@Valid Group group, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("subjectsList", subjectDao.getAllSubjects());
            return "admin/groups/edit";
        }

        groupDao.save(group);

        redirectAttributes.addFlashAttribute("message", "Group updated successfully");
        return "redirect:/admin/groups";
    }
}
