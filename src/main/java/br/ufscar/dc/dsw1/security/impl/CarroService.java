package br.ufscar.dc.dsw1.security.impl;

import br.ufscar.dc.dsw1.dao.ICarroDAO;
import br.ufscar.dc.dsw1.domain.Carro;
import br.ufscar.dc.dsw1.security.spec.ICarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class CarroService implements ICarroService {
    @Autowired
    ICarroDAO dao;

    public void salvar(Carro s){
        dao.save(s);
    }
    public void excluir(Long id){
        dao.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Carro buscaPorId(Long id){
        return dao.findById(id);
    }
    @Transactional(readOnly = true)
    public List<Carro> buscarTodos(){
        return dao.findAll();
    }
    @Transactional(readOnly = true)
    public List<Carro> buscarTodosDaLoja(String cnpj){
        return dao.findAllByLoja_Cnpj(cnpj);
    }
}
