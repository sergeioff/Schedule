package schedule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import schedule.models.Group;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface GroupDao extends JpaRepository<Group, Long> {
    Group getGroupById(Long id);
    Group save(Group group);
    void delete(Group group);

    @Query("select g from Group g")
    List<Group> getAllGroups();
}
