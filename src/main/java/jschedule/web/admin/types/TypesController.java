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
    private String addTypeOfPair(@Valid TypeOfPairForm typeOfPairForm, Errors errors,
                                 RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return "admin/types/add";
        }

        typeDao.save(typeOfPairForm.toTypeOfPair());

        redirectAttributes.addFlashAttribute("message", "Type successfully added");
        return "redirect:/admin/types";
    }

    @RequestMapping(value = "/delete/{id}")
    private String deleteTypeOfPair(@PathVariable long id, RedirectAttributes redirectAttributes) {
        typeDao.delete(id);
        redirectAttributes.addFlashAttribute("message", "Type successfully deleted");
        return "redirect:/admin/types";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    private String showEditForm(@PathVariable long id, Model model) {
        TypeOfPairForm form = new TypeOfPairForm();
        form.setName(typeDao.getTypeOfPairById(id).getName());

        model.addAttribute("typeOfPairForm", form);
        model.addAttribute("id", id);
        return "admin/types/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String processEditForm(@PathVariable long id, @Valid TypeOfPairForm form,
                                     Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addAttribute("id", id);
            return "admin/types/edit";
        }

        typeDao.update(id, form.getName());
        redirectAttributes.addFlashAttribute("message", "Type successfully updated");
        return "redirect:/admin/types";
    }
}
