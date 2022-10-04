package br.com.autorizador.controller;

import br.com.autorizador.exception.GenericBusinessException;
import br.com.autorizador.model.response.ErrorResponse;
import br.com.autorizador.services.AutorizadorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "API AUTORIZADOR")
@RestController
@RequestMapping("/api/autorizador/v1")
public class AutorizadorController {

    @Autowired
    private AutorizadorService service;

    @ApiResponses(value = {
            @ApiResponse(code = 422, message = "Não autorizado.", response = ErrorResponse.class)
    })
    @ApiOperation(value = "Criar Cartão")
    @PostMapping("/{id}")
    public ResponseEntity<?> insert(@PathVariable("id") String id) {

        return ResponseEntity.ok().build();
    }

}