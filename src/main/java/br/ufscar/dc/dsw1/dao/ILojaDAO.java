package br.ufscar.dc.dsw1.dao;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@SuppressWarnings("unchecked")
public interface ILojaDAO extends CrudRepository<Loja, String> {
    Loja save(Cliente s);
    Loja findByCnpj(String cnpj);
    Loja deleteByCnpj(String cnpj);
    List<Loja> findAll();
}
