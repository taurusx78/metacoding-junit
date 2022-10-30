package com.example.metacodingjunit.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CMRespDto<T> {
    
    private int code; // 성공 (1), 실패 (-1)
    private String message;
    private T response;

    @Builder
    public CMRespDto(int code, String message, T response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }
}
