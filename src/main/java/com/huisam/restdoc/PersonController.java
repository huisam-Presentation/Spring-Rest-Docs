package com.huisam.restdoc;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class PersonController {

    @GetMapping(value = "/v1/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getPerson() {
        return ResponseEntity.ok(Person.builder()
                .name("person")
                .age(ThreadLocalRandom.current().nextInt(10))
                .build()
        );
    }

    @PostMapping(value = "/v1/person",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> postPerson(@RequestBody @Valid ClientRequest clientRequest) {
        return ResponseEntity.ok(
                Person.builder()
                        .name(clientRequest.getName())
                        .age(ThreadLocalRandom.current().nextInt(100))
                        .build()
        );
    }


}
