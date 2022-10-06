package br.com.autorizador.service;

import br.com.autorizador.exception.GenericBusinessException;
import br.com.autorizador.model.Request.CartaoRequest;
import br.com.autorizador.model.Request.TransacaoRequest;
import br.com.autorizador.model.entity.Cartao;
import br.com.autorizador.repository.CartaoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class AutorizadorServiceTest {

    @InjectMocks
    AutorizadorServiceImpl service;
    @Mock
    CartaoRepository repository;

    CartaoRequest cartaoRequest;
    TransacaoRequest transacaoRequest;
    Cartao cartao;

    @Before
    public void setUp() {
        service = new AutorizadorServiceImpl();
        initMocks(this);

        cartaoRequest = CartaoRequest.builder()
                .numeroCartao("123456")
                .senha("555555")
                .build();

        cartao = new Cartao();
        cartao.setId(cartaoRequest.getNumeroCartao());
        cartao.setSenha(cartaoRequest.getSenha());
        cartao.setSaldo(BigDecimal.valueOf(50));

        transacaoRequest = transacaoRequest.builder()
                .valor(BigDecimal.valueOf(50))
                .senha("555555")
                .numeroCartao("123456")
                .build();
    }

    @Test
    public void criarCartaoSucesso() throws GenericBusinessException {

        when(repository.findById(anyString())).thenReturn(Optional.of(cartao));

        when(repository.insert(any(Cartao.class))).thenReturn(cartao);

        var response = this.service.criarCartao(cartaoRequest);

        assertThat(response.getNumeroCartao()).isNotNull();
    }

    @Test
    public void criarCartaoException() throws GenericBusinessException {

        when(repository.findById(anyString())).thenReturn(Optional.of(cartao));

        when(repository.insert(any(Cartao.class))).thenThrow(new RuntimeException());

        assertThatThrownBy(() -> this.service.criarCartao(cartaoRequest)).isInstanceOf(GenericBusinessException.class);
    }

    @Test
    public void obterSaldoSucesso() throws GenericBusinessException {

        when(repository.findById(anyString())).thenReturn(Optional.of(cartao));

        var response = this.service.obterSaldo("123456");

        assertThat(response).isNotNull();
    }

    @Test
    public void autorizarSucesso() throws GenericBusinessException {

        when(repository.findById(anyString())).thenReturn(Optional.of(cartao));

        this.service.autorizar(transacaoRequest);
    }

    @Test
    public void autorizarSenhaInvalidaException() throws GenericBusinessException {

        transacaoRequest = transacaoRequest.builder()
                .valor(BigDecimal.valueOf(50))
                .senha("666666")
                .numeroCartao("123456")
                .build();

        when(repository.findById(anyString())).thenReturn(Optional.of(cartao));

        assertThatThrownBy(() -> this.service.autorizar(transacaoRequest)).isInstanceOf(GenericBusinessException.class).hasMessage("SENHA_INVALIDA");
    }

    @Test
    public void autorizarSaldoInsuficienteException() throws GenericBusinessException {

        cartao = new Cartao();
        cartao.setId(cartaoRequest.getNumeroCartao());
        cartao.setSenha(cartaoRequest.getSenha());
        cartao.setSaldo(BigDecimal.ZERO);

        when(repository.findById(anyString())).thenReturn(Optional.of(cartao));

        assertThatThrownBy(() -> this.service.autorizar(transacaoRequest)).isInstanceOf(GenericBusinessException.class).hasMessage("SALDO_INSUFICIENTE");
    }
}
