package server.api;

import commons.Board;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("boards")
public class BoardController {

    public final List<Board> boards = new ArrayList<>();
    
    @GetMapping("boards/{id}")
    public ResponseEntity<Board> getById(@PathVariable long id) {
        if (id < 0 || boards.size() <= id) {
            return ResponseEntity.badRequest().build();
        }
        int i = Integer.parseInt(Long.toString(id));
        return ResponseEntity.ok(boards.get(i));
    }
    @GetMapping("boards/{name}")
    public ResponseEntity<Board> getByName(@PathVariable("name") String name) {
        for (Board board : boards) {
            if (board.getName().equals(name))
                return ResponseEntity.ok(board);
        }
            return ResponseEntity.badRequest().build();

    }
}
