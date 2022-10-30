package com.example.metacodingjunit.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.metacodingjunit.domain.Book;
import com.example.metacodingjunit.domain.BookRepository;
import com.example.metacodingjunit.web.dto.request.BookSaveReqDto;
import com.example.metacodingjunit.web.dto.response.BookRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
    
    private final BookRepository bookRepository;

    // 1. 책 등록하기
    @Transactional
    public BookRespDto 책등록하기(BookSaveReqDto dto) {
        Book entity = bookRepository.save(dto.toEntity());
        return entity.toDto(); // * 엔티티가 아닌 DTO 응답하기!
    }

    // 2. 책 목록보기

    // 3. 책 한건보기

    // 4. 책 삭제하기

    // 5. 책 수정하기
}
