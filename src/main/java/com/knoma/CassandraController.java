package com.knoma;

import com.datastax.oss.driver.api.core.CqlSession;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller("/cassandra")
public class CassandraController {
    private static final Logger log = LoggerFactory.getLogger(CassandraController.class);
    private final CqlSession session;


    @Inject
    public CassandraController(CqlSession session) {
        this.session = session;
    }

    @Get(uri = "/", produces = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<Map<String, String>>> get() {
        return Mono.from(session.executeReactive("SELECT release_version FROM system.local;"))
                .map(v -> HttpResponse.ok(Map.of("version", v.getString(0))));
    }
}
