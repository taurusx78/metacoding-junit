package com.example.metacodingjunit.web.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.metacodingjunit.domain.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookSaveReqDto {
    
    @Size(min = 1, max = 50)
    @NotBlank
    private String title;

    @Size(min = 2, max = 20)
    @NotBlank
    private String author;

    // BookSaveReqDto 객체를 바탕으로 Book 엔티티 생성
    public Book toEntity() {
        return Book.builder().title(title).author(author).build();
    }
}
