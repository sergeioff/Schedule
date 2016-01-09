package schedule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import schedule.models.Teacher;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TeacherDao extends JpaRepository<Teacher, Long> {
    Teacher getTeacherById(Long id);
    Teacher save(Teacher teacher);
    void delete(Teacher teacher);

    @Query("select t from Teacher t")
    List<Teacher> getAllTeachers();
}
