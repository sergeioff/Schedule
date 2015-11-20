package schedule.web.admin;

import schedule.dao.TypeDao;
import schedule.models.TypeOfPair;
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
@RequestMapping("/admin/types")
public class TypesController {
    @Autowired
    private TypeDao typesRepository;

    @RequestMapping(method = RequestMethod.GET)
    private String index(Model model) {
        model.addAttribute("types", typesRepository.getAllTypes());
        return "admin/types/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String showForm(TypeOfPair typeOfPair, Model model) {
        model.addAttribute("action", "add");
        return "admin/types/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String processForm(@Valid TypeOfPair typeOfPair, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            return "admin/types/form";
        }

        typesRepository.save(typeOfPair);

        redirectAttributes.addFlashAttribute("message", "typeAdded");
        return "redirect:/admin/types";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private String deleteTypeOfPair(long deleteId, RedirectAttributes redirectAttributes) {
        TypeOfPair typeOfPair = typesRepository.getTypeOfPairById(deleteId);
        typesRepository.delete(typeOfPair);
        redirectAttributes.addFlashAttribute("message", "typeDeleted");
        return "redirect:/admin/types";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("action", "edit");
        model.addAttribute("typeOfPair", typesRepository.getTypeOfPairById(id));
        return "admin/types/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEditForm(@Valid TypeOfPair typeOfPair, BindingResult bindingResult,
                                    Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            return "admin/types/form";
        }

        typesRepository.save(typeOfPair);

        redirectAttributes.addFlashAttribute("message", "typeUpdated");
        return "redirect:/admin/types";
    }
}
