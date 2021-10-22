package br.ufscar.dc.dsw1.security;

import br.ufscar.dc.dsw1.dao.IClienteDAO;
import br.ufscar.dc.dsw1.dao.ILojaDAO;
import br.ufscar.dc.dsw1.dao.IUsuarioDAO;
import br.ufscar.dc.dsw1.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsuarioDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private IUsuarioDAO dao;
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Usuario usuario = dao.findByEmail(username);
         
        if (usuario == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new UsuarioDetails(usuario);
    }
}