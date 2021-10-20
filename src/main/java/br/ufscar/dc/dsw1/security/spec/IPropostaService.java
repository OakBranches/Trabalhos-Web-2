package br.ufscar.dc.dsw1.security.spec;

import br.ufscar.dc.dsw1.domain.Proposta;

import java.util.List;

public interface IPropostaService {
    void salvar(Proposta s);
    void excluir(Long id);
    Proposta buscaPorId(Long id);
    List<Proposta> buscarTodos();
    List<Proposta> buscarTodosPorCliente(String cpf);
    List<Proposta> buscarTodosPorCarro(Long id);
}
