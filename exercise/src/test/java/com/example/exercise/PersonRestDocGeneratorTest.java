package com.example.exercise;

import com.example.exercise.person.web.in.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs()
public class PersonRestDocGeneratorTest {

    private static final String ID = "1";
    private static final String FIRST_NAME = "Giel";
    private static final String LAST_NAME = "Reynders";
    private static final int AGE = 7;

    private static final String ID_DESCRIPTION = "This field contains the id of the person";
    private static final String FIST_NAME_DESCRIPTION = "This field contains the first name of the person";
    private static final String LAST_NAME_DESCRIPTION = "This field contains the last name of the person";
    private static final String AGE_DESCRIPTION = "This field contains the age of the person";


    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/snippets");

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    private RestDocumentationResultHandler documentationResultHandler;

    private FieldDescriptor[] personDescriptor;
    {
        personDescriptor = new FieldDescriptor[]{
                    fieldWithPath("id").type(JsonFieldType.STRING).description(ID_DESCRIPTION),
                    fieldWithPath("firstName").type(JsonFieldType.STRING).description(FIST_NAME_DESCRIPTION),
                    fieldWithPath("lastName").type(JsonFieldType.STRING).description(LAST_NAME_DESCRIPTION),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description(AGE_DESCRIPTION)
            };
    }

    private FieldDescriptor[] dsf ;
    {
        dsf = new FieldDescriptor[]{
                fieldWithPath("id").type(JsonFieldType.STRING).description(ID_DESCRIPTION),
                fieldWithPath("firstName").type(JsonFieldType.STRING).description(FIST_NAME_DESCRIPTION),
                fieldWithPath("lastName").type(JsonFieldType.STRING).description(LAST_NAME_DESCRIPTION),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description(AGE_DESCRIPTION)
        };
    }

    @Before
    public void setup() {
        documentationResultHandler = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(documentationResultHandler)
                .build();
    }

    @Test
    public void applicationContext() {
    }

    @Test
    public void getPersons() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andDo(documentationResultHandler.document(responseFields(personDescriptor)));
    }

    @Test
    public void createAPerson() throws Exception {
        Person person = new Person(ID, FIRST_NAME, LAST_NAME, AGE);

        mockMvc.perform(put("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person))
        )
                .andExpect(status().isCreated())
                .andDo(documentationResultHandler.document(requestFields(dsf)));
    }
}
