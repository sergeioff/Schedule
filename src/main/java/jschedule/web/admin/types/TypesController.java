package jschedule.web.admin.types;

import jschedule.dao.TypeDao;
import jschedule.models.forms.TypeOfPairForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
    private String home(Model model) {
        model.addAttribute("types", typeDao.getAllTypes());
        return "admin/types/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String showAddTypeForm(Model model) {
        model.addAttribute("typeOfPairForm", new TypeOfPairForm());
        return "admin/types/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String addTypeOfPair(@Valid TypeOfPairForm typeOfPairForm, Errors errors, RedirectAttributes model) {
        if (errors.hasErrors()) {
            return "admin/types/add";
        }

        typeDao.save(typeOfPairForm.toTypeOfPair());

        model.addFlashAttribute("message", "Type successfully added");
        return "redirect:/admin/types";
    }

    @RequestMapping(value = "/delete/{id}")
    private String deleteTypeOfPair(@PathVariable long id, RedirectAttributes model) {
        typeDao.delete(id);
        model.addFlashAttribute("message", "Type successfully deleted");
        return "redirect:/admin/types";
    }
}
