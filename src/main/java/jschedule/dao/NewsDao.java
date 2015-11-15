package jschedule.dao;

import jschedule.models.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface NewsDao extends JpaRepository<News, Long> {
    News getNewsById(Long id);
    News save(News news);
    News deleteNewsById(Long id);

    @Query("select n from News n order by n.date desc")
    List<News> getAllNews();
}
