package br.ufscar.dc.dsw1.security.impl;

import br.ufscar.dc.dsw1.dao.ILojaDAO;
import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.security.spec.ILojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class LojaService implements ILojaService {
    @Autowired
    ILojaDAO dao;

    public void salvar(Cliente s){
        dao.save(s);
    }
    public void excluir(String cnpj){
        dao.deleteByCnpj(cnpj);
    }
    @Transactional(readOnly = true)
    public Loja buscaPorCnpj(String cnpj) {
        return dao.findByCnpj(cnpj);
    }
    @Transactional(readOnly = true)
    public List<Loja> buscarTodos(){
        return dao.findAll();
    }
}
