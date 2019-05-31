package com.example.exercise.person.web.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {

    private static final Person person = new Person("1", "Giel", "Reynders", 27);

    @PutMapping(consumes = "application/json")
    public ResponseEntity<?> createPerson(@RequestBody Person person) {
        return ResponseEntity.created(URI.create("/persons/" + person.getId())).build();
    }

    @GetMapping
    public ResponseEntity<Person> getPersons() {
        return ResponseEntity.ok(person);
    }
}
