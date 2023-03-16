package server.database;

import commons.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
public interface TagRepository extends JpaRepository<Tag, Long> {
=======
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByCardId(Long cardId);
    List<Tag> findByBoardId(Long boardId);
    void deleteByBoardId(Long boardId);
    void deleteBycardId(Long cardId);
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
}
