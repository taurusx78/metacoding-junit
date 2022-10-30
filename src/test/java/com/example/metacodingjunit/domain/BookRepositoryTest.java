package com.example.metacodingjunit.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // DB 관련 컴포넌트만 메모리에 로딩 (예. Repository)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    
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
    }

    // 2. 책 목록보기

    // 3. 책 한건보기

    // 4. 책 수정하기

    // 5. 책 삭제하기
}
