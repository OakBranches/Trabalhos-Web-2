package br.ufscar.dc.dsw1.services.spec;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Loja;

import java.util.List;

public interface ILojaService {
    void salvar(Cliente s);
    Loja buscaPorCnpj(String cnpj);
    void excluir(String cnpj);
    List<Loja> buscarTodos();
}