package br.com.autorizador.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection="cartao")
@Getter
@Setter
public class Cartao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String nome;

	private String senha;

	private String saldo;
}