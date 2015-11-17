package jschedule.web.admin;

import jschedule.dao.NewsDao;
import jschedule.models.News;
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
    public String showForm(News news, Model model) {
        model.addAttribute("actionName", "Add a new news");
        return "admin/news/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processForm(@Valid News news, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Add a new news");
            return "admin/news/form";
        }

        newsDao.save(news);

        redirectAttributes.addFlashAttribute("message", "News added successfully");
        return "redirect:/admin/news";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long id, Model model) {
        News news = newsDao.getNewsById(id);
        model.addAttribute("actionName", "Edit news");
        model.addAttribute("news", news);
        return "admin/news/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditForm(@Valid News news, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionName", "Edit news");
            return "admin/news/form";
        }

        newsDao.save(news);

        redirectAttributes.addFlashAttribute("message", "News updated successfully");
        return "redirect:/admin/news";
    }

    @RequestMapping(value = "/delete")
    public String delete(long deleteId, RedirectAttributes redirectAttributes) {
        News news = newsDao.getNewsById(deleteId);
        newsDao.delete(news);

        redirectAttributes.addFlashAttribute("message", "News deleted successfully");
        return "redirect:/admin/news";
    }
}
