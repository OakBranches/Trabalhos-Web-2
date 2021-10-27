package br.ufscar.dc.dsw1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@Table(name = "Usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {

    @NotBlank(message= "{NotBlank}")
    @Size(max = 256, message = "{Size.usuario.email}")
    @Column(nullable = false, length = 256, unique = true)
    private String email;
    
	@NotBlank(message= "{NotBlank}")
    @Column(nullable = false, length = 256)
    private String senha;
       
    @NotBlank(message= "{NotBlank}")
    @Column(nullable = false, length = 256)
    private String nome;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    protected Long id;
    
    @NotNull(message= "{NotBlank}")
    @Column(nullable = false)
    private int papel;

    public Usuario(){};
    public Usuario(String email, String senha, String nome, int papel) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.papel = papel;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", nome='" + nome + '\'' +
                ", id=" + (id == null ? "null": id) +
                ", papel=" + papel +
                '}';
    }
}