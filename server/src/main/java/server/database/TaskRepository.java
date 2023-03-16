package server.database;

import commons.Task;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
public interface TaskRepository extends JpaRepository<Task, Long> {
=======
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCardId(Long cardId);
    void deleteByCardId(Long cardId);
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
}
