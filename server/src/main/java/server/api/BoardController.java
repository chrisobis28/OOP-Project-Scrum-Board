package server.api;

import commons.Board;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardRepository repo;

    /**
     * Constructor for the controller.
     *
     * @param repo Repository instance
     */
    public BoardController(BoardRepository repo) {
        this.repo = repo;
    }

    /**
     * Mapping for GET requests that return a board with a given id.
     *
     * @param id The id of the board the user wants to see
     * @return The response status of the request
     */
    @GetMapping("/{id}")
    public ResponseEntity<Board> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    /**
     * Mapping for GET requests that returns the list of all currently created boards.
     *
     * @return List of currently created boards.
     */
    @GetMapping(path = {"", "/"})
    public List<Board> getAll() {
        return repo.findAll();
    }
    /*/**
     * Mapping for GET requests that returns a board with a given name.
     *
     * @param name The name of the board the user wants to see
     * @return The response status of the request
     */
    /*@GetMapping("/{name}")
    public ResponseEntity<Board> getByName(@PathVariable("name") String name) {
        for (Board board : repo.findAll()) {
            if (board.getBoardName().equals(name))
                return ResponseEntity.ok(board);
        }

        return ResponseEntity.badRequest().build();
    }*/

    /**
     * Mapping for POST requests that adds a Board to the repository.
     *
     * @param board The board to be added to the repository
     * @return Response status of the request
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Board> add(@RequestBody Board board) {
        if (board.getBoardName()==null||board.getBoardName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Board saved = repo.save(board);
        return ResponseEntity.ok(saved);
    }

    /**
     * Mapping for delete requests that deletes a board with the given id
     * @param id id of the board to be deleted
     * @return Response status of the request
     */
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Board> delete(@PathVariable("id") long id) {
        if (id<0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        Board deleted = repo.findById(id).get();
        repo.delete(deleted);
        return ResponseEntity.ok(deleted);
    }

    @PostMapping(path = {"/edit", "/edit/"})
    public ResponseEntity<Board> edit(@RequestBody Board board) {
        if (board.getId() < 0 || !repo.existsById(board.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Board saved = repo.save(board);
        return ResponseEntity.ok(saved);
    }

    /**
     * An endpoint for clearing all of the boards in the repo
     * (can only be done manually)
     */
    @GetMapping(path = {"/clear"})
    public void clear() {
        repo.deleteAll();
    }
}
