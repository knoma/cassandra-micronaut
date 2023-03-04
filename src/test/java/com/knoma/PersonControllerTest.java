//package com.knoma;
//
//import com.datastax.oss.driver.api.core.CqlIdentifier;
//import com.datastax.oss.driver.api.core.CqlSession;
//import com.knoma.pojo.Person;
//import com.knoma.pojo.PersonDAO;
//import io.micronaut.http.HttpStatus;
//import io.micronaut.http.MediaType;
//import io.micronaut.runtime.server.EmbeddedServer;
//import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
//
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.*;
//
//import java.util.UUID;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//
//@MicronautTest
//class PersonControllerTest {
//
//    @Inject
//    EmbeddedServer server;
//
//    private PersonDAO personDAO;
//
//    @Inject
//    CqlSession session;
//
//    UUID personId1 = UUID.randomUUID();
//    UUID personId2 = UUID.randomUUID();
//
//    @BeforeEach
//    public void setupData() {
//
//        PersonMapper personMapper = new PersonMapperBuilder(session).build();
//        this.personDAO = personMapper.personDao(CqlIdentifier.fromCql("cass_drop"));
//
//        session.execute("TRUNCATE cass_drop.person ;");
//
////        // create and save test data to Cassandra
////        Person person1 = new Person(personId1, "John", "Doe", "30");
////        Person person2 = new Person(personId2, "Jane", "Doe", "25");
////        personDAO.saveAsync(person1);
////        personDAO.saveAsync(person2);
//    }
//
//    @Test
//    void testGetPersonById() {
//
//        given()
//                .port(server.getPort())
//                .when()
//                .get("/person/{personId}", personId1)
//                .then()
//                .statusCode(HttpStatus.OK.getCode())
//                .contentType(MediaType.APPLICATION_JSON)
//                .body("id", equalTo(personId1.toString()));
//    }
//
//    @Test
//    void testDeletePerson() {
//
//        given()
//                .port(server.getPort())
//                .when()
//                .delete("/person/{personId}", personId2)
//                .then()
//                .statusCode(HttpStatus.OK.getCode())
//                .contentType(MediaType.APPLICATION_JSON)
//                .body("count", equalTo(1));
//    }
//
//    @Test
//    void testGetAllPersons() {
//        given()
//                .port(server.getPort())
//                .when()
//                .get("/person/all")
//                .then()
//                .statusCode(HttpStatus.OK.getCode())
//                .contentType(MediaType.APPLICATION_JSON);
//    }
//
//    @Test
//    void testGetPersonsCount() {
//        given()
//                .port(server.getPort())
//                .when()
//                .get("/person/count")
//                .then()
//                .statusCode(HttpStatus.OK.getCode())
//                .contentType(MediaType.APPLICATION_JSON)
//                .body("count", equalTo(2));
//    }
//
//    @Test
//    void testSavePerson() {
//        Person person = new Person(UUID.randomUUID(), "Test", "User", "test@sdfsd");
//
//        given()
//                .port(server.getPort())
//                .body(person)
//                .contentType(MediaType.APPLICATION_JSON)
//                .when()
//                .post("/person")
//                .then()
//                .statusCode(HttpStatus.OK.getCode());
//
//        given()
//                .port(server.getPort())
//                .when()
//                .get("/person/all")
//                .then()
//                .statusCode(HttpStatus.OK.getCode())
//                .contentType(MediaType.APPLICATION_JSON)
//                .body("size()", equalTo(1))
//                .body("[0].id", equalTo(person.getId().toString()))
//                .body("[0].firstName", equalTo(person.getFirstName()))
//                .body("[0].lastName", equalTo(person.getLastName()));
//    }
//}
