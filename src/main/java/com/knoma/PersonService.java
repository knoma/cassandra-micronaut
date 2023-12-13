package com.knoma;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.knoma.pojo.Person;
import com.knoma.pojo.PersonDAO;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class PersonService {

    private final PersonDAO personDAO;

    @Inject
    public PersonService(CqlSession session) {
        PersonMapper personMapper = new PersonMapperBuilder(session).build();
        this.personDAO = personMapper.personDao(CqlIdentifier.fromCql("cass_drop"));
    }

    public Mono<Person> getById(UUID personId) {
        return Mono.fromCompletionStage(() -> personDAO.getById(personId));
    }

    public Mono<Void> delete(UUID personId) {
        return Mono.fromRunnable(() -> personDAO.delete(personId));
    }

    public Mono<Long> getCount() {
        return Mono.fromCompletionStage(personDAO.getCount());
    }

    public Flux<Person> getAll() {
        return Mono.fromCompletionStage(personDAO.getAll())
                .flatMapMany(iterable -> Flux.fromIterable(iterable.currentPage()));
    }

    public Mono<Void> save(Person person) {
        return Mono.fromCompletionStage(() -> personDAO.saveAsync(person)).then();
    }
}
