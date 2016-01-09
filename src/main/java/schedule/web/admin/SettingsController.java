package schedule.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import schedule.models.Settings;
import schedule.models.forms.SettingsForm;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping(value = "/admin/settings")
public class SettingsController {
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("settingsForm", new SettingsForm(Settings.getStartWeek(), Settings.getFinalWeek(),
                Settings.getDaysCount(), Settings.getSubgroupsInGroup(), Settings.getPairsInDay()));
        model.addAttribute("currentTab", "settings");
        return "admin/settings/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveSettings(@Valid SettingsForm settingsForm, Errors errors,
                               RedirectAttributes redirectAttributes, Locale locale) {
        if (errors.hasErrors()) {
            return "admin/settings/index";
        }

        Settings.setSettings(settingsForm.getStartWeek(), settingsForm.getFinalWeek(),
                settingsForm.getDaysCount(), settingsForm.getSubgroupsInGroup(), settingsForm.getPairsInDay());

        String message = messageSource.getMessage("admin.messages.settingsUpdated", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/settings/";
    }
}
