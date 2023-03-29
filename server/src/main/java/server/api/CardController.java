package server.api;

import commons.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardRepository repo;

    public CardController(CardRepository repo){
        this.repo = repo;
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Card> delete(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        Card deleted = repo.getById(id);
        repo.delete(deleted);
        return ResponseEntity.ok(deleted);
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Card> add(@RequestBody Card card) {

        if (card.getCardName() == null) {
            return ResponseEntity.badRequest().build();
        }

        Card saved = repo.save(card);
        return ResponseEntity.ok(saved);
    }

    @PostMapping(path = {"/edit", "/edit/"})
    public ResponseEntity<Card> edit(@RequestBody Card card) {
        if (card.getId() < 0 || !repo.existsById(card.getId())) {
            return ResponseEntity.badRequest().build();
        }

        repo.save(card);
        return ResponseEntity.ok(card);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }
}
