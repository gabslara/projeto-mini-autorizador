package br.com.autorizador.exception;

import br.com.autorizador.model.enums.ErroAutorizacaoEnum;
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

        Object body = ex.getBody();

        if (body == null && ex.getMessage() != null) {
            try {
                body = ErroAutorizacaoEnum.valueOf(ex.getMessage());
            } catch (IllegalArgumentException e) {
                body = new Object[]{ex.getMessage()};
            }
        }

        return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getStatus(), request);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, ERRO_REQUISICAO, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
