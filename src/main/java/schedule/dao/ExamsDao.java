package schedule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import schedule.models.Exam;
import schedule.models.Group;

import java.util.List;

public interface ExamsDao extends JpaRepository<Exam, Long> {
    Exam getExamById(Long id);
    Exam save(Exam exam);
    void delete(Exam exam);

    @Query("select e from Exam e where e.currentGroup=:group_id")
    List<Exam> getExamsByGroup(@Param("group_id") Group group);
}
