package jschedule.web;

import jschedule.dao.GroupDao;
import jschedule.dao.PairDao;
import jschedule.models.Pair;
import jschedule.models.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/forStudents")
public class ScheduleController {
    private PairDao pairsRepository;
    private GroupDao groupsRepository;

    @Autowired
    public ScheduleController(PairDao pairsRepository, GroupDao groupsRepository) {
        this.pairsRepository = pairsRepository;
        this.groupsRepository = groupsRepository;
    }

    @RequestMapping(value = "/{groupId}/{subgroup}/{week}", method = RequestMethod.GET)
    public String showScheduleForGroupBySubgroupAndWeek(@PathVariable long groupId, @PathVariable long subgroup,
                                                        @PathVariable long week, Model model) {
        long days = Settings.getDaysCount();
        HashMap<Long, List<Pair>> schedule = new HashMap<Long, List<Pair>>();

        for (long day = 1; day <= days; day++) {
            schedule.put(day, pairsRepository.getPairsByGroupSubgroupWeekAndDay(
                    groupsRepository.getGroupById(groupId), subgroup, week, day));
        }

        model.addAttribute("schedule", schedule.entrySet());

        model.addAttribute("groups", groupsRepository.getAllGroups());
        model.addAttribute("subgroupsCount", Settings.getSubgroupsInGroup());
        model.addAttribute("startWeek", Settings.getStartWeek());
        model.addAttribute("finalWeek", Settings.getFinalWeek());

        return "scheduleForStudents";
    }
}
