package jschedule.dao;

import jschedule.models.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TeacherDao extends JpaRepository<Teacher, Long> {
    Teacher getTeacherById(Long id);

    @Query("select t from Teacher t")
    List<Teacher> getAllTeachers();

    @Modifying(clearAutomatically = true)
    @Query("update Teacher t set t.name=:name where t.id=:id")
    void update(@Param("id") Long id, @Param("name") String name);
}
