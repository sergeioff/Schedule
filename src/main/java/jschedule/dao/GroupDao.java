package jschedule.dao;

import jschedule.models.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface GroupDao extends JpaRepository<Group, Long> {
    Group getGroupById(Long id);
    @Query("select g from Group g")
    List<Group> getAllGroups();
}
