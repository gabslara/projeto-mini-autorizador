package br.com.autorizador.model.Request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Builder
public class TransacaoRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull
	private String numeroCartao;
	@NotNull
	private String senha;
	@NotNull
	private BigDecimal valor;
}