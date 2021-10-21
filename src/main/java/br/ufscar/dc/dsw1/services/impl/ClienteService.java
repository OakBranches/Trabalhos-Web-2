package br.ufscar.dc.dsw1.services.impl;

import br.ufscar.dc.dsw1.dao.IClienteDAO;
import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.services.spec.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class ClienteService implements IClienteService {
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
        return !(dao.findClienteById(id).getPropostas().isEmpty());
    }
    @Transactional(readOnly = true)
    public List<Cliente> buscarTodos(){
        return dao.findAll();
    }
}
