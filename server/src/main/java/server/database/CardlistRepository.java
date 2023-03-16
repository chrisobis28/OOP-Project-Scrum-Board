package server.database;

import commons.Cardlist;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
public interface CardlistRepository extends JpaRepository<Cardlist, Long> {
=======
import java.util.List;

public interface CardlistRepository extends JpaRepository<Cardlist, Long> {

    List<Cardlist> findByBoardId(Long boardId);
    void deleteByBoardId(Long boardId);
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
}
