package com.example.continent.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public abstract class BaseException extends RuntimeException {
    //Đây là object chuẩn của Spring Boot 3, theo RFC 7807, để trả lỗi dưới dạng JSON chuẩn
    private final ProblemDetail problemDetail;

    protected BaseException(HttpStatus status, String code, Object... args) {
        super(code); // giữ lại code để tra trong i18n

        //Tạo ra 1 ProblemDetail mặc định với status = HTTP status truyền vào.
        this.problemDetail = ProblemDetail.forStatus(status);

        //Đặt title = lý do mặc định của HTTP status
        this.problemDetail.setTitle(status.getReasonPhrase());

        //detail tạm thời = code, qua GlobalExceptionHandler nó sẽ override thành message đã dịch theo locale
        this.problemDetail.setDetail(code); // sẽ override trong GlobalExceptionHandler
    }


    //lấy ra ProblemDetail để custom và trả về JSON cho client
    public ProblemDetail getProblemDetail() {
        return problemDetail;
    }
}
