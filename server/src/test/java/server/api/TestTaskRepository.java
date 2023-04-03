package server.api;

import commons.Task;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.TaskRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestTaskRepository implements TaskRepository {
    public final List<Task> tasks = new ArrayList<>();

    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public List<Task> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Task> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return tasks.size();
    }

    @Override
    public void deleteById(Long id) {
        for(Task b : tasks)
            if(b.getId() == id)
                tasks.remove(b);
    }

    @Override
    public void delete(Task task) {
        for(Task b : tasks)
            if(task.equals(b))
                tasks.remove(b);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }


    @Override
    public void deleteAll(Iterable<? extends Task> entities) {
        tasks.clear();
    }

    @Override
    public void deleteAll() {
        tasks.clear();
    }

    @Override
    public <S extends Task> S save(S entity) {
        tasks.add(entity);
        return entity;
    }

    @Override
    public <S extends Task> List<S> saveAll(Iterable<S> entities) {
        Iterator iter = entities.iterator();
        List<S> returnList = new ArrayList<>();
        while(iter.hasNext()){
            S s = (S) iter.next();
            returnList.add(s);
            tasks.add(s);
        }
        return returnList;
    }

    @Override
    public Optional<Task> findById(Long id) {
        Task task = null;
        for(Task b : tasks){
            if(b.getId() == id)
                task = b;
        }
        return Optional.ofNullable(task);
    }

    @Override
    public boolean existsById(Long id) {
        for(Task b : tasks)
            if(b.getId() == id)
                return true;
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Task> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Task> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Task> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Task getOne(Long aLong) {
        return null;
    }

    @Override
    public Task getById(Long id) {
        if(existsById(id)) {
            Task task = null;
            for(Task b : tasks)
                if(b.getId() == id)
                    task = b;
            return task;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public <S extends Task> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Task> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Task> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Task> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Task> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Task> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Task, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Task> findByCardId(Long cardId) {
        return null;
    }

    @Override
    public void deleteByCardId(Long cardId) {

    }
}


