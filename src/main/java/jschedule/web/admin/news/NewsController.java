package jschedule.web.admin.news;

import jschedule.dao.NewsDao;
import jschedule.models.forms.NewsForm;
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
@RequestMapping(value = "/admin/news")
public class NewsController {

    @Autowired
    private NewsDao newsDao;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("news", newsDao.getAllNews());
        return "admin/news/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("newsForm", new NewsForm());
        return "admin/news/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@Valid NewsForm form, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return "admin/news/add";
        }

        newsDao.save(form.toNews());

        redirectAttributes.addFlashAttribute("message", "news added");
        return "redirect:/admin/news";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        newsDao.delete(id);
        redirectAttributes.addFlashAttribute("message", "news deleted");
        return "redirect:/admin/news";
    }
}
