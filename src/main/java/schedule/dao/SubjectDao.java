package schedule.dao;

import schedule.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SubjectDao extends JpaRepository<Subject, Long> {
    Subject getSubjectById(Long id);
    Subject save(Subject subject);
    void delete(Subject subject);

    @Query("select s from Subject s")
    List<Subject> getAllSubjects();
}
