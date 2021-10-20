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
    public void excluir(String cpf){
        dao.deleteByCpf(cpf);
    }
    @Transactional(readOnly = true)
    public Cliente buscaPorCpf(String cpf){
        return dao.findByCpf(cpf);
    }
    @Transactional(readOnly = true)
    public List<Cliente> buscarTodos(){
        return dao.findAll();
    }
}
