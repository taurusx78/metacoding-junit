package com.example.metacodingjunit.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.example.metacodingjunit.domain.Book;
import com.example.metacodingjunit.domain.BookRepository;
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

    @Autowired
    private BookRepository bookRepository;

    // @BeforeAll // 각 테스트 시작 전 전체 한번만 실행
    @BeforeEach // 각 테스트 시작 전마다 실행
    public void 데이터준비() {
        String title = "title";
        String author = "author";
        bookRepository.save(Book.builder().title(title).author(author).build());
    } // 하나의 테스트가 끝나면 트랜잭션 종료됨
    

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

    @Sql("classpath:db/tableInit.sql") // primary key 초기화 (전체테스트 시 에러 방지)
    @Test
    public void findAll() {
        // given
        String url = "http://localhost:" + port + "/api/v1/book";

        // when
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        // then
        DocumentContext dc = JsonPath.parse(responseEntity.getBody()); // json 파싱
        int code = dc.read("$.code");
        String title = dc.read("$.response.items[0].title");
        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("title");
    }
}
