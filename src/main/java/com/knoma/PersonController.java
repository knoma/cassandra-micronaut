package com.knoma;

import com.knoma.pojo.Person;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
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
    private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;

    @Inject
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Get(uri = "/{personId}", produces = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<Person>> get(UUID personId) {
        return personService.getById(personId)
                .map(HttpResponse::ok);
    }

    @Delete(uri = "/{personId}", produces = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<Map<String, Long>>> delete(UUID personId) {
        return personService.delete(personId)
                .then(personService.getCount())
                .map(count -> Map.of("count", count))
                .map(HttpResponse::ok);
    }

    @Get(uri = "/all", produces = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<List<Person>>> getAll() {
        log.info("Received request to index endpoint");
        return personService.getAll()
                .collectList()
                .map(HttpResponse::ok);
    }

    @Get(uri = "/count", produces = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<Map<String, Long>>> getCount() {
        return personService.getCount()
                .map(count -> Map.of("count", count))
                .map(HttpResponse::ok);
    }

    @Post(uri = "/", produces = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<Void>> save(Person person) {
        return personService.save(person)
                .map(ignore -> HttpResponse.ok());
    }
}
