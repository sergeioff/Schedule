package jschedule.dao;

import jschedule.models.domain.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PairDao extends JpaRepository<Pair, Long> {
    Pair getPairById(Long id);
    Pair save(Pair pair);

    @Query("select p from Pair p")
    List<Pair> getAllPairs(Pair pair);

    //TODO: write query
    //List<Pair> getPairsForGroupSubgroupAndWeek(int group_id, int subgroup, int week);
}
