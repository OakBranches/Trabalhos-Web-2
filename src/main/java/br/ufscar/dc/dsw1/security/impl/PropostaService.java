package br.ufscar.dc.dsw1.security.impl;

import br.ufscar.dc.dsw1.dao.IPropostaDAO;
import br.ufscar.dc.dsw1.domain.Proposta;
import br.ufscar.dc.dsw1.security.spec.IPropostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class PropostaService implements IPropostaService {
    @Autowired
    IPropostaDAO dao;

    public void salvar(Proposta s){
        dao.save(s);
    }
    public void excluir(Long id){
        dao.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Proposta buscaPorId(Long id){
        return dao.findById(id);
    }
    @Transactional(readOnly = true)
    public List<Proposta> buscarTodos(){
        return dao.findAll();
    }
    @Transactional(readOnly = true)
    public List<Proposta> buscarTodosPorCliente(String cpf){
        return dao.findAllByCliente_Cpf(cpf);
    }
    @Transactional(readOnly = true)
    public List<Proposta> buscarTodosPorCarro(Long id){
        return dao.findAllByCarro_Id(id);
    }
}
