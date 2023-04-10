package server.database;

import commons.Cardlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardlistRepository extends JpaRepository<Cardlist, Long> {

    //Query to get the card lists with board id.
    @Query(value = "SELECT * FROM cardlist WHERE boardid = :boardID", nativeQuery = true)
    List<Cardlist> findByBoardId(@Param("boardID") long boardID);

}
