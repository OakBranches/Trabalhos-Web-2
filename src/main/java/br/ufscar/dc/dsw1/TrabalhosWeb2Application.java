package br.ufscar.dc.dsw1;

import br.ufscar.dc.dsw1.dao.ILojaDAO;
import br.ufscar.dc.dsw1.dao.IUsuarioDAO;
import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.domain.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class TrabalhosWeb2Application {

    public static void main(String[] args) {
        SpringApplication.run(TrabalhosWeb2Application.class, args);
    }
    @Bean
    public CommandLineRunner demo(ILojaDAO lojaDAO, BCryptPasswordEncoder encoder) {
        return (args) -> {

            Loja l1 = new Loja();

            l1.setNome("Ensaio Jr");
            l1.setEmail("loja@loja.com");
            l1.setPapel(1);
            l1.setSenha(encoder.encode("loja"));
            l1.setCodigo("1111111111111");
            l1.setDescricao("BOa loja.");
            lojaDAO.save(l1);

        };
    }

}
