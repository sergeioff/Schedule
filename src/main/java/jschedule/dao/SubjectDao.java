package jschedule.dao;

import jschedule.models.domain.Subject;
import jschedule.models.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SubjectDao extends JpaRepository<Subject, Long> {
    Subject getSubjectById(Long id);

    @Query("select s from Subject s")
    List<Subject> getAllSubjects();

    @Modifying(clearAutomatically = true)
    @Query("update Subject s set s.name=:name, s.teacher=:teacher where s.id=:id")
    void update(@Param("id") Long id, @Param("name") String name, @Param("teacher") Teacher teacher);
}
