package br.ufscar.dc.dsw1.services.spec;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Usuario;

import java.util.List;

public interface IClienteService {
    void salvar(Cliente s);
    Cliente buscaPorId(Long id);
    Cliente buscaPorCpf(String cpf);
    void excluirPorId(Long id);
    List<Cliente> buscarTodosClientes();
    boolean clienteTemPropostasAbertasNoCarro(Long id, Long car_id);
    boolean clienteTemPropostas(Long id);
    boolean emailIsValid(Usuario usr);
}
