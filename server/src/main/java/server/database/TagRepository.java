package server.database;

import commons.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByCardId(Long cardId);
    List<Tag> findByBoardId(Long boardId);
    void deleteByBoardId(Long boardId);
    void deleteBycardId(Long cardId);
}
