package schedule.web.admin;

import org.springframework.context.MessageSource;
import schedule.dao.GroupDao;
import schedule.dao.PairDao;
import schedule.dao.TypeDao;
import schedule.models.Group;
import schedule.models.Pair;
import schedule.models.Settings;
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
import java.util.Locale;

@Controller
@RequestMapping("/admin/schedule")
public class PairsController {
    private GroupDao groupsRepository;
    private PairDao pairsRepository;
    private TypeDao typesRepository;
    private MessageSource messageSource;

    @Autowired
    public PairsController(GroupDao groupsRepository, PairDao pairsRepository, TypeDao typesRepository,
                           MessageSource messageSource) {
        this.groupsRepository = groupsRepository;
        this.pairsRepository = pairsRepository;
        this.typesRepository = typesRepository;
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(@CookieValue(value = "group", defaultValue = "-1") long group, Model model) {
        model.addAttribute("groups", groupsRepository.getAllGroups());
        model.addAttribute("currentTab", "schedule");
        Group selectedGroup = groupsRepository.getGroupById(group);

        if (selectedGroup != null) {
            model.addAttribute("selectedGroup",selectedGroup);
            model.addAttribute("pairs", pairsRepository.getPairsByGroup(selectedGroup));
        } else {
            model.addAttribute("selectedGroup", new Group());
            model.addAttribute("selectGroupMessage", true);
        }

        return "admin/pairs/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String selectGroup(Long selectedGroup, HttpServletResponse response) {
        response.addCookie(new Cookie("group", selectedGroup.toString()));
        return "redirect:/admin/schedule";
    }

    @SuppressWarnings("UnusedParameters")
    @RequestMapping(value = "/addPairFor{groupId}", method = RequestMethod.GET)
    public String showForm(@PathVariable long groupId, Pair pair, Model model) {
        model.addAttribute("action", "add");
        model.addAttribute("currentGroup", groupsRepository.getGroupById(groupId));
        fillModelBySettings(model);
        model.addAttribute("types", typesRepository.getAllTypes());

        return "admin/pairs/form";
    }

    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/addPairFor{groupId}", method = RequestMethod.POST)
    public String processForm(@Valid Pair pair, BindingResult bindingResult, Model model,
                              RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            model.addAttribute("currentGroup", pair.getCurrentGroup());
            fillModelBySettings(model);
            model.addAttribute("types", typesRepository.getAllTypes());

            return "admin/pairs/form";
        }

        pairsRepository.save(pair);

        String message = messageSource.getMessage("admin.messages.pairAdded", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/schedule";
    }

    @RequestMapping(value = "/editPair/{pairId}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long pairId, Model model) {
        model.addAttribute("action", "edit");

        Pair pair = pairsRepository.getPairById(pairId);
        model.addAttribute("pair", pair);

        model.addAttribute("currentGroup", pair.getCurrentGroup());
        fillModelBySettings(model);
        model.addAttribute("types", typesRepository.getAllTypes());

        return "admin/pairs/form";
    }

    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/editPair/{id}", method = RequestMethod.POST)
    public String processEditForm(@Valid Pair pair, BindingResult bindingResult, Model model,
                                  RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            model.addAttribute("currentGroup", pair.getCurrentGroup());
            fillModelBySettings(model);
            model.addAttribute("types", typesRepository.getAllTypes());

            return  "admin/pairs/form";
        }

        pairsRepository.save(pair);

        String message = messageSource.getMessage("admin.messages.pairUpdated", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/schedule";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePair(long deleteId, RedirectAttributes redirectAttributes, Locale locale) {
        Pair pair = pairsRepository.getPairById(deleteId);
        pairsRepository.delete(pair);

        String message = messageSource.getMessage("admin.messages.pairDeleted", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

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
