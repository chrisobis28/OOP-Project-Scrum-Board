package server.api;

import commons.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardRepository repo;
    private SimpMessagingTemplate messages;

    public CardController(CardRepository repo, SimpMessagingTemplate messages){
        this.repo = repo;
        this.messages = messages;
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

        messages.convertAndSend("/topic/cards", card);
        Card saved = repo.save(card);
        return ResponseEntity.ok(saved);
    }

    @PostMapping(path = {"/edit", "/edit/"})
    public ResponseEntity<Card> edit(@RequestBody Card card) {
        if (card.getId() < 0 || !repo.existsById(card.getId())) {
            return ResponseEntity.badRequest().build();
        }

        repo.save(card);
        messages.convertAndSend("/topic", card);
        return ResponseEntity.ok(card);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @GetMapping(path = {"/clear"})
    public void clear() {
        repo.deleteAll();
    }

    @GetMapping(path = {"/all/{id}"})
    public List<Card> getAllByCardListId(@PathVariable("id") long id) {
        return repo.findByCardListId(id);
    }
}
