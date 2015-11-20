package schedule.web;

import schedule.dao.GroupDao;
import schedule.dao.NewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {
    private NewsDao newsDao;
    private GroupDao groupDao;

    @Autowired
    public HomeController(NewsDao newsDao, GroupDao groupDao) {
        this.newsDao = newsDao;
        this.groupDao = groupDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("currentTab", "news");
        model.addAttribute("news", newsDao.getAllNews());
        return "index";
    }
}
