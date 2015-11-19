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
    private GroupDao groupRepository;
    private SubjectDao subjectRepository;

    @Autowired
    public GroupsController(GroupDao groupRepository, SubjectDao subjectRepository) {
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("groups", groupRepository.getAllGroups());
        return "admin/groups/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showForm(Group group, Model model) {
        model.addAttribute("actionName", "Add a new group");
        model.addAttribute("subjectsList", subjectRepository.getAllSubjects());
        return "admin/groups/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddGroup(@Valid Group group, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Add a new group");
            model.addAttribute("subjectsList", subjectRepository.getAllSubjects());
            return "admin/groups/form";
        }

        groupRepository.save(group);
        redirectAttributes.addFlashAttribute("message", "Group added successfully");
        return "redirect:/admin/groups";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("actionName", "Edit group");
        model.addAttribute("group", groupRepository.getGroupById(id));
        model.addAttribute("subjectsList", subjectRepository.getAllSubjects());
        return "admin/groups/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String updateGroup(@Valid Group group, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Edit group");
            model.addAttribute("subjectsList", subjectRepository.getAllSubjects());
            return "admin/groups/form";
        }

        groupRepository.save(group);

        redirectAttributes.addFlashAttribute("message", "Group updated successfully");
        return "redirect:/admin/groups";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteGroup(long deleteId, RedirectAttributes redirectAttributes) {
        Group group = groupRepository.getGroupById(deleteId);
        groupRepository.delete(group);

        redirectAttributes.addFlashAttribute("message", "Group deleted successfully");
        return "redirect:/admin/groups";
    }
}
