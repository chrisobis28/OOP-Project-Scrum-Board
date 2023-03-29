package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;

@Service
public class BoardService {

    private final BoardRepository repo;

    @Autowired
    public BoardService(BoardRepository repo) {
        this.repo = repo;
    }
}
