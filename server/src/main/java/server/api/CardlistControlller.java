package server.api;

import commons.Cardlist;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.CardlistRepository;

import java.util.List;

@RestController
@RequestMapping("/api/cardlist")
public class CardlistControlller {
    private final CardlistRepository repo;

    public CardlistControlller(CardlistRepository repo) {
        this.repo = repo;
    }


    @GetMapping(path = { "", "/" })
    public List<Cardlist> getAll() {
        return repo.findAll();
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Cardlist> add(@RequestBody Cardlist cardlist) {

        if (isNullOrEmpty(cardlist.cardlistName)) {
            return ResponseEntity.badRequest().build();
        }

        Cardlist saved = repo.save(cardlist);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
