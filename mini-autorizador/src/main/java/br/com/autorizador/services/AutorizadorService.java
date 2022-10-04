package br.com.autorizador.services;

import br.com.autorizador.exception.GenericBusinessException;
import br.com.autorizador.model.Request.CartaoRequest;
import br.com.autorizador.model.Request.TransacaoRequest;
import br.com.autorizador.model.entity.Cartao;
import br.com.autorizador.model.enums.ErroAutorizacaoEnum;
import br.com.autorizador.model.response.CartaoResponse;
import br.com.autorizador.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AutorizadorService {

    private static final BigDecimal VALOR_SALDO_INICIAL = new BigDecimal(500);

    @Autowired
    CartaoRepository repository;

    Object objError;

    public CartaoResponse criarCartao(CartaoRequest request) throws GenericBusinessException {

        try {

            var cartao = repository.findById(request.getNumeroCartao());

            cartao.ifPresent(c -> objError = CartaoResponse.builder()
                    .numeroCartao(c.getId())
                    .senha(c.getSenha())
                    .build());

            repository.insert(new Cartao(request.getNumeroCartao(), request.getSenha(), VALOR_SALDO_INICIAL));

            return CartaoResponse.builder()
                    .numeroCartao(request.getNumeroCartao())
                    .senha(request.getSenha())
                    .build();

        } catch (Exception ex) {
            throw new GenericBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, objError);
        }
    }

    public BigDecimal obterSaldo(String numeroCartao) throws GenericBusinessException {

        var cartao = repository.findById(numeroCartao).orElseThrow(() -> new GenericBusinessException(HttpStatus.NOT_FOUND));

        return cartao.getSaldo();
    }

    public void autorizar(TransacaoRequest request) throws GenericBusinessException {

        var cartao = repository.findById(request.getNumeroCartao()).orElseThrow(() -> new GenericBusinessException(HttpStatus.NOT_FOUND, ErroAutorizacaoEnum.CARTAO_INEXISTENTE));
        
        if (!cartao.getSenha().equals(request.getSenha())) {
            throw new GenericBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, ErroAutorizacaoEnum.SENHA_INVALIDA);
        }

        if (cartao.getSaldo().compareTo(request.getValor()) >= 0) {
            var saldoAtualizado = cartao.getSaldo().subtract(request.getValor());
            cartao.setSaldo(saldoAtualizado);
            repository.save(cartao);
        } else {
            throw new GenericBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, ErroAutorizacaoEnum.SALDO_INSUFICIENTE);
        }
    }
}
