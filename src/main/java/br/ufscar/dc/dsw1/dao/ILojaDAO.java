package br.ufscar.dc.dsw1.dao;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

@SuppressWarnings("unchecked")
public interface ILojaDAO extends CrudRepository<Loja, Long> {
    Loja save(Loja s);
    Loja findByCnpj(String cnpj);
    Loja deleteByCnpj(String cnpj);
    Loja findLojaById(Long id);
    Loja deleteLojaById(Long id);
    List<Loja> findAll();
}
