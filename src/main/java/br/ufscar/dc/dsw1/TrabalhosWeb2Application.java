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

            Loja l1 = new Loja("loja@loja.com", encoder.encode("loja"), "Ensaio Jr", 1, "1111111111111", "BOa loja.");
            lojaDAO.save(l1);

            Loja l2 = new Loja("loja2@loja.com", encoder.encode("loja2"), "Ensaio 2 Jr", 1, "1111111111111", "BOa loja XD.");
            lojaDAO.save(l1);

        };
    }

}
