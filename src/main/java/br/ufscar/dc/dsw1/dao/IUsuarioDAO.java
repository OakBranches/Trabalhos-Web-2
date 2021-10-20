package br.ufscar.dc.dsw1.dao;

import br.ufscar.dc.dsw1.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@SuppressWarnings("unchecked")
public interface IUsuarioDAO extends CrudRepository<Usuario, String> {

    Usuario save(Usuario s);
    Usuario findByCodigo(String codigo);
    Usuario deleteByCodigo(String codigo);
    Usuario findByEmail(String email);
    List<Usuario> findAll();
}
