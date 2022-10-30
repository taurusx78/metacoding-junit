package com.example.metacodingjunit.web.dto.request;

import com.example.metacodingjunit.domain.Book;

import lombok.Data;

@Data
public class BookSaveReqDto {
    
    private String title;
    private String author;

    // BookSaveReqDto 객체를 바탕으로 Book 엔티티 생성
    public Book toEntity() {
        return Book.builder().title(title).author(author).build();
    }
}
