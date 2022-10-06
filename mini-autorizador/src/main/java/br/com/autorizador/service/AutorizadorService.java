package br.com.autorizador.service;

import br.com.autorizador.exception.GenericBusinessException;
import br.com.autorizador.model.Request.CartaoRequest;
import br.com.autorizador.model.Request.TransacaoRequest;
import br.com.autorizador.model.response.CartaoResponse;

import java.math.BigDecimal;

public interface AutorizadorService {

    /***
     * Método para criar cartão
     * @param request
     */
    CartaoResponse criarCartao(CartaoRequest request) throws GenericBusinessException;

    /***
     * Método para obter saldo disponível
     * @param numeroCartao
     */
    BigDecimal obterSaldo(String numeroCartao) throws GenericBusinessException;

    /***
     * Método para autorizar transação
     * @param request
     */
    void autorizar(TransacaoRequest request) throws GenericBusinessException;
}
