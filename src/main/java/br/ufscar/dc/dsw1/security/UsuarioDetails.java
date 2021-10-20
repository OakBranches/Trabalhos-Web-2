package br.ufscar.dc.dsw1.security;

import br.ufscar.dc.dsw1.domain.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("serial")
public class UsuarioDetails implements UserDetails {
 
    private Usuario usuario;
     
    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String papel = usuario.getPapel() == 1 ? "ROLE_ADMIN" : usuario.getPapel() == 2 ?  "ROLE_LOJA" : "ROLE_USER";
        //todo arrumar o papel
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(papel);
        System.out.println(papel);
        return Arrays.asList(authority);
    }
 
    @Override
    public String getPassword() {
        return usuario.getSenha();
    }
 
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }

	public Usuario getUsuario() {
		return usuario;
	}
    
}