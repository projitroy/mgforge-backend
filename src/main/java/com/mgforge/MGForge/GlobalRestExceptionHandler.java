package com.mgforge.MGForge;

import com.mgforge.MGForge.exception.AccountDisabledException;
import com.mgforge.MGForge.exception.AdminPortalAccessDeniedException;
import com.mgforge.MGForge.exception.InvalidCredentialsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException ex,
            HttpServletRequest request
    ){
        ErrorResponse body = new ErrorResponse(
                "INVALID_CREDENTIALS",
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AccountDisabledException.class)
    public ResponseEntity<ErrorResponse> handleAccountDisabled(
            AccountDisabledException ex,
            HttpServletRequest request
    ){
        ErrorResponse body = new ErrorResponse(
                "ACCOUNT_DISABLED",
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(AdminPortalAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAdminPortalDenied(
            AdminPortalAccessDeniedException ex,
            HttpServletRequest request
    ){
        ErrorResponse body = new ErrorResponse(
                "ADMIN_PORTAL_ACCESS_DENIED",
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(
            Exception ex,
            HttpServletRequest request
    ){
        ErrorResponse body = new ErrorResponse(
                "Internal Error",
                "Something went wrong. Please try again later!!",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
