package schedule.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import schedule.dao.NewsDao;
import schedule.utils.CurrentWeekService;

@Controller
@RequestMapping("/")
public class HomeController {
    private NewsDao newsDao;
    private CurrentWeekService currentWeekService;

    @Autowired
    public HomeController(NewsDao newsDao, CurrentWeekService currentWeekService) {
        this.newsDao = newsDao;
        this.currentWeekService = currentWeekService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("currentTab", "news");
        model.addAttribute("news", newsDao.getAllNews());
        model.addAttribute("currentWeek", currentWeekService.getCurrentWeek());
        return "index";
    }
}
