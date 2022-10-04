package br.com.autorizador.repository;

import br.com.autorizador.model.entity.Cartao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends MongoRepository<Cartao, String> {
}