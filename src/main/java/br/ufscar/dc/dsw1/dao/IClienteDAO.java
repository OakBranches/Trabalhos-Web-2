package br.ufscar.dc.dsw1.dao;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@SuppressWarnings("unchecked")
public interface IClienteDAO extends CrudRepository<Cliente, String> {
    Cliente save(Cliente s);
    Cliente findByCodigo(String cpf);
    Cliente deleteByCodigo(String cpf);
    List<Cliente> findAll();
}
