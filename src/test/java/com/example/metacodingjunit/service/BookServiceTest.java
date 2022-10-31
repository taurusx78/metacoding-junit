package com.example.metacodingjunit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.metacodingjunit.domain.Book;
import com.example.metacodingjunit.domain.BookRepository;
import com.example.metacodingjunit.util.MailSender;
import com.example.metacodingjunit.web.dto.request.BookSaveReqDto;
import com.example.metacodingjunit.web.dto.response.BookRespDtoList;
import com.example.metacodingjunit.web.dto.response.BookRespDto;

// Repository를 제외한 Service만 테스트하기 위해 가짜데이터 생성

@ExtendWith(MockitoExtension.class) // 가짜 메모리 환경 생성
public class BookServiceTest {

    @InjectMocks // 가짜데이터 주입 받음
    private BookService bookService;

    @Mock // 가짜데이터 생성
    private BookRepository bookRepository;

    @Mock // 가짜데이터 생성
    private MailSender mailSender;
    
    @Test
    public void 책등록하기() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("title");
        dto.setAuthor("author");

        // stub (가설)
        // 가설 1. bookRepository의 save()를 실행하면 dto.toEntity()가 리턴됨
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        // 가설 2. mailSender의 send()를 실행하면 true가 리턴됨
        when(mailSender.send()).thenReturn(true);

        // when
        BookRespDto bookRespDto = bookService.책등록하기(dto);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

    @Test
    public void 책목록보기() {
        // given

        // stub
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1L, "title1", "author1"));
        bookList.add(new Book(2L, "title2", "author2"));
        when(bookRepository.findAll()).thenReturn(bookList);

        // when
        BookRespDtoList dtoList = bookService.책목록보기();

        // then
        assertThat(dtoList.getItems().get(0).getTitle()).isEqualTo(bookList.get(0).getTitle());
        assertThat(dtoList.getItems().get(0).getAuthor()).isEqualTo(bookList.get(0).getAuthor());
        assertThat(dtoList.getItems().get(1).getTitle()).isEqualTo(bookList.get(1).getTitle());
        assertThat(dtoList.getItems().get(1).getAuthor()).isEqualTo(bookList.get(1).getAuthor());
    }

    @Test
    public void 책한건보기() {
        // given
        Long id = 1L;

        // stub
        Book book = new Book(id, "title", "author");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto dto = bookService.책한건보기(id);

        // then
        assertThat(dto.getTitle()).isEqualTo(book.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void 책수정하기() {
        // given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("expectedTitle");
        dto.setAuthor("expectedAuthor");

        // stub
        Book book = new Book(id, "title", "author");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.책수정하기(id, dto);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }
}
