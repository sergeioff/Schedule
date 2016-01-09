package schedule.web.admin;

import org.springframework.context.MessageSource;
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
import java.util.Locale;

@Controller
@RequestMapping(value = "/admin/news")
public class NewsController {
    private NewsDao newsRepository;
    private MessageSource messageSource;

    @Autowired
    public NewsController(NewsDao newsRepository, MessageSource messageSource) {
        this.newsRepository = newsRepository;
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("news", newsRepository.getAllNews());
        model.addAttribute("currentTab", "news");
        return "admin/news/index";
    }

    @SuppressWarnings("UnusedParameters")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showForm(News news, Model model) {
        model.addAttribute("action", "add");
        return "admin/news/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processForm(@Valid News news, BindingResult bindingResult, Model model,
                              RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add");
            return "admin/news/form";
        }

        newsRepository.save(news);

        String message = messageSource.getMessage("admin.messages.newsAdded", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/news";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long id, Model model) {
        News news = newsRepository.getNewsById(id);
        model.addAttribute("action", "edit");
        model.addAttribute("news", news);
        return "admin/news/form";
    }

    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditForm(@Valid News news, BindingResult bindingResult, Model model,
                                  RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            return "admin/news/form";
        }

        newsRepository.save(news);

        String message = messageSource.getMessage("admin.messages.newsUpdated", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/news";
    }

    @RequestMapping(value = "/delete")
    public String delete(long deleteId, RedirectAttributes redirectAttributes, Locale locale) {
        News news = newsRepository.getNewsById(deleteId);
        newsRepository.delete(news);

        String message = messageSource.getMessage("admin.messages.newsDeleted", null, locale);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin/news";
    }
}
