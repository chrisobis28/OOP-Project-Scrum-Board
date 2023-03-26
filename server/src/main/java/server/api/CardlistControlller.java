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

    /**
     * Constructor of the repository.
     *
     * @param repo Repository instance.
     */
    public CardlistControlller(CardlistRepository repo) {
        this.repo = repo;
    }

    /**
     * Mapping for GET requests targeted at card lists that return
     *  the card lists in the repository.
     *
     * @return List of all card lists in the repository.
     */
    @GetMapping(path = { "", "/" })
    public List<Cardlist> getAll() {
        return repo.findAll();
    }

    /**
     * Mapping for POST requests targeted at card lists that adds a specific card list.
     *
     * @param cardlist Card list to be added to the repository.
     * @return Response status of the http request.
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Cardlist> add(@RequestBody Cardlist cardlist) {

        if (isNullOrEmpty(cardlist.cardlistName)) {
            return ResponseEntity.badRequest().build();
        }

        Cardlist saved = repo.save(cardlist);
        return ResponseEntity.ok(saved);
    }

    /**
     * Mapping for delete requests using the id included as a path variable to
     *  find and delete a certain card list.
     *
     * @param id Id of the card list to be deleted.
     * @return Response status of the http request.
     */
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Cardlist> delete(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        Cardlist deleted = repo.getById(id);
        repo.delete(deleted);
        return ResponseEntity.ok(deleted);
    }

    /**
     * Method that checks if a string is either null of empty.
     *
     * @param s String to be checked.
     * @return  True if string is null or empty
     *          False if string is another value.
     */
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
