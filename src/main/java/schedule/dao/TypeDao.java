package schedule.dao;

import schedule.models.TypeOfPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TypeDao extends JpaRepository<TypeOfPair, Long> {
    TypeOfPair getTypeOfPairById(Long id);
    TypeOfPair save(TypeOfPair typeOfPair);
    void delete(TypeOfPair typeOfPair);

    @Query("select t from TypeOfPair t")
    List<TypeOfPair> getAllTypes();
}