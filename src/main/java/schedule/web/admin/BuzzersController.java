package schedule.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/admin/buzzers")
public class BuzzersController {
    @Autowired
    private BuzzerDao buzzersRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("buzzers", buzzersRepository.getAllBuzzers());
        model.addAttribute("currentTab", "buzzers");
        return "admin/buzzers/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showForm(BuzzerOnPair buzzerOnPair, Model model) {
        model.addAttribute("action", "add");
        return "admin/buzzers/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processForm(@Valid BuzzerOnPair buzzerOnPair, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            return "admin/buzzers/form";
        }

        buzzersRepository.save(buzzerOnPair);

        redirectAttributes.addFlashAttribute("message", "buzzerAdded");
        return "redirect:/admin/buzzers";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long id, Model model) {
        BuzzerOnPair buzzerOnPair = buzzersRepository.getBuzzerOnPairById(id);
        model.addAttribute("action", "edit");
        model.addAttribute("buzzerOnPair", buzzerOnPair);
        return "admin/buzzers/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditForm(@Valid BuzzerOnPair buzzerOnPair, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            return "admin/buzzers/form";
        }

        buzzersRepository.save(buzzerOnPair);

        redirectAttributes.addFlashAttribute("message", "buzzerUpdated");
        return "redirect:/admin/buzzers";
    }

    @RequestMapping(value = "/delete")
    public String delete(long deleteId, RedirectAttributes redirectAttributes) {
        BuzzerOnPair buzzerOnPair = buzzersRepository.getBuzzerOnPairById(deleteId);
        buzzersRepository.delete(buzzerOnPair);

        redirectAttributes.addFlashAttribute("message", "buzzerDeleted");
        return "redirect:/admin/buzzers";
    }
}
