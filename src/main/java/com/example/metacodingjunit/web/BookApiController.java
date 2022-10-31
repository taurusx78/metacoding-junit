package com.example.metacodingjunit.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.metacodingjunit.service.BookService;
import com.example.metacodingjunit.web.dto.request.BookSaveReqDto;
import com.example.metacodingjunit.web.dto.response.BookRespDto;
import com.example.metacodingjunit.web.dto.response.BookRespDtoList;
import com.example.metacodingjunit.web.dto.response.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    // 1. 책 등록하기
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> save(@RequestBody @Valid BookSaveReqDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        } else {
            BookRespDto bookRespDto = bookService.책등록하기(dto);
            return new ResponseEntity<>(new CMRespDto<>(1, "책 등록 성공", bookRespDto), HttpStatus.CREATED); // 201
        }
    }

    // 2. 책 목록보기
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> findAll() {
        BookRespDtoList dtoList = bookService.책목록보기();
        return new ResponseEntity<>(new CMRespDto<>(1, "책 목록보기 성공", dtoList), HttpStatus.OK); // 200
    }

    // 3. 책 한건보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        BookRespDto dto = bookService.책한건보기(id);
        return new ResponseEntity<>(new CMRespDto<>(1, "책 한건보기 성공", dto), HttpStatus.OK);
    }

    // 4. 책 삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        bookService.책삭제하기(id);
        return new ResponseEntity<>(new CMRespDto<>(1, "책 삭제하기 성공", null), HttpStatus.OK); // 200
    }

    // 5. 책 수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody @Valid BookSaveReqDto dto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        } else {
            BookRespDto bookRespDto = bookService.책수정하기(id, dto);
            return new ResponseEntity<>(new CMRespDto<>(1, "책 수정하기 성공", bookRespDto), HttpStatus.OK); // 200;
        }
    }
}
