package br.ufscar.dc.dsw1.services.spec;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.domain.Usuario;

import java.util.List;

public interface ILojaService {
    void salvar(Loja s);
    Loja buscaPorCnpj(String cnpj);
    Loja buscaPorId(Long id);
    void excluirPorId(Long id);
    void excluir(String cnpj);
    boolean LojaTemCarros(Long id);
    List<Loja> buscarTodasLojas();
    boolean emailIsValid(Usuario usr);
}
