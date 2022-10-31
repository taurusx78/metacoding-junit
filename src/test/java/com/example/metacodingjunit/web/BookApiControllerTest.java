package com.example.metacodingjunit.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.example.metacodingjunit.web.dto.request.BookSaveReqDto;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

// 통합테스트 (Controller, Service, Repository) - stub 필요없음
// AWS 배포 전 통합테스트만 진행할 예정
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void save() throws Exception {
        // given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto("title", "author");

        String url = "http://localhost:" + port + "/api/v1/book";

        // when
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, bookSaveReqDto, String.class);

        // then
        DocumentContext dc = JsonPath.parse(responseEntity.getBody()); // json 파싱
        String title = dc.read("$.response.title");
        String author = dc.read("$.response.author");
        assertThat(title).isEqualTo("title");
        assertThat(author).isEqualTo("author");
    }
}
