package server.api;

import commons.Cardlist;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.CardlistRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestCardlistRepository implements CardlistRepository {
    public final List<Cardlist> cardlists = new ArrayList<>();

    public List<Cardlist> findAll() {
        return cardlists;
    }

    @Override
    public List<Cardlist> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Cardlist> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Cardlist> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return cardlists.size();
    }

    @Override
    public void deleteById(Long id) {
        for(Cardlist b : cardlists)
            if(b.getId() == id)
                cardlists.remove(b);
    }

    @Override
    public void delete(Cardlist cardlist) {
        for(Cardlist b : cardlists)
            if(cardlist.equals(b))
                cardlists.remove(b);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }


    @Override
    public void deleteAll(Iterable<? extends Cardlist> entities) {
        cardlists.clear();
    }

    @Override
    public void deleteAll() {
        cardlists.clear();
    }

    @Override
    public <S extends Cardlist> S save(S entity) {
        cardlists.add(entity);
        return entity;
    }

    @Override
    public <S extends Cardlist> List<S> saveAll(Iterable<S> entities) {
        Iterator iter = entities.iterator();
        List<S> returnList = new ArrayList<>();
        while(iter.hasNext()){
            S s = (S) iter.next();
            returnList.add(s);
            cardlists.add(s);
        }
        return returnList;
    }

    @Override
    public Optional<Cardlist> findById(Long id) {
        Cardlist cardlist = null;
        for(Cardlist b : cardlists){
            if(b.getId() == id)
                cardlist = b;
        }
        return Optional.ofNullable(cardlist);
    }

    @Override
    public boolean existsById(Long id) {
        for(Cardlist b : cardlists)
            if(b.getId() == id)
                return true;
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Cardlist> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Cardlist> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Cardlist> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Cardlist getOne(Long aLong) {
        return null;
    }

    @Override
    public Cardlist getById(Long id) {
        if(existsById(id)) {
            Cardlist cardlist = null;
            for(Cardlist b : cardlists)
                if(b.getId() == id)
                    cardlist = b;
            return cardlist;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public <S extends Cardlist> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Cardlist> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Cardlist> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Cardlist> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Cardlist> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Cardlist> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Cardlist, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Cardlist> findByBoardId(long cardlistId) {
        return null;
    }

    @Override
    public void deleteByBoardId(long cardlistId) {

    }
}
