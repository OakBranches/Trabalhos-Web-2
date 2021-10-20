package br.ufscar.dc.dsw1.services.spec;

import br.ufscar.dc.dsw1.domain.Usuario;

import java.util.List;

public interface IUsuarioService {
    void salvar(Usuario s);
    Usuario buscaPorId(Long Id);
    Usuario buscaPorEmail(String email);
    void excluir(Long Id);
    List<Usuario> buscarTodos();
}
