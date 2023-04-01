package server.api;

import commons.Cardlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.CardlistRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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

    //Event listeners for other endpoints to trigger an update.
    private Map<Object, Consumer<Cardlist>> listeners = new HashMap<>();

    /**
     * Long-polling implementation for a cardlist.
     * @return A Response which is either contains a cardlist or 204 no content status.
     */
    @GetMapping(path = {"/update"})
    public DeferredResult<ResponseEntity<Cardlist>> getUpdates() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Cardlist>>(1024L, noContent);

        var key = new Object();
        listeners.put(key, list -> {
            res.setResult(ResponseEntity.ok(list));
        });

        res.onCompletion(() -> {
            listeners.remove(key);
        });
        return res;
    }

    /**
     * Mapping for GET requests targeted at card lists with a
     *  certain board id that returns the card lists
     *  of that board in the repository.
     *
     * @return List of all card lists in the repository for a board.
     */
    @GetMapping(path = {"/{id}"})
    public List<Cardlist> getAllByBoardId(@PathVariable("id") long id) {
        return repo.findByBoardId(id);
    }

    /**
     * Mapping for POST requests targeted at card lists that adds a specific card list.
     *
     * @param cardlist Card list to be added to the repository.
     * @return Response status of the http request.
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Cardlist> add(@RequestBody Cardlist cardlist) {

        if (isNullOrEmpty(cardlist.getCardlistName())) {
            return ResponseEntity.badRequest().build();
        }

        listeners.forEach((key, listener) -> listener.accept(cardlist));

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
     * Mapping for post requests looking to update an already existing list.
     *
     * @param cardlist The newest version of the list.
     * @return Response status of the http request.
     */
    @PostMapping(path = {"/edit", "/edit/"})
    public ResponseEntity<Cardlist> edit(@RequestBody Cardlist cardlist) {
        if (cardlist.getId() < 0 || !repo.existsById(cardlist.getId())) {
            return ResponseEntity.badRequest().build();
        }

        listeners.forEach((key, listener) -> listener.accept(cardlist));

        repo.save(cardlist);
        return ResponseEntity.ok(cardlist);
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

    /**
     * An endpoint for clearing all of the boards in the repo
     * (can only be done manually)
     */
    @GetMapping(path = {"/clear"})
    public void clear() {
        repo.deleteAll();
    }
}
