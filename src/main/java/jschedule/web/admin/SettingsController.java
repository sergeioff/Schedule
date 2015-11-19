package jschedule.web.admin;

import jschedule.models.Settings;
import jschedule.models.forms.SettingsForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin/settings")
public class SettingsController {
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("settingsForm", new SettingsForm(Settings.getStartWeek(), Settings.getFinalWeek(),
                Settings.getDaysCount(), Settings.getSubgroupsInGroup(), Settings.getPairsInDay()));
        return "admin/settings/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveSettings(@Valid SettingsForm settingsForm, Errors errors,
                               RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return "admin/settings/index";
        }

        Settings.setSettings(settingsForm.getStartWeek(), settingsForm.getFinalWeek(),
                settingsForm.getDaysCount(), settingsForm.getSubgroupsInGroup(), settingsForm.getPairsInDay());

        redirectAttributes.addFlashAttribute("message", "new settings saved");
        return "redirect:/admin/settings/";
    }
}
