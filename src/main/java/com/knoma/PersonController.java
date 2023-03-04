package com.knoma;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.knoma.pojo.Person;
import com.knoma.pojo.PersonDAO;
import io.micronaut.http.MediaType;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.Map;
import java.util.UUID;


@Controller("/person")
public class PersonController {

    private PersonDAO personDAO;

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);



    @Inject
    public PersonController(CqlSession session) {
        PersonMapper personMapper = new PersonMapperBuilder(session).build();
        this.personDAO = personMapper.personDao(CqlIdentifier.fromCql("cass_drop"));
    }

    @Get(uri = "/{personId}", produces = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<Person>> get(UUID personId) {
        return Mono.fromCompletionStage(personDAO.getById(personId))
                .map(person -> HttpResponse.ok(person));
    }

    @Delete(uri = "/{personId}", produces = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<Map<String, Long>>> delete(UUID personId) {
        personDAO.delete(personId);
        return Mono.fromCompletionStage(personDAO.getCount())
                .map(count -> Map.of("count", count))
                .map(responseBody -> HttpResponse.ok(responseBody));
    }

    @Get(uri = "/all", produces = MediaType.APPLICATION_JSON)
    public Mono<List<Person>> getAll() {
        log.info("Received request to index endpoint");

        return Mono.fromCompletionStage(personDAO.getAll())
                .flatMapIterable(page -> page.currentPage())
                .collectList();
    }

    @Get(uri = "/count", produces = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<Map<String, Long>>> getCount() {
        return Mono.fromCompletionStage(personDAO.getCount())
                .map(count -> Map.of("count", count))
                .map(responseBody -> HttpResponse.ok(responseBody));
    }

    @Post(uri = "/", produces = MediaType.APPLICATION_JSON)
    public Mono<Void> save(Person person) {
        return Mono.fromCompletionStage(personDAO.saveAsync(person));
    }
}