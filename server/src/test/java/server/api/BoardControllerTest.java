package server.api;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class BoardControllerTest {
    private BoardRepository repo;
    private BoardController boardController;

    @BeforeEach
    public void setup() {
        repo = new BoardRepository();
        boardController = new BoardController(repo);
    }
@Test

}