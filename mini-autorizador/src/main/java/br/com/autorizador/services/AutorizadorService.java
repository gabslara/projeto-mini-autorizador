package br.com.autorizador.services;

import br.com.autorizador.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorizadorService {
	
	@Autowired
	CartaoRepository repository;

	public void insert(String id) {

	}

}
