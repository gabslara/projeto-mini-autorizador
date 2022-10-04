package br.com.autorizador.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class CartaoResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private String numeroCartao;
	private String senha;
}