package br.com.autorizador.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ErrorResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private int status;
	private String body;

}