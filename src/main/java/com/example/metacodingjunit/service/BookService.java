package com.example.metacodingjunit.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.metacodingjunit.domain.Book;
import com.example.metacodingjunit.domain.BookRepository;
import com.example.metacodingjunit.util.MailSender;
import com.example.metacodingjunit.web.dto.request.BookSaveReqDto;
import com.example.metacodingjunit.web.dto.response.BookRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
    
    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1. 책 등록하기
    @Transactional
    public BookRespDto 책등록하기(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        // 책 등록 성공 시 메일 전송
        if (!mailSender.send()) {
            throw new RuntimeException("메일 전송에 실패하였습니다.");
        }
        return bookPS.toDto(); // * 엔티티가 아닌 DTO 응답하기!
    }

    // 2. 책 목록보기
    public List<BookRespDto> 책목록보기() {
        return bookRepository.findAll().stream()
                .map(book -> book.toDto()) // .map(book::toDto)
                .collect(Collectors.toList());
    }

    // 3. 책 한건보기
    public BookRespDto 책한건보기(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 책이 없습니다. id=" + id));
        return book.toDto();
    }

    // 4. 책 삭제하기
    @Transactional
    public void 책삭제하기(Long id) {
        bookRepository.deleteById(id);
    }

    // 5. 책 수정하기
    @Transactional
    public BookRespDto 책수정하기(Long id, BookSaveReqDto dto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 책이 없습니다. id=" + id));
        book.update(dto.getTitle(), dto.getAuthor());
        return book.toDto();
    } // 메서드 종료 시 더티체킹으로 UPDATE 됨
}
