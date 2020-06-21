package com.huisam.restdoc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@WebFluxTest(PersonController.class)
@Import(RestDocConfiguration.class)
@AutoConfigureRestDocs
public class WebClientPersonControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Person Post Test")
    void test() {
        /* given */
        ClientRequest clientRequest = new ClientRequest("hi");

        /* when */
        final Person responsePerson = webTestClient.post()
                .uri("/v1/person")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(clientRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class)
                .consumeWith(
                        document("web-test-person-post",
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
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("Name of Person"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("Age of Person")
                                ))
                )
                .returnResult()
                .getResponseBody();

        /* then */
        assertThat(responsePerson)
                .extracting("name")
                .isEqualTo(clientRequest.getName());
    }
}
