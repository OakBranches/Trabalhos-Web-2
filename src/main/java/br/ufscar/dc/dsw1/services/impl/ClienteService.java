package br.ufscar.dc.dsw1.services.impl;

import br.ufscar.dc.dsw1.dao.IClienteDAO;
import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Proposta;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.services.spec.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = false)
public class ClienteService extends UsuarioService implements IClienteService {
    @Autowired
    IClienteDAO dao;

    public void salvar(Cliente s){
        dao.save(s);
    }
    public void excluirPorId(Long id){
        dao.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Cliente buscaPorId(Long id){
        return dao.findClienteById(id);
    }
    @Transactional(readOnly = true)
    public boolean clienteTemPropostas(Long id){
        return !(dao.findClienteById(id).getPropostas().isEmpty());}
    @Transactional(readOnly = true)
    public boolean clienteTemPropostasAbertasNoCarro(Long id, Long car_id){
        Predicate<Proposta> filtro = proposta -> (proposta.getId().equals(car_id) && proposta.getStatus() == 0);
        return dao.findClienteById(id).getPropostas().stream().anyMatch(filtro);
    }
    @Transactional(readOnly = true)
    public List<Cliente> buscarTodosClientes(){
        return dao.findAll();
    }
    @Transactional(readOnly = true)
    public boolean emailIsValid(Usuario usr){
        Usuario loja = buscaPorEmail(usr.getEmail());
        Long Id = usr.getId();
        return loja == null || Objects.equals(loja.getId(), Id);
    }
    @Transactional(readOnly = true)
    public Cliente buscaPorCpf(String cpf){
        return dao.findByCpf(cpf);
    }
}
