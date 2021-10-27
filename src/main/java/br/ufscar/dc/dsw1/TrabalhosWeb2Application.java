package br.ufscar.dc.dsw1;

import br.ufscar.dc.dsw1.dao.*;
import br.ufscar.dc.dsw1.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class TrabalhosWeb2Application {

    public static void main(String[] args) {
        SpringApplication.run(TrabalhosWeb2Application.class, args);
    }
    @Bean
    public CommandLineRunner demo(ILojaDAO lojaDAO, IUsuarioDAO usuarioDAO, ICarroDAO carroDAO, IClienteDAO clienteDAO, IPropostaDAO propostaDAO, BCryptPasswordEncoder encoder) {
        return (args) -> {

            Loja l1 = new Loja("loja@loja.com", encoder.encode("loja"), "Ensaio Jr", "11.111.111/1111-11", "BOa loja.");
            lojaDAO.save(l1);

            Loja l2 = new Loja("loja2@loja.com", encoder.encode("loja2"), "Ensaio 2 Jr", "11.111.111/1111-12", "BOa loja XD.");
            lojaDAO.save(l2);

            Usuario u1 = new Usuario("ok@ok.com", encoder.encode("ok"), "Ok", 1);
            usuarioDAO.save(u1);

            Carro car1 =  new Carro(BigDecimal.valueOf(10000), BigDecimal.valueOf(100),"0000001","none","carro antigo","0",1080, l1);
            carroDAO.save(car1);

            Carro car2 = new Carro(BigDecimal.valueOf(1000), BigDecimal.valueOf(5), "0000002","carro","carro antigo 2", "none",1081, l1);
            carroDAO.save(car2);

            Carro car3 = new Carro(BigDecimal.valueOf(2000), BigDecimal.valueOf(50000), "0000003","fusca","aço ino","214",1082, l2);
            carroDAO.save(car3);

            Date aux = new SimpleDateFormat("yyyy-MM-dd").parse("2001-03-01");
            Cliente c1 = new Cliente("cliente@cleinte.com", encoder.encode("cliente"), "João", "000.000.001-10", "999999999", "m", aux);
            clienteDAO.save(c1);

            aux = new SimpleDateFormat("yyyy-MM-dd").parse("2002-03-01");
            Cliente c2 = new Cliente("joao@cliente.com", encoder.encode("123"), "Clovis", "000.000.002-10", "992999999", "f",aux);
            clienteDAO.save(c2);

            aux = new SimpleDateFormat("yyyy-MM-dd").parse("2001-02-22");
            Proposta p1 = new Proposta(BigDecimal.valueOf(100), 0, "horrivel", aux, car1, c1);
            propostaDAO.save(p1);

            aux = new SimpleDateFormat("yyyy-MM-dd").parse("2001-02-22");
            Proposta p2 = new Proposta(BigDecimal.valueOf(100), 0, "condicao boa", aux, car2, c2);
            propostaDAO.save(p2);

        };
    }

}
