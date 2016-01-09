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
import schedule.dao.BuzzerDao;
import schedule.models.BuzzerOnPair;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/admin/buzzers")
public class BuzzersController {
    private BuzzerDao buzzersRepository;
    private MessageSource messageSource;

    @Autowired
    public BuzzersController(BuzzerDao buzzersRepository, MessageSource messageSource) {
        this.buzzersRepository = buzzersRepository;
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("buzzers", buzzersRepository.getAllBuzzers());
        model.addAttribute("currentTab", "buzzers");
        return "admin/buzzers/index";
    }

    @SuppressWarnings("UnusedParameters")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showForm(BuzzerOnPair buzzerOnPair, Model model) {
        model.addAttribute("action", "add");
        return "admin/buzzers/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processForm(@Valid BuzzerOnPair buzzerOnPair, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            return "admin/buzzers/form";
        }

        buzzersRepository.save(buzzerOnPair);

        String message = messageSource.getMessage("admin.messages.buzzerAdded", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/buzzers";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long id, Model model) {
        BuzzerOnPair buzzerOnPair = buzzersRepository.getBuzzerOnPairById(id);
        model.addAttribute("action", "edit");
        model.addAttribute("buzzerOnPair", buzzerOnPair);
        return "admin/buzzers/form";
    }

    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditForm(@Valid BuzzerOnPair buzzerOnPair, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            return "admin/buzzers/form";
        }

        buzzersRepository.save(buzzerOnPair);

        String message = messageSource.getMessage("admin.messages.buzzerUpdated", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/buzzers";
    }

    @RequestMapping(value = "/delete")
    public String delete(long deleteId, RedirectAttributes redirectAttributes, Locale locale) {
        BuzzerOnPair buzzerOnPair = buzzersRepository.getBuzzerOnPairById(deleteId);
        buzzersRepository.delete(buzzerOnPair);

        String message = messageSource.getMessage("admin.messages.buzzerDeleted", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/buzzers";
    }
}
