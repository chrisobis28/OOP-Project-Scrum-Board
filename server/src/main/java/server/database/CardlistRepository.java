package server.database;

import commons.Cardlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardlistRepository extends JpaRepository<Cardlist, Long> {

    List<Cardlist> findByBoardId(Long boardId);
    void deleteByBoardId(Long boardId);
}
