package com.example.metacodingjunit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.metacodingjunit.domain.BookRepository;
import com.example.metacodingjunit.util.MailSender;
import com.example.metacodingjunit.web.dto.request.BookSaveReqDto;
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
}
