package server.database;

import commons.Cardlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardlistRepository extends JpaRepository<Cardlist, Long> {
}
