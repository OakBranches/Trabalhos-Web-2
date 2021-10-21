package br.ufscar.dc.dsw1.services.spec;

import br.ufscar.dc.dsw1.domain.Cliente;

import java.util.List;

public interface IClienteService {
    void salvar(Cliente s);
    Cliente buscaPorCpf(String cpf);
    Cliente buscaPorId(Long id);
    void excluirPorId(Long id);
    void excluir(String cpf);
    List<Cliente> buscarTodos();
    boolean clienteTemPropostas(Long id);
}
