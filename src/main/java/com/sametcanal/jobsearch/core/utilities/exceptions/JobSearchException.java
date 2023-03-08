package com.sametcanal.jobsearch.core.utilities.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobSearchException extends RuntimeException{
    private String errorCode;
    private Object errorMessage;
    private HttpStatus httpStatus;

    JobSearchException(String errorCode,String errorMessage,HttpStatus httpStatus){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
