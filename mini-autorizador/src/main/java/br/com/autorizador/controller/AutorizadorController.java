package br.com.autorizador.controller;

import br.com.autorizador.exception.GenericBusinessException;
import br.com.autorizador.model.Request.CartaoRequest;
import br.com.autorizador.model.Request.TransacaoRequest;
import br.com.autorizador.model.response.CartaoResponse;
import br.com.autorizador.services.AutorizadorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Api(value = "API AUTORIZADOR")
@RestController
@RequestMapping("v1/autorizador")
public class AutorizadorController {

    @Autowired
    private AutorizadorService service;

    @ApiOperation(value = "Criar Cartão")
    @PostMapping(path = "/cartoes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartaoResponse> criarCartao(@Valid @RequestBody CartaoRequest request) throws GenericBusinessException {

        var response = service.criarCartao(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Obter saldo do Cartão")
    @GetMapping(path = "/cartoes/{numeroCartao}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BigDecimal> obterSaldo(@Valid @PathVariable String numeroCartao) throws GenericBusinessException {

        var response = service.obterSaldo(numeroCartao);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Realizar uma Transação")
    @PostMapping(path = "/transacoes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> autorizar(@Valid @RequestBody TransacaoRequest request) throws GenericBusinessException {

        service.autorizar(request);

        return new ResponseEntity<>("Ok", HttpStatus.CREATED);
    }

}