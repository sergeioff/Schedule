package schedule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import schedule.models.BuzzerOnPair;

import java.util.List;

public interface BuzzerDao extends JpaRepository<BuzzerOnPair, Long> {
    BuzzerOnPair getBuzzerOnPairById(Long id);
    BuzzerOnPair save(BuzzerOnPair buzzer);
    void delete(BuzzerOnPair buzzer);

    @Query("select b from BuzzerOnPair b")
    List<BuzzerOnPair> getAllBuzzers();
}
