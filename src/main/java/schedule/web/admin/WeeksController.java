package schedule.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import schedule.dao.WeeksDao;
import schedule.models.Week;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/admin/weeks")
public class WeeksController {
    private WeeksDao weeksRepository;
    private MessageSource messageSource;

    @Autowired
    public WeeksController(WeeksDao weeksRepository, MessageSource messageSource) {
        this.weeksRepository = weeksRepository;
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("weeks", weeksRepository.getAllWeeks());
        model.addAttribute("currentTab", "weeks");
        return "admin/weeks/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showForm(Week week, Model model) {
        model.addAttribute("action", "add");
        return "admin/weeks/form";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processForm(@Valid Week week, BindingResult bindingResult, Model model,
                              RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            return "admin/weeks/form";
        }

        weeksRepository.save(week);

        String message = messageSource.getMessage("admin.messages.weekAdded", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/weeks";
    }

    @RequestMapping(value = "editWeek/{weekNum}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable int weekNum, Model model) {
        model.addAttribute("action", "edit");

        Week week = weeksRepository.getWeekByNumber(weekNum);
        model.addAttribute("week", week);

        return "admin/weeks/form";
    }

    @RequestMapping(value = "editWeek/{weekNum}", method = RequestMethod.POST)
    public String processEditForm(@Valid Week week, BindingResult bindingResult, Model model,
                                  RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            return "admin/weeks/form";
        }

        weeksRepository.save(week);

        String message = messageSource.getMessage("admin.messages.weekUpdated", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/weeks/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteWeek(int deleteId, RedirectAttributes redirectAttributes, Locale locale) {
        Week week = weeksRepository.getWeekByNumber(deleteId);
        weeksRepository.delete(week);

        String message = messageSource.getMessage("admin.messages.weekDeleted", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/weeks";
    }
}
