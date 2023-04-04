package server.api;

import commons.Board;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.BoardRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestBoardRepository implements BoardRepository {

    public final List<Board> boards = new ArrayList<>();

    public List<Board> findAll() {
        return boards;
    }

    @Override
    public List<Board> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Board> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Board> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return boards.size();
    }

    @Override
    public void deleteById(Long id) {
        for(Board b : boards)
            if(b.getId() == id)
                boards.remove(b);
    }

    @Override
    public void delete(Board board) {
        for(Board b : boards)
            if(board.equals(b))
                boards.remove(b);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }


    @Override
    public void deleteAll(Iterable<? extends Board> entities) {
        boards.clear();
    }

    @Override
    public void deleteAll() {
        boards.clear();
    }

    @Override
    public <S extends Board> S save(S entity) {
        boards.add(entity);
        return entity;
    }

    @Override
    public <S extends Board> List<S> saveAll(Iterable<S> entities) {
        Iterator iter = entities.iterator();
        List<S> returnList = new ArrayList<>();
        while(iter.hasNext()){
            S s = (S) iter.next();
            returnList.add(s);
            boards.add(s);
        }
        return returnList;
    }

    @Override
    public Optional<Board> findById(Long id) {
        Board board = null;
        for(Board b : boards){
            if(b.getId() == id)
                board = b;
        }
        return Optional.ofNullable(board);
    }

    @Override
    public boolean existsById(Long id) {
        for(Board b : boards)
            if(b.getId() == id)
                return true;
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Board> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Board> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Board> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Board getOne(Long aLong) {
        return null;
    }

    @Override
    public Board getById(Long id) {
        if(existsById(id)) {
            Board board = null;
            for(Board b : boards)
                if(b.getId() == id)
                    board = b;
            return board;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public <S extends Board> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Board> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Board> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Board> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Board> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Board> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Board, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
