package com.example.metacodingjunit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("dev") // dev 모드에서만 동작하도록 설정
@DataJpaTest // DB 관련 컴포넌트만 메모리에 로딩 (예. Repository)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    // @BeforeAll // 각 테스트 시작 전 전체 한번만 실행
    @BeforeEach // 각 테스트 시작 전마다 실행
    public void 데이터준비() {
        String title = "title";
        String author = "author";
        bookRepository.save(Book.builder().title(title).author(author).build());
    } // 하나의 테스트가 끝나면 트랜잭션 종료됨
    
    // 1. 책 등록하기
    @Test
    public void 책등록하기() {
        // given (데이터 준비)
        String title = "title";
        String author = "author";
        Book book = Book.builder().title(title).author(author).build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then (검증)
        assertThat(bookPS.getTitle()).isEqualTo(title);
        assertThat(bookPS.getAuthor()).isEqualTo(author);
    } // 트랜잭션 종료 및 저장된 데이터 초기화

    // 2. 책 목록보기
    @Test
    public void 책목록보기() {
        // given
        String title = "title";
        String author = "author";

        // when
        List<Book> bookList = bookRepository.findAll();

        // then
        Book bookPS = bookList.get(0);
        assertThat(bookPS.getTitle()).isEqualTo(title);
        assertThat(bookPS.getAuthor()).isEqualTo(author);
    }

    // 3. 책 한건보기
    @Sql("classpath:db/tableInit.sql") // primary key 초기화 (전체테스트 시 에러 방지)
    @Test
    public void 책한건보기() {
        // given
        Long id = 1L;
        String title = "title";
        String author = "author";

        // when
        Book bookPS = bookRepository.findById(id).get();

        // then
        assertThat(bookPS.getTitle()).isEqualTo(title);
        assertThat(bookPS.getAuthor()).isEqualTo(author);
    }

    // 4. 책 삭제하기
    @Sql("classpath:db/tableInit.sql") // primary key 초기화 (전체테스트 시 에러 방지)
    @Test
    public void 책삭제() {
        // given
        Long id = 1L;

        // when
        // 1. id가 null인 경우 IllegalArgumentsException 발동
        // 2. id가 없는 경우 EmptyResultDataAccessException 발동
        bookRepository.deleteById(id); 

        // then
        assertFalse(bookRepository.findById(id).isPresent()); // false일 때 테스트 성공
    }

    // 5. 책 수정하기
    @Sql("classpath:db/tableInit.sql") // primary key 초기화 (전체테스트 시 에러 방지)
    @Test
    public void 책수정하기() {
        // given
        Long id = 1L;
        String expectedTitle = "title2";
        String expectedAuthor = "author2";
        Book book = new Book(id, expectedTitle, expectedAuthor);

        // when
        Book bookPS = bookRepository.save(book);

        // then
        assertThat(bookPS.getId()).isEqualTo(id);
        assertThat(bookPS.getTitle()).isEqualTo(expectedTitle);
        assertThat(bookPS.getAuthor()).isEqualTo(expectedAuthor);
    }
}
