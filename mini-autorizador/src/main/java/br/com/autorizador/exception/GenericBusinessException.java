package br.com.autorizador.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GenericBusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    private HttpStatus status;
    private String message;
    private Object body;

    public GenericBusinessException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public GenericBusinessException(HttpStatus status, Object body) {
        this.status = status;
        this.body = body;
    }

    public GenericBusinessException(HttpStatus status) {
        this.status = status;
    }
}
