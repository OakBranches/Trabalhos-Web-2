package br.ufscar.dc.dsw1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {

	@NotBlank
    @Column(nullable = false, length = 256, unique = true)
    private String email;
    
	@NotBlank
    @Column(nullable = false, length = 256)
    private String senha;
       
    @NotBlank
    @Column(nullable = false, length = 256)
    private String nome;

	@Id
    @NotBlank
	//todo arrumar a mensagem
	@Size(min = 11, max = 20, message = "{Size.editora.CNPJ}")
    @Column(nullable = false)
    private String codigo;
    
    @NotNull
    @Column(nullable = false)
    private int papel;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getPapel() {
		return papel;
	}

	public void setPapel(int papel) {
		this.papel = papel;
	}


}