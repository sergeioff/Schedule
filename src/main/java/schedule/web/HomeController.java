package schedule.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import schedule.dao.NewsDao;

@Controller
@RequestMapping("/")
public class HomeController {
    private NewsDao newsDao;

    @Autowired
    public HomeController(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("currentTab", "news");
        model.addAttribute("news", newsDao.getAllNews());
        return "index";
    }
}
