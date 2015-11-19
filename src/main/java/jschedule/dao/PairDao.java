package jschedule.dao;

import jschedule.models.Group;
import jschedule.models.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PairDao extends JpaRepository<Pair, Long> {
    Pair getPairById(Long id);
    Pair save(Pair pair);
    void delete(Pair pair);

    @Query("select p from Pair p")
    List<Pair> getAllPairs(Pair pair);

    @Query("select p from Pair p where p.currentGroup=:group_id")
    List<Pair> getPairsByGroup(@Param("group_id") Group group_id);

    @Query("select p from Pair p where p.currentGroup=:group_id and p.week=:week and p.day=:day and " +
            "(p.subGroup=:subgroup or p.subGroup = 0) order by p.numberInDay")
    List<Pair> getPairsByGroupSubgroupAndWeekAndDay(@Param("group_id") long group_id,
                                                    @Param("subgroup") long subgroup,
                                                    @Param("week") long week,
                                                    @Param("day") long day);
}
