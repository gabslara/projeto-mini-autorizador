package br.com.autorizador.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

@Document(collection = "cartao")
@Getter
@Setter
public class Cartao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String senha;
    private BigDecimal saldo;

    public Cartao(String id, String senha, BigDecimal saldo) {
        this.id = id;
        this.senha = senha;
        this.saldo = saldo;
    }
}