package schedule.web.admin;

import schedule.dao.NewsDao;
import schedule.models.News;
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
    private NewsDao newsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("news", newsRepository.getAllNews());
        model.addAttribute("currentTab", "news");
        return "admin/news/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showForm(News news, Model model) {
        model.addAttribute("action", "add");
        return "admin/news/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processForm(@Valid News news, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            return "admin/news/form";
        }

        newsRepository.save(news);

        redirectAttributes.addFlashAttribute("message", "newsAdded");
        return "redirect:/admin/news";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long id, Model model) {
        News news = newsRepository.getNewsById(id);
        model.addAttribute("action", "edit");
        model.addAttribute("news", news);
        return "admin/news/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditForm(@Valid News news, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            return "admin/news/form";
        }

        newsRepository.save(news);

        redirectAttributes.addFlashAttribute("message", "newsUpdated");
        return "redirect:/admin/news";
    }

    @RequestMapping(value = "/delete")
    public String delete(long deleteId, RedirectAttributes redirectAttributes) {
        News news = newsRepository.getNewsById(deleteId);
        newsRepository.delete(news);

        redirectAttributes.addFlashAttribute("message", "newsDeleted");
        return "redirect:/admin/news";
    }
}
