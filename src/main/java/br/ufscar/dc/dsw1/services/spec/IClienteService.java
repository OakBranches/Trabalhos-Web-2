package br.ufscar.dc.dsw1.services.spec;

import br.ufscar.dc.dsw1.domain.Cliente;

import java.util.List;

public interface IClienteService {
    void salvar(Cliente s);
    Cliente buscaPorId(Long id);
    void excluirPorId(Long id);
    List<Cliente> buscarTodos();
    boolean clienteTemPropostasAbertasNoCarro(Long id, Long car_id);
    boolean clienteTemPropostas(Long id);
}
