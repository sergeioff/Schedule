package jschedule.dao;

import jschedule.models.domain.TypeOfPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TypeDao extends JpaRepository<TypeOfPair, Long> {
    TypeOfPair getTypeOfPairById(Long id);
    TypeOfPair save(TypeOfPair typeOfPair);

    @Query("select t from TypeOfPair t")
    List<TypeOfPair> getAllTypes();

    @Modifying(clearAutomatically = true)
    @Query("update TypeOfPair t set t.name=:name where t.id=:id")
    void update(@Param("id") Long id, @Param("name") String name);
}
