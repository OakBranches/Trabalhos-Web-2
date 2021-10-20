package br.ufscar.dc.dsw1.dao;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Proposta;
import br.ufscar.dc.dsw1.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@SuppressWarnings("unchecked")
public interface IPropostaDAO extends CrudRepository<Proposta, String> {
    Proposta save(Proposta s);
    Proposta findById(Long id);
    Proposta deleteById(Long id);
    List<Proposta> findAll();
    List<Proposta> findAllByCliente_Codigo(String cpf);
    List<Proposta> findAllByCarro_Id(Long id);

}
