package br.ufscar.dc.dsw1.dao;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@SuppressWarnings("unchecked")
public interface IClienteDAO extends CrudRepository<Cliente, Long> {
    Cliente save(Cliente s);
    Cliente findClienteById(Long id);
    Cliente findByCpf(String cpf);
    List<Cliente> findAll();
}
