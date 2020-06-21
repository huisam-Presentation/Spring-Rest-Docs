package com.huisam.restdoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PersonController.class)
@Import(RestDocConfiguration.class)
@AutoConfigureRestDocs
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Person Get Test")
    void Person_Get_Test() throws Exception {
        /* given when then */
        this.mockMvc.perform(get("/v1/person").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("person-get",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description(MediaType.APPLICATION_JSON_VALUE)
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON_VALUE)
                        ),
                        responseFields(
                                fieldWithPath("name").description("Name of Person"),
                                fieldWithPath("age").description("Age of Person")
                        ))
                )
                .andExpect(jsonPath("$.name").value(containsString("person")))
                .andExpect(jsonPath("$.age").isNumber())
        ;
    }

    @Test
    @DisplayName("Person Post Test")
    void person_post_test() throws Exception {
        /* given */
        ClientRequest clientRequest = new ClientRequest("hi");

        /* when & then */
        this.mockMvc.perform(post("/v1/person")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientRequest))
        )
                .andDo(print())
                .andDo(document("person-post",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON_VALUE),
                                headerWithName(HttpHeaders.ACCEPT).description(MediaType.APPLICATION_JSON_VALUE)
                        ),
                        requestFields(
                                fieldWithPath("name").optional().description("Name of Person")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON_VALUE)
                        ),
                        responseFields(
                                fieldWithPath("name").description("Name of Person"),
                                fieldWithPath("age").description("Age of Person")
                        ))
                )
                .andExpect(jsonPath("$.name").value(containsString("hi")))
                .andExpect(jsonPath("$.age").isNumber())
        ;
    }
}