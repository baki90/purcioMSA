package com.order.common.error;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ExceptionController {

    private final ObjectMapper objectMapper;

    // valid 유효성 검사를 통과하지 못한 것을 관리하는 핸들러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        log.warn("[MethodArgumentNotValidException 발생] url:{}, trace:{}",request.getRequestURI(), e.getStackTrace());
        ErrorResponse errorResponse = makeErrorResponse(e.getBindingResult());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // findBy를 하였는데, 특정 값이 조회되지 않았을 때 반환하는 에러
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> noSuchElementException(NoSuchElementException e, HttpServletRequest request){
        log.warn("[NoSuchElementException 발생] url:{}, trace:{}",request.getRequestURI(), e.getStackTrace());

        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_FOUND_ELEMENT.getCode(), ErrorCode.NOT_FOUND_ELEMENT.getDescription(),
                e.getMessage());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 처리가 불가한 값이 넘어왔을 때
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException e, HttpServletRequest request){
        log.warn("[NoSuchElementException 발생] url:{}, trace:{}",request.getRequestURI(), e.getStackTrace());

        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ILLEGAL_ARG.getCode(),
                ErrorCode.ILLEGAL_ARG.getDescription(), e.getMessage());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // feign error decoder
    @ExceptionHandler(FeignException.BadRequest.class)
    public Map<String, Object> feignException(FeignException e, HttpServletResponse response) throws JsonProcessingException {
        response.setStatus(e.status());
        Map<String, Object> stringObjectMap = objectMapper.readValue(e.contentUTF8(), new TypeReference<Map<String, Object>>() {
        });
        return stringObjectMap;
    }

    private ErrorResponse makeErrorResponse(BindingResult bindingResult){
        String code = "";
        String description = "";
        String detail = "";

        if(bindingResult.hasErrors()){
            detail = bindingResult.getFieldError().getDefaultMessage();
            String bindResultCode = bindingResult.getFieldError().getCode();

            switch (bindResultCode){
                case "NotBlank":
                    code = ErrorCode.NOT_BLANK.getCode();
                    description = ErrorCode.NOT_BLANK.getDescription();
                    break;
            }
        }

        return new ErrorResponse(code, description, detail);
    }
}