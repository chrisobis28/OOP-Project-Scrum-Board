package server.database;

import commons.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
  @Query(value = "SELECT * FROM card WHERE cardlistID = :cardlistId", nativeQuery = true)
  List<Card> findByCardListId(@Param("cardlistId") long cardlistId);
}
