package br.ufscar.dc.dsw1.security.spec;

import br.ufscar.dc.dsw1.domain.Usuario;

import java.util.List;

public interface IUsuarioService {
    void salvar(Usuario s);
    Usuario buscaPorCodigo(String codigo);
    void excluir(String codigo);
    List<Usuario> buscarTodos();
}
