package br.com.autorizador.exception;

import br.com.autorizador.model.response.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERRO_EXCEPTION = "Mensagem de erro: {}, exceção: () ";

    @ResponseBody
    @ExceptionHandler(GenericBusinessException.class)
    public ResponseEntity<Object> handleGenericBusinessException(
            GenericBusinessException ex, WebRequest request){
        return getObjectResponseGeneric(ex, request);
    }

    private ResponseEntity<Object> getObjectResponseGeneric(GenericBusinessException ex, WebRequest request){

        var error = ErrorResponse.builder()
                .status(ex.getStatus().value())
                .body(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), ex.getStatus(), request);
    }
}
