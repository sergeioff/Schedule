package jschedule.web.admin;

import jschedule.dao.TypeDao;
import jschedule.models.TypeOfPair;
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
    private TypeDao typeDao;

    @RequestMapping(method = RequestMethod.GET)
    private String index(Model model) {
        model.addAttribute("types", typeDao.getAllTypes());
        return "admin/types/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String showForm(TypeOfPair typeOfPair, Model model) {
        model.addAttribute("actionName", "Add a new type");
        return "admin/types/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String processForm(@Valid TypeOfPair typeOfPair, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Add a new type");
            return "admin/types/form";
        }

        typeDao.save(typeOfPair);

        redirectAttributes.addFlashAttribute("message", "Type successfully added");
        return "redirect:/admin/types";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private String deleteTypeOfPair(long deleteId, RedirectAttributes redirectAttributes) {
        TypeOfPair typeOfPair = typeDao.getTypeOfPairById(deleteId);
        typeDao.delete(typeOfPair);
        redirectAttributes.addFlashAttribute("message", "Type successfully deleted");
        return "redirect:/admin/types";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("actionName", "Edit type");
        model.addAttribute("typeOfPair", typeDao.getTypeOfPairById(id));
        return "admin/types/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEditForm(@Valid TypeOfPair typeOfPair, BindingResult bindingResult,
                                    Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Edit type");
            return "admin/types/form";
        }

        typeDao.save(typeOfPair);

        redirectAttributes.addFlashAttribute("message", "Type successfully updated");
        return "redirect:/admin/types";
    }
}
