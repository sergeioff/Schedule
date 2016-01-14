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
import schedule.dao.TypeDao;
import schedule.models.TypeOfPair;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/admin/types")
public class TypesController {
    private TypeDao typesRepository;
    private MessageSource messageSource;

    @Autowired
    public TypesController(TypeDao typesRepository, MessageSource messageSource) {
        this.typesRepository = typesRepository;
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    private String index(Model model) {
        model.addAttribute("types", typesRepository.getAllTypes());
        model.addAttribute("currentTab", "types");
        return "admin/types/index";
    }

    @SuppressWarnings("UnusedParameters")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String showForm(TypeOfPair typeOfPair, Model model) {
        model.addAttribute("action", "add");
        return "admin/types/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String processForm(@Valid TypeOfPair typeOfPair, BindingResult bindingResult, Model model,
                               RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            return "admin/types/form";
        }

        typesRepository.save(typeOfPair);

        String message = messageSource.getMessage("admin.messages.typeAdded", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/types";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private String deleteTypeOfPair(long deleteId, RedirectAttributes redirectAttributes, Locale locale) {
        TypeOfPair typeOfPair = typesRepository.getTypeOfPairById(deleteId);
        try {
            typesRepository.delete(typeOfPair);
            redirectAttributes.addFlashAttribute("message",
                    messageSource.getMessage("admin.messages.typeDeleted", null, locale));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("admin.messages.typeDeletionError", null, locale));
        }

        return "redirect:/admin/types";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("action", "edit");
        model.addAttribute("typeOfPair", typesRepository.getTypeOfPairById(id));
        return "admin/types/form";
    }

    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEditForm(@Valid TypeOfPair typeOfPair, BindingResult bindingResult, Model model,
                                   RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            return "admin/types/form";
        }

        typesRepository.save(typeOfPair);

        String message = messageSource.getMessage("admin.messages.typeUpdated", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/types";
    }
}
