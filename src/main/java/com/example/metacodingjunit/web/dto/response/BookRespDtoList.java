package com.example.metacodingjunit.web.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class BookRespDtoList {
    
    List<BookRespDto> items;

    public BookRespDtoList(List<BookRespDto> items) {
        this.items = items;
    }
}
