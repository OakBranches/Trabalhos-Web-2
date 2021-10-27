package br.ufscar.dc.dsw1.services.impl;

import br.ufscar.dc.dsw1.dao.ILojaDAO;
import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.services.spec.ILojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = false)
public class LojaService extends UsuarioService implements ILojaService {
    @Autowired
    ILojaDAO dao;

    public void salvar(Loja s){
        dao.save(s);
    }
    public void excluir(String cnpj){
        dao.deleteByCnpj(cnpj);
    }
    public void excluirPorId(Long id){
        dao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean LojaTemCarros(Long id){
        return !(dao.findLojaById(id).getCarros().isEmpty());
    }
    @Transactional(readOnly = true)
    public Loja buscaPorId(Long id){
        return dao.findLojaById(id);
    }
    @Transactional(readOnly = true)
    public Loja buscaPorCnpj(String cnpj) {
        return dao.findByCnpj(cnpj);
    }
    @Transactional(readOnly = true)
    public List<Loja> buscarTodasLojas(){
        return dao.findAll();
    }
    @Transactional(readOnly = true)
    public boolean emailIsValid(Usuario usr){
        Usuario loja = buscaPorEmail(usr.getEmail());
        Long Id = usr.getId();
        return loja == null || Objects.equals(loja.getId(), Id);
    }
}
