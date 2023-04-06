package server.api;


import commons.Board;
import commons.Task;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.TaskRepository;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo){
      this.repo = repo;
    }

    @GetMapping(path = {"","/"})
    public List<Task> getAll(){
      return repo.findAll();
    }


  @GetMapping("/{id}")
  public ResponseEntity<Task> getById(@PathVariable("id") long id) {
    if (id < 0 || !repo.existsById(id)) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(repo.findById(id).get());
  }

  @PostMapping(path = { "", "/" })
  public ResponseEntity<Task> add(@RequestBody Task task) {

    if (task == null || task.description == null){
      return ResponseEntity.badRequest().build();
    }

    Task saved = repo.save(task);
    return ResponseEntity.ok(saved);
  }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Task> delete(@PathVariable("id") long id) {
        if (id<0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        Task deleted = repo.findById(id).get();
        repo.delete(deleted);
        return ResponseEntity.ok(deleted);
    }

}
