package jschedule.web.admin;

import jschedule.dao.GroupDao;
import jschedule.dao.PairDao;
import jschedule.dao.TypeDao;
import jschedule.models.Group;
import jschedule.models.Pair;
import jschedule.models.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/schedule")
public class PairsController {
    private GroupDao groupsRepository;
    private PairDao pairsRepository;
    private TypeDao typesRepository;

    @Autowired
    public PairsController(GroupDao groupsRepository, PairDao pairsRepository, TypeDao typesRepository) {
        this.groupsRepository = groupsRepository;
        this.pairsRepository = pairsRepository;
        this.typesRepository = typesRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(@CookieValue(value = "group", defaultValue = "-1") long group, Model model) {
        model.addAttribute("groups", groupsRepository.getAllGroups());
        Group selectedGroup = groupsRepository.getGroupById(group);

        if (selectedGroup != null) {
            model.addAttribute("selectedGroup",selectedGroup);
            model.addAttribute("pairs", pairsRepository.getPairsByGroup(selectedGroup));
        } else {
            model.addAttribute("selectedGroup", new Group());
            model.addAttribute("selectGroupMessage", "Select a group");
        }

        return "admin/pairs/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String selectGroup(Long selectedGroup, HttpServletResponse response) {
        response.addCookie(new Cookie("group", selectedGroup.toString()));
        return "redirect:/admin/schedule";
    }

    @RequestMapping(value = "/addPairFor{groupId}", method = RequestMethod.GET)
    public String showForm(@PathVariable long groupId, Pair pair, Model model) {
        model.addAttribute("actionName", "Add a new pair");
        model.addAttribute("currentGroup", groupsRepository.getGroupById(groupId));
        model.addAttribute("subgroupsCount", Settings.getSubgroupsInGroup());
        model.addAttribute("startWeek", Settings.getStartWeek());
        model.addAttribute("finalWeek", Settings.getFinalWeek());
        model.addAttribute("daysCount", Settings.getDaysCount());
        model.addAttribute("pairsInDay", Settings.getPairsInDay());
        model.addAttribute("types", typesRepository.getAllTypes());

        return "admin/pairs/form";
    }

    @RequestMapping(value = "/addPairFor{groupId}", method = RequestMethod.POST)
    public String processForm(@Valid Pair pair, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Add a new pair");
            model.addAttribute("currentGroup", pair.getCurrentGroup());
            fillModelBySettings(model);
            model.addAttribute("types", typesRepository.getAllTypes());

            return "admin/pairs/form";
        }

        pairsRepository.save(pair);
        redirectAttributes.addFlashAttribute("message", "Pair added successfully");
        return "redirect:/admin/schedule";
    }

    @RequestMapping(value = "/editPair/{pairId}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long pairId, Model model) {
        model.addAttribute("actionName", "Edit pair");

        Pair pair = pairsRepository.getPairById(pairId);
        model.addAttribute("pair", pair);

        model.addAttribute("currentGroup", pair.getCurrentGroup());
        fillModelBySettings(model);
        model.addAttribute("types", typesRepository.getAllTypes());

        return "admin/pairs/form";
    }

    @RequestMapping(value = "/editPair/{id}", method = RequestMethod.POST)
    public String processEditForm(@Valid Pair pair, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Edit pair");
            model.addAttribute("currentGroup", pair.getCurrentGroup());
            fillModelBySettings(model);
            model.addAttribute("types", typesRepository.getAllTypes());

            return  "admin/pairs/form";
        }

        pairsRepository.save(pair);
        redirectAttributes.addFlashAttribute("message", "Pair updated successfully");
        return "redirect:/admin/schedule";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePair(long deleteId, RedirectAttributes redirectAttributes) {
        Pair pair = pairsRepository.getPairById(deleteId);
        pairsRepository.delete(pair);
        redirectAttributes.addFlashAttribute("message", "Pair deleted successfully");
        return "redirect:/admin/schedule";
    }

    private static void fillModelBySettings(Model model) {
        model.addAttribute("subgroupsCount", Settings.getSubgroupsInGroup());
        model.addAttribute("startWeek", Settings.getStartWeek());
        model.addAttribute("finalWeek", Settings.getFinalWeek());
        model.addAttribute("daysCount", Settings.getDaysCount());
        model.addAttribute("pairsInDay", Settings.getPairsInDay());
    }
}
