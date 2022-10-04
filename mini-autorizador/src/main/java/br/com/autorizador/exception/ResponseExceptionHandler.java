package br.com.autorizador.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERRO_REQUISICAO = "Parâmetros inválidos";

    @ResponseBody
    @ExceptionHandler(GenericBusinessException.class)
    public ResponseEntity<Object> handleGenericBusinessException(
            GenericBusinessException ex, WebRequest request) {
        return getObjectResponseGeneric(ex, request);
    }

    private ResponseEntity<Object> getObjectResponseGeneric(GenericBusinessException ex, WebRequest request) {

        Object body = null;

        if (ex.getBody() != null) {
            body = ex.getBody();
        }

        return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getStatus(), request);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, ERRO_REQUISICAO, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
