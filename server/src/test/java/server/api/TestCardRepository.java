package server.api;

import commons.Card;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.CardRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestCardRepository implements CardRepository {
    public final List<Card> cards = new ArrayList<>();

    public List<Card> findAll() {
        return cards;
    }

    @Override
    public List<Card> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Card> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Card> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public List<Card> findByCardListId(long id) {return null;}

    @Override
    public long count() {
        return cards.size();
    }

    @Override
    public void deleteById(Long id) {
        for(Card b : cards)
            if(b.getId() == id)
                cards.remove(b);
    }

    @Override
    public void delete(Card card) {
        for(Card b : cards)
            if(card.equals(b))
                cards.remove(b);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }


    @Override
    public void deleteAll(Iterable<? extends Card> entities) {
        cards.clear();
    }

    @Override
    public void deleteAll() {
        cards.clear();
    }

    @Override
    public <S extends Card> S save(S entity) {
        cards.add(entity);
        return entity;
    }

    @Override
    public <S extends Card> List<S> saveAll(Iterable<S> entities) {
        Iterator iter = entities.iterator();
        List<S> returnList = new ArrayList<>();
        while(iter.hasNext()){
            S s = (S) iter.next();
            returnList.add(s);
            cards.add(s);
        }
        return returnList;
    }

    @Override
    public Optional<Card> findById(Long id) {
        Card card = null;
        for(Card b : cards){
            if(b.getId() == id)
                card = b;
        }
        return Optional.ofNullable(card);
    }

    @Override
    public boolean existsById(Long id) {
        for(Card b : cards)
            if(b.getId() == id)
                return true;
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Card> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Card> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Card> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Card getOne(Long aLong) {
        return null;
    }

    @Override
    public Card getById(Long id) {
        if(existsById(id)) {
            Card card = null;
            for(Card b : cards)
                if(b.getId() == id)
                    card = b;
            return card;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public <S extends Card> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Card> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Card> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Card> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Card> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Card> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Card, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}


