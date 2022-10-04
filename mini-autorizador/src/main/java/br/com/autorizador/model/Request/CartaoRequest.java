package br.com.autorizador.model.Request;

import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
public class CartaoRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull
	private String numeroCartao;
	@NotNull
	private String senha;
}