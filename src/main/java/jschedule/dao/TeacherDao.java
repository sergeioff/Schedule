package jschedule.dao;

import jschedule.models.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TeacherDao extends JpaRepository<Teacher, Long> {
    Teacher getTeacherById(Long id);
    @Query("select t from Teacher t")
    List<Teacher> getAllTeachers();
}
