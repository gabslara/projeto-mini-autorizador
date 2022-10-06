package br.com.autorizador.model.Request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Builder
public class CartaoRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull
	private String numeroCartao;
	@NotNull
	private String senha;
}