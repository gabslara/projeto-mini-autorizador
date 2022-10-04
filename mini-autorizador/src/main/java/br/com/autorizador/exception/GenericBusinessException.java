package br.com.autorizador.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class GenericBusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    private final HttpStatus status;
    private final String message;
}
