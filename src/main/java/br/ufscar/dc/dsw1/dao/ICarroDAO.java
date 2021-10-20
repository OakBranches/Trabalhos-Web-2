package br.ufscar.dc.dsw1.dao;

import br.ufscar.dc.dsw1.domain.Carro;
import br.ufscar.dc.dsw1.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@SuppressWarnings("unchecked")
public interface ICarroDAO extends CrudRepository<Carro, String> {
    Carro save(Carro s);
    Carro findById(Long Id);
    Carro deleteById(Long id);
    List<Carro> findAll();
    List<Carro> findAllByLoja_Codigo(String cnpj);
}
