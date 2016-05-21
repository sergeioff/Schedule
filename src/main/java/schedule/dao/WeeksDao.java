package schedule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import schedule.models.Week;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface WeeksDao extends JpaRepository<Week, Integer> {
    Week getWeekByNumber(Integer number);
    Week save(Week week);
    void delete(Week week);

    @Query("select w from Week w")
    List<Week> getAllWeeks();
}
