package br.com.autorizador.services;

import br.com.autorizador.exception.GenericBusinessException;
import br.com.autorizador.model.Request.CartaoRequest;
import br.com.autorizador.model.Request.TransacaoRequest;
import br.com.autorizador.model.entity.Cartao;
import br.com.autorizador.model.enums.ErroAutorizacaoEnum;
import br.com.autorizador.model.response.CartaoResponse;
import br.com.autorizador.repository.CartaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class AutorizadorServiceImpl implements AutorizadorService {

    private static final String MSG_INICIO_METODO_LOG = "Início do método {} da classe {}";
    private static final String MSG_FIM_METODO_LOG = "Fim do método {} da classe {}";
    private static final BigDecimal VALOR_SALDO_INICIAL = new BigDecimal(500);

    @Autowired
    CartaoRepository repository;

    Object objError;

    @Override
    public CartaoResponse criarCartao(CartaoRequest request) throws GenericBusinessException {
        log.info(MSG_INICIO_METODO_LOG, "criarCartao", this.getClass().getName());

        try {

            log.info("Verificando se já existe cartão com o número informado");
            var cartao = repository.findById(request.getNumeroCartao());

            cartao.ifPresent(c -> objError = CartaoResponse.builder()
                    .numeroCartao(c.getId())
                    .senha(c.getSenha())
                    .build());

            log.info("Inserindo informações do cartão na base de dados");
            repository.insert(new Cartao(request.getNumeroCartao(), request.getSenha(), VALOR_SALDO_INICIAL));

            log.info(MSG_FIM_METODO_LOG, "criarCartao", this.getClass().getName());
            return CartaoResponse.builder()
                    .numeroCartao(request.getNumeroCartao())
                    .senha(request.getSenha())
                    .build();
        } catch (Exception ex) {
            log.error("Erro: {}", ex.getMessage());
            throw new GenericBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, objError);
        }
    }

    @Override
    public BigDecimal obterSaldo(String numeroCartao) throws GenericBusinessException {
        log.info(MSG_INICIO_METODO_LOG, "obterSaldo", this.getClass().getName());

        log.info("Verificando se o cartão existe");
        var cartao = repository.findById(numeroCartao).orElseThrow(() -> new GenericBusinessException(HttpStatus.NOT_FOUND));

        log.info(MSG_FIM_METODO_LOG, "obterSaldo", this.getClass().getName());
        return cartao.getSaldo();
    }

    @Override
    public void autorizar(TransacaoRequest request) throws GenericBusinessException {
        log.info(MSG_INICIO_METODO_LOG, "autorizar", this.getClass().getName());

        log.info("Verificando se o cartão existe");
        var cartao = repository.findById(request.getNumeroCartao()).orElseThrow(() -> new GenericBusinessException(HttpStatus.NOT_FOUND, ErroAutorizacaoEnum.CARTAO_INEXISTENTE));

        log.info("Validando a senha informada");
        if (!cartao.getSenha().equals(request.getSenha())) {
            log.error("Erro: {}", ErroAutorizacaoEnum.SENHA_INVALIDA);
            throw new GenericBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, ErroAutorizacaoEnum.SENHA_INVALIDA);
        }

        log.info("Obtendo o saldo");
        if (cartao.getSaldo().compareTo(request.getValor()) >= 0) {
            var saldoAtualizado = cartao.getSaldo().subtract(request.getValor());
            cartao.setSaldo(saldoAtualizado);
            repository.save(cartao);
        } else {
            log.error("Erro: {}", ErroAutorizacaoEnum.SALDO_INSUFICIENTE);
            throw new GenericBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, ErroAutorizacaoEnum.SALDO_INSUFICIENTE);
        }
        log.info(MSG_FIM_METODO_LOG, "autorizar", this.getClass().getName());
    }
}
