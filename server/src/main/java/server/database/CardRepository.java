package server.database;

import commons.Card;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
public interface CardRepository extends JpaRepository<Card, Long> {
=======
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByCardlistId(Long cardlistId);
    void deleteByCardlistId(Long cardlistId);
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
}
