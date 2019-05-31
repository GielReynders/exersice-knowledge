package com.example.exercise;

import com.example.exercise.person.web.in.Person;
import com.example.exercise.person.web.in.PersonController;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractVerifierBase {

    @LocalServerPort
    int port;

    @MockBean
    private PersonController personController;

    @Test
    public void contextLoads() {
    }

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:" + this.port;

        Person person = new Person("1", "Giel", "Renyders", 27);

        Mockito.when(personController.createPerson(ArgumentMatchers.any()))
                .thenReturn(ResponseEntity.created(URI.create("/persons/" + person.getId())).build());
    }
}
