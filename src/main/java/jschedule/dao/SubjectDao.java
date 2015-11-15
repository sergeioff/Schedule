package jschedule.dao;

import jschedule.models.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SubjectDao extends JpaRepository<Subject, Long> {
    Subject getSubjectById(Long id);
    @Query("select s from Subject s")
    List<Subject> getAllSubjects();
}
