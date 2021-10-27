package br.ufscar.dc.dsw1.services.impl;

import br.ufscar.dc.dsw1.dao.IUsuarioDAO;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.services.spec.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = false)
public class UsuarioService implements IUsuarioService {
    @Autowired
    IUsuarioDAO dao;
    public void salvar(Usuario s){
        dao.save(s);
    }
    public void excluir(Long id){
        dao.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Usuario buscaPorId(Long id){
        return dao.findById(id);
    }
    @Transactional(readOnly = true)
    public Usuario buscaPorEmail(String email){return dao.findByEmail(email);}
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos(){
        return dao.findAll();
    }
}
