package server.api;

import commons.Board;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("boards")
public class BoardController {
    public final List<Board> boards = new ArrayList<>();
    public final Map<String, Integer> names = new HashMap<>();
    @GetMapping("boards")
    public ResponseEntity<Board> getById(@PathVariable("id") long id) {
        if (id < 0 || boards.size() <= id) {
            return ResponseEntity.badRequest().build();
        }
        int i = Integer.valueOf(Long.toString(id));
        return ResponseEntity.ok(boards.get(i));
    }
    @GetMapping("boards/{name}")
    public ResponseEntity<Board> getByName(@PathVariable("name") String name) {
        for(int i = 0; i < boards.size(); i++) {
            if (boards.get(i).getName().equals(name))
                return ResponseEntity.ok(boards.get(i));
        }
            return ResponseEntity.badRequest().build();

    }
}
