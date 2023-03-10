package server.database;

import commons.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByCardId(Long cardId);
    List<Tag> findByBoardId(Long boardId);
    void deleteByBoardId(Long boardId);
    void deleteBycardId(Long cardId);
}
