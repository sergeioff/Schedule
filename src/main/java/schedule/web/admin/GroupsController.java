package schedule.web.admin;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import schedule.dao.GroupDao;
import schedule.dao.SubjectDao;
import schedule.models.Group;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/admin/groups")
public class GroupsController {
    private GroupDao groupRepository;
    private SubjectDao subjectRepository;
    private MessageSource messageSource;

    @Autowired
    public GroupsController(GroupDao groupRepository, SubjectDao subjectRepository, MessageSource messageSource) {
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("groups", groupRepository.getAllGroups());
        model.addAttribute("currentTab", "groups");
        return "admin/groups/index";
    }

    @SuppressWarnings("UnusedParameters")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showForm(Group group, Model model) {
        model.addAttribute("action", "add");
        model.addAttribute("subjectsList", subjectRepository.getAllSubjects());
        return "admin/groups/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddGroup(@Valid Group group, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            model.addAttribute("subjectsList", subjectRepository.getAllSubjects());
            return "admin/groups/form";
        }

        groupRepository.save(group);

        String message = messageSource.getMessage("admin.messages.groupAdded", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/groups";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("action", "edit");
        model.addAttribute("group", groupRepository.getGroupById(id));
        model.addAttribute("subjectsList", subjectRepository.getAllSubjects());
        return "admin/groups/form";
    }

    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String updateGroup(@Valid Group group, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            model.addAttribute("subjectsList", subjectRepository.getAllSubjects());
            return "admin/groups/form";
        }

        groupRepository.save(group);

        String message = messageSource.getMessage("admin.messages.groupUpdated", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/groups";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteGroup(long deleteId, RedirectAttributes redirectAttributes, Locale locale) {
        Group group = groupRepository.getGroupById(deleteId);

        try {
            groupRepository.delete(group);
            redirectAttributes.addFlashAttribute("message",
                    messageSource.getMessage("admin.messages.groupDeleted", null, locale));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("admin.messages.groupDeletionError", null, locale));
        }

        return "redirect:/admin/groups";
    }
}
