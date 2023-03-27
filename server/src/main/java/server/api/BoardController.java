package server.api;

import commons.Board;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardRepository repo;
    public final List<Board> boards = new ArrayList<>();

    public BoardController(BoardRepository repo) {
        this.repo = repo;
    }

    /**
     * Finds and returns the board with the id specified as the parameter
     * @param id The id of the board the user wants to see
     * @return returns the specified board
     * Returns a not found error when the index is out of bounds.
     */
    @GetMapping("boards/{id}")
    public ResponseEntity<Board> getById(@PathVariable long id) {
        if (id < 0 || boards.size() <= id) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        int i = Integer.parseInt(Long.toString(id));
        return ResponseEntity.ok(boards.get(i));
    }

    /**
     * Finds and returns the board with the name specified as the parameter
     * @param name The name of the board the user wants to see
     * @return returns the specified board
     * Returns a not found error when the board cannot be found
     */
    @GetMapping("boards/{name}")
    public ResponseEntity<Board> getByName(@PathVariable("name") String name) {
        for (Board board : boards) {
            if (board.getBoardName().equals(name))
                return ResponseEntity.ok(board);
        }
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @PostMapping(path = {"/edit", "/edit/"})
    public ResponseEntity<Board> edit(@RequestBody Board board) {
        if (board.getId() < 0 || !repo.existsById(board.getId())) {
            return ResponseEntity.badRequest().build();
        }

        repo.save(board);
        return ResponseEntity.ok(board);
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Board> add(@RequestBody Board board) {

        if (board.getBoardName() == null || board.getBoardName().equals("")){
            return ResponseEntity.badRequest().build();
        }

        boards.add(board);
        Board saved = repo.save(board);
        return ResponseEntity.ok(saved);
    }

}
