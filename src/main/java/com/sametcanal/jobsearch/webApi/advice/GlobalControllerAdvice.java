package com.sametcanal.jobsearch.webApi.advice;

import com.sametcanal.jobsearch.core.utilities.exceptions.JobSearchException;
import com.sametcanal.jobsearch.core.utilities.exceptions.JobSearchExceptionConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    public static final String CONTENT_TYPE_VALUE = "application/json";
    public static final String CONTENT_TYPE = "Content-Type";

    public static final String RETURN_CODE_HEADER = "jsp-return-code";
    public static final String RETURN_MESSAGE_HEADER = "jsp-return-message";

    @ExceptionHandler(JobSearchException.class)
    public ResponseEntity<Object> handle(JobSearchException e){
        log.error(e.getMessage(), e);

        String errorMessage = (String) e.getErrorMessage();
        String errorCode = e.getErrorCode();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        responseHeaders.add(RETURN_CODE_HEADER, errorCode);
        responseHeaders.add(RETURN_MESSAGE_HEADER, errorMessage);

        JobSearchException jobSearchException = JobSearchException
                .builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .httpStatus(e.getHttpStatus())
                .build();

        return ResponseEntity
                .status(e.getHttpStatus())
                .headers(responseHeaders)
                .body(jobSearchException);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<JobSearchException> nestedObjectExceptionHandling(DataIntegrityViolationException exceptions) {
        log.error("Global Controller Advice - Data Integrity Violantion Exception");
        return new ResponseEntity<JobSearchException>(JobSearchExceptionConstants.NESTED_OBJECT_EXCEPTION, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationExceptions(MethodArgumentNotValidException exceptions) {
        log.error("Global Controller Advice - Method Argument Not Valid Exception");
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new JobSearchException("JSP-1002", errors ,HttpStatus.BAD_REQUEST).getErrorMessage();
    }
}
