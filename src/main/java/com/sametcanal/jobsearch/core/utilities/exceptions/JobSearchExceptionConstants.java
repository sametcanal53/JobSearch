package com.sametcanal.jobsearch.core.utilities.exceptions;

import org.springframework.http.HttpStatus;

public class JobSearchExceptionConstants {
    // JSP-1XXX -> Global Controller Advice
    public static JobSearchException NESTED_OBJECT_EXCEPTION =
            new JobSearchException("JSP-1001","Nested object exception", HttpStatus.BAD_REQUEST);
    // JSP-1002 -> in GlobalControllerAdvice

}
